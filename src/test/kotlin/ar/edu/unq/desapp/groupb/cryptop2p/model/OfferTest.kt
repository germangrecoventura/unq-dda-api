package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.AssetBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.OfferBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.UserBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class OfferTest {
    @Autowired
    lateinit var validator: Validator

    fun anyUser(): UserBuilder {
        return UserBuilder()
            .withFirstName("Homero")
            .withLastName("Simpson")
            .withEmail("homero.simpson@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Super!")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678")
    }

    fun anyAsset(): AssetBuilder {
        val now = LocalDateTime.now()

        return AssetBuilder()
            .withName("ALICEUSDT")
            .withCreated(now)
    }

    fun anyOfferBuy(): OfferBuilder {
        return OfferBuilder()
            .withAsset(anyAsset().build())
            .withQuantity(20.00)
            .withUnitPrice(40.00)
            .withUser(anyUser().build())
            .withType(OfferType.BUY)
            .withActive(true)
            .withCreated(LocalDateTime.now())
    }

    fun anyOfferSell(): OfferBuilder {
        return OfferBuilder()
            .withAsset(anyAsset().build())
            .withQuantity(20.00)
            .withUnitPrice(40.00)
            .withUser(anyUser().build())
            .withType(OfferType.SELL)
            .withActive(true)
            .withCreated(LocalDateTime.now())
    }

    @Test
    fun `should create an offer of type BUY`() {
        assertDoesNotThrow { anyOfferBuy().build() }
    }

    @Test
    fun `should create an offer of type SELL`() {
        assertDoesNotThrow { anyOfferSell().build() }
    }

    @Test
    fun `should have an error when asset name is null`() {
        val offer = anyOfferBuy().withAsset(null).build()
        val violations = validator.validate(offer)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the quantity is negative`() {
        val offer = anyOfferBuy().withQuantity(-50.00).build()
        val violations = validator.validate(offer)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the unit price is negative`() {
        val offer = anyOfferBuy().withUnitPrice(-50.00).build()
        val violations = validator.validate(offer)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have a total amount equal to 800`() {
        val offer = anyOfferBuy().build()
        assertEquals(800.0, offer.totalAmount)
    }

    @Test
    fun `should have an error when the user is null`() {
        val offer = anyOfferBuy().withUser(null).build()
        val violations = validator.validate(offer)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the active flag is null`() {
        val offer = anyOfferBuy().withActive(null).build()
        val violations = validator.validate(offer)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the created date is null`() {
        val offer = anyOfferBuy().withCreated(null).build()
        val violations = validator.validate(offer)
        Assertions.assertTrue(violations.isNotEmpty())
    }
}
