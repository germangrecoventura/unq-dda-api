package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.model.TradedVolumeReport
import ar.edu.unq.desapp.groupb.cryptop2p.model.TradedVolumeReportLineItem
import ar.edu.unq.desapp.groupb.cryptop2p.model.TransactionStatus
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.UserNotRegisteredException
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.TransactionRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class ReportService(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val exchangeService: ExchangeService,
    private val clock: Clock,
) {
    var logger: Logger = LoggerFactory.getLogger(ReportService::class.java)
    fun generateTradedVolumeReport(userId: Long, startDate: LocalDate, endDate: LocalDate): TradedVolumeReport {
        logger.info("Generate traded volume report...")
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

        val requestDateTime = LocalDateTime.now(clock)

        val report: TradedVolumeReport
        if (transactions.isEmpty()) {
            report = TradedVolumeReport(user, requestDateTime, 0.0, 0.0, listOf())
        } else {
            val assetNames = transactions.map { it.asset!!.name!! }.toSet()
            val assetPrices = exchangeService.getCryptoAssetsPrices(assetNames.toList())

            val lineItems = transactions.groupBy { it.asset!!.name }
                .map {
                    val assetName = it.key!!
                    val assetPrice: Double
                    try {
                        assetPrice = assetPrices.getValue(assetName)
                    } catch (e: NoSuchElementException) {
                        throw ModelException("Missing price for $assetName")
                    }

                    val quantity = it.value.sumOf { t -> t.quantity!! }
                    val totalAmountInARS = assetPrice * quantity

                    TradedVolumeReportLineItem(
                        assetName,
                        quantity,
                        assetPrice,
                        totalAmountInARS,
                    )
                }.toList()

            val totalAmountInARS = lineItems.sumOf { it.totalAmountInARS }
            val totalAmountInUSD = totalAmountInARS / exchangeRateUSD

            report = TradedVolumeReport(user, requestDateTime, totalAmountInUSD, totalAmountInARS, lineItems)
        }
        return report
    }
}
