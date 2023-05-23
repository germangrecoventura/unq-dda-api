package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.*
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.AssetBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.OfferBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.TransactionBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.UserBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.TransactionRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.*
import java.util.*


@SpringBootTest
@ExtendWith(MockitoExtension::class)
class ReportServiceTest {
    @MockBean
    @Autowired
    lateinit var userRepository: UserRepository

    @MockBean
    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @MockBean
    @Autowired
    lateinit var exchangeService: ExchangeService

    @MockBean
    @Autowired
    lateinit var clock: Clock

    @Autowired
    lateinit var reportService: ReportService

    lateinit var startDateTime: LocalDateTime
    lateinit var endDateTime: LocalDateTime
    val buyer: User = anyBuyer()
    val seller: User = anySeller()

    private fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withAsset(anyAsset().build())
            .withQuantity(20.00)
            .withUnitPrice(40.00)
            .withTotalAmount(800.00)
            .withSeller(seller)
            .withBuyer(buyer)
            .withOffer(anyOffer().build())
            .withCreated(LocalDateTime.of(2023, 5, 21, 0, 0, 0))
            .withStatus(TransactionStatus.CONFIRMED)
    }

    private final fun anyUser(): UserBuilder {
        return UserBuilder()
            .withFirstName("Homero")
            .withLastName("Seller")
            .withEmail("homero.seller@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Super!")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678")
    }

    private final fun anySeller(): User {
        val seller = anyUser()
            .withLastName("Seller")
            .withEmail("homero.seller@sprinfield.com")
            .build()
        seller.id = 1
        return seller
    }

    private final fun anyBuyer(): User {
        val buyer = anyUser()
            .withLastName("Buyer")
            .withEmail("homero.buyer@sprinfield.com")
            .build()
        buyer.id = 2
        return buyer
    }

    private fun anyAsset(): AssetBuilder {
        val now = LocalDateTime.now()

        return AssetBuilder()
            .withName("ALICEUSDT")
            .withCreated(now)
    }

    private fun anyOffer(): OfferBuilder {
        return OfferBuilder()
            .withAsset(anyAsset().build())
            .withQuantity(20.00)
            .withUnitPrice(40.00)
            .withTotalAmount(800.00)
            .withUser(buyer)
            .withOperation(OfferType.BUY)
            .withActive(true)
            .withCreated(LocalDateTime.now())
    }

    @BeforeEach
    fun setUp() {
        val fixedClock =
            Clock.fixed(
                LocalDate.of(2023, 5, 21).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault()
            )

        Mockito
            .`when`(clock.instant()).thenReturn(fixedClock.instant())
        Mockito
            .`when`(clock.getZone()).thenReturn(ZoneId.systemDefault())

        startDateTime = LocalDate.of(2023, 5, 20).atStartOfDay()
        endDateTime = LocalDateTime.of(2023, 5, 22, 23, 59, 59)

        Mockito
            .`when`(userRepository.findById(1))
            .thenReturn(Optional.of(seller))
        Mockito
            .`when`(
                transactionRepository.getAllBySellerOrBuyerAndCreatedBetweenAndStatus(
                    seller,
                    startDateTime,
                    endDateTime,
                    TransactionStatus.CONFIRMED
                )
            )
            .thenReturn(listOf())
        Mockito
            .`when`(exchangeService.getConversionRateARStoUSD())
            .thenReturn(240.0)
        Mockito
            .`when`(exchangeService.getCryptoAssetsPrices(listOf("ALICEUSDT")))
            .thenReturn(mapOf("ALICEUSDT" to 40.0))
    }

    @Test
    fun `should return a report with no line items when user does not have any transactions`() {
        val expectedDateTime = LocalDateTime.of(2023, 5, 21, 0, 0, 0)

        val report = reportService.generateTradedVolumeReport(1, startDateTime.toLocalDate(), endDateTime.toLocalDate())

        Assertions.assertEquals(expectedDateTime, report.requestDateTime)
        Assertions.assertEquals(seller, report.user)
        Assertions.assertEquals(0.0, report.totalAmountARS)
        Assertions.assertEquals(0.0, report.totalAmountUSD)
        Assertions.assertEquals(listOf<TradedVolumeReportLineItem>(), report.lineItems)
    }

    @Test
    fun `should return a report with line items when user has transactions`() {
        val transactions = listOf(
            anyTransaction().build(),
            anyTransaction().build(),
            anyTransaction().build()
        )

        Mockito
            .`when`(
                transactionRepository.getAllBySellerOrBuyerAndCreatedBetweenAndStatus(
                    seller,
                    startDateTime,
                    endDateTime,
                    TransactionStatus.CONFIRMED
                )
            )
            .thenReturn(transactions)


        val expectedDateTime = LocalDateTime.of(2023, 5, 21, 0, 0, 0)

        val report = reportService.generateTradedVolumeReport(1, startDateTime.toLocalDate(), endDateTime.toLocalDate())

        Assertions.assertEquals(expectedDateTime, report.requestDateTime)
        Assertions.assertEquals(seller, report.user)
        Assertions.assertEquals(2400.0, report.totalAmountARS)
        Assertions.assertEquals(10.0, report.totalAmountUSD)
        Assertions.assertEquals(1, report.lineItems.size)
        Assertions.assertEquals("ALICEUSDT", report.lineItems[0].asset)
        Assertions.assertEquals(60.0, report.lineItems[0].quantity)
        Assertions.assertEquals(40.0, report.lineItems[0].unitPrice)
        Assertions.assertEquals(2400.0, report.lineItems[0].totalAmountInARS)
    }
}
