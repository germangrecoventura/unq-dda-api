package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.model.Transaction
import ar.edu.unq.desapp.groupb.cryptop2p.model.TransactionStatus
import ar.edu.unq.desapp.groupb.cryptop2p.model.UserTransactionRating
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.TransactionValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.OfferRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.TransactionRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserTransactionRatingRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.TransactionCreateRequestDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.TransactionDTO
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val offerRepository: OfferRepository,
    private val transactionValidator: TransactionValidator,
    private val userTransactionRatingRepository: UserTransactionRatingRepository
) {
    var logger: Logger = LoggerFactory.getLogger(TransactionService::class.java)
    fun save(transactionDTO: TransactionCreateRequestDTO): Transaction {
        logger.info("Saving transaction...")
        transactionValidator.isCreationRequestValid(transactionDTO.userId!!, transactionDTO.offerId!!)
        val user = userRepository.findById(transactionDTO.userId!!).get()
        var offer = offerRepository.findById(transactionDTO.offerId!!).get()
        val buyer = if (offer.operation == OfferType.BUY) {
            offer.user
        } else {
            user
        }
        val seller = if (offer.operation == OfferType.SELL) {
            offer.user
        } else {
            user
        }
        offer.isActive = false
        offer = offerRepository.save(offer)
        val transaction = Transaction().fromModel(
            offer.asset!!,
            offer.quantity!!,
            offer.unitPrice!!,
            offer.totalAmount!!,
            offer,
            seller!!,
            buyer!!
        )
        return transactionRepository.save(transaction)
    }


    fun transferTransaction(transactionDTO: TransactionDTO): Transaction {
        logger.info("Transfer transaction...")
        transactionValidator.isTransferTransactionValid(transactionDTO.userId!!, transactionDTO.transactionId!!)
        val transaction = transactionRepository.findById(transactionDTO.transactionId!!).get()
        transaction.status = TransactionStatus.TRANSFERRED
        return transactionRepository.save(transaction)
    }

    fun confirmTransaction(transactionDTO: TransactionDTO): Transaction {
        logger.info("Confirm transaction...")
        transactionValidator.isConfirmTransactionValid(transactionDTO.userId!!, transactionDTO.transactionId!!)
        val transaction = transactionRepository.findById(transactionDTO.transactionId!!).get()
        transaction.status = TransactionStatus.CONFIRMED
        val confirmedTransaction = transactionRepository.save(transaction)
        val points = if (LocalDateTime.now().isAfter(transaction.offer!!.created!!.plusMinutes(31))) 5 else 10
        val ratingBuyer = UserTransactionRating()
        ratingBuyer.transaction = confirmedTransaction
        ratingBuyer.user = transaction.buyer
        ratingBuyer.created = LocalDateTime.now()
        ratingBuyer.rating = points
        val ratingSeller = UserTransactionRating()
        ratingSeller.transaction = confirmedTransaction
        ratingSeller.user = transaction.seller
        ratingSeller.created = LocalDateTime.now()
        ratingSeller.rating = points
        userTransactionRatingRepository.save(ratingBuyer)
        userTransactionRatingRepository.save(ratingSeller)
        return confirmedTransaction
    }

    fun cancelTransaction(transactionCancelDTO: TransactionDTO): Transaction {
        logger.info("Cancel transaction...")
        transactionValidator.isCancelTransactionValid(
            transactionCancelDTO.userId!!,
            transactionCancelDTO.transactionId!!
        )
        val transaction = transactionRepository.findById(transactionCancelDTO.transactionId!!).get()
        val user = userRepository.findById(transactionCancelDTO.userId!!).get()
        transaction.status = TransactionStatus.CANCELED
        val canceledTransaction = transactionRepository.save(transaction)
        val rating = UserTransactionRating()
        rating.transaction = canceledTransaction
        rating.user = user
        rating.created = LocalDateTime.now()
        rating.rating = -20
        userTransactionRatingRepository.save(rating)
        return canceledTransaction
    }

    fun clear() {
        transactionRepository.deleteAll()
    }
}
