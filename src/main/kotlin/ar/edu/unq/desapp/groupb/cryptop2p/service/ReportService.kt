package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.model.TradedVolumeReport
import ar.edu.unq.desapp.groupb.cryptop2p.model.TradedVolumeReportLineItem
import ar.edu.unq.desapp.groupb.cryptop2p.model.TransactionStatus
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.UserNotRegisteredException
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.TransactionRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class ReportService(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val exchangeService: ExchangeService,
) {
    fun generateTradedVolumeReport(userId: Long, startDate: LocalDate, endDate: LocalDate): TradedVolumeReport {
        val optionalUser = userRepository.findById(userId)
        if (optionalUser.isEmpty) {
            throw UserNotRegisteredException()
        }
        val user = optionalUser.get()
        val exchangeRateUSD = exchangeService.getConversionRateARStoUSD()

        val transactions = transactionRepository.getAllBySellerOrBuyerAndCreatedBetweenAndStatus(
            user,
            startDate.atStartOfDay(),
            endDate.atTime(23, 59, 59),
            TransactionStatus.CONFIRMED
        )

        val assetNames = transactions.map { it.asset!!.name!! }.toSet()
        val assetPrices = exchangeService.getCryptoAssetsPrices(assetNames.toList())

        val requestDateTime = LocalDateTime.now()


        val lineItems = transactions.groupBy { it.asset }
            .map {
                val assetName = it.key!!.name!!
                val assetPrice: Double
                try {
                    assetPrice = assetPrices.getValue(it.key!!.name!!)
                } catch (e: NoSuchElementException) {
                    throw ModelException("Missing price for $assetName")
                }

                val quantity = it.value.sumOf { t -> t.quantity!! }
                val totalAmountInARS = assetPrice * quantity

                TradedVolumeReportLineItem(
                    it.key!!.name!!,
                    quantity,
                    assetPrice,
                    totalAmountInARS,
                )
            }.toList()

        val totalAmountInARS = lineItems.sumOf { it.totalAmountInARS }
        val totalAmountInUSD = totalAmountInARS / exchangeRateUSD

        return TradedVolumeReport(user, requestDateTime, totalAmountInUSD, totalAmountInARS, lineItems)
    }
}
