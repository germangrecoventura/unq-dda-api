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
import jakarta.transaction.Transactional
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
    fun save(userId: Long, offerId: Long): Transaction {
        transactionValidator.isCreationRequestValid(userId, offerId)
        val user = userRepository.findById(userId).get()
        val offer = offerRepository.findById(offerId).get()
        val fivePercent = offer.asset!!.prices.last().unitPrice!! * 5.0 / 100
        val isActive = offer.unitPrice!! <= (offer.asset!!.prices.last().unitPrice!! + fivePercent)
                &&
                offer.unitPrice!! >= (offer.asset!!.prices.last().unitPrice!! - fivePercent)
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
        if (!isActive) {
            offer.isActive = false
            offerRepository.save(offer)
            val transaction = Transaction().fromModel(
                offer.asset!!,
                offer.quantity!!,
                offer.unitPrice!!,
                offer.totalAmount!!,
                offer,
                seller!!,
                buyer!!
            )
            transaction.status = TransactionStatus.CANCELED
            return transactionRepository.save(transaction)
        } else {
            offer.isActive = false
            offerRepository.save(offer)
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
    }

    fun transferTransaction(userId: Long, transactionId: Long): Transaction {
        transactionValidator.isTransferedValid(userId, transactionId)
        val transaction = transactionRepository.findById(transactionId).get()
        transaction.status = TransactionStatus.TRANSFERRED
        return transactionRepository.save(transaction)
    }

    fun confirmTransferTransaction(userId: Long, transactionId: Long): Transaction {
        transactionValidator.isConfirmTransferedValid(userId, transactionId)
        val transaction = transactionRepository.findById(transactionId).get()
        transaction.status = TransactionStatus.CONFIRMED
        val transactionCompleted = transactionRepository.save(transaction)
        val points = if (LocalDateTime.now().isAfter(transaction.offer!!.created!!.plusMinutes(31))) 5 else 10
        val ratingBuyer = UserTransactionRating()
        ratingBuyer.transaction = transactionCompleted
        ratingBuyer.user = transaction.buyer
        ratingBuyer.created = LocalDateTime.now()
        ratingBuyer.rating = points
        val ratingSeller = UserTransactionRating()
        ratingBuyer.transaction = transactionCompleted
        ratingBuyer.user = transaction.seller
        ratingBuyer.created = LocalDateTime.now()
        ratingBuyer.rating = points
        userTransactionRatingRepository.save(ratingBuyer)
        userTransactionRatingRepository.save(ratingSeller)
        return transactionCompleted
    }

    fun cancelTransaction(userId: Long, transactionId: Long): Transaction {
        transactionValidator.isCancelTransactionValid(userId, transactionId)
        val transaction = transactionRepository.findById(transactionId).get()
        val user = userRepository.findById(userId).get()
        transaction.status = TransactionStatus.CANCELED
        val transactionUpdate = transactionRepository.save(transaction)
        val rating = UserTransactionRating()
        rating.transaction = transactionUpdate
        rating.user = user
        rating.created = LocalDateTime.now()
        rating.rating = -20
        userTransactionRatingRepository.save(rating)
        return transaction
    }


    fun clear() {
        transactionRepository.deleteAll()
    }
}