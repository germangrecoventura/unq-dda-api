package ar.edu.unq.desapp.groupb.cryptop2p.service

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
        val requestDateTime = LocalDateTime.now()
        var totalAmountInUSD = 0.0
        var totalAmountInARS = 0.0
        val lineItems = mutableListOf<TradedVolumeReportLineItem>()

        val exchangeRate = exchangeService.getARStoUSDExchangeRate()

        val transactions = transactionRepository.getAllBySellerOrBuyerAndCreatedBetweenAndStatus(
            user,
            startDate.atStartOfDay(),
            endDate.atTime(23, 59, 59),
            TransactionStatus.CONFIRMED
        )


        transactions.forEach {
            totalAmountInARS += it.totalAmount!!
            val item = TradedVolumeReportLineItem(
                it.asset!!.name!!, it.quantity!!, it.unitPrice!!, it.totalAmount!!
            )
            lineItems.add(item)
        }

        totalAmountInUSD = totalAmountInARS / exchangeRate

//        Dia y hora de solicitud -> LocalDatetime.now
//        Valor total operado en dólares -> Valor total operado segun el rating
//        Valor total operado en pesos ARG -> Valor total operado segun el rating
//        Activos:
//        Criptoactivo -> Nombre
//        Cantidad nominal del Cripto Activo -> Suma de la cantidad en rating porque ahi tiene la oferta
//        Cotización actual del Cripto Activo -> Binance
//        Monto de la cotización en pesos ARG -> Binance y conversor

        return TradedVolumeReport(user, requestDateTime, totalAmountInUSD, totalAmountInARS, lineItems)
    }
}
