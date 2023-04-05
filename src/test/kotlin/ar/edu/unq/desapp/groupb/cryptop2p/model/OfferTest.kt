package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.OfferBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.UserBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
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

    fun anyOfferBuy(): OfferBuilder {
        return OfferBuilder()
            .withAsset("ALICEUSDT")
            .withQuantity(20.00)
            .withUnitPrice(40.00)
            .withTotalAmount(400.00)
            .withUser(anyUser().build())
            .withOperation(OfferType.BUY)
            .withActive(true)
            .withCreated(LocalDateTime.now())
    }

    fun anyOfferSell(): OfferBuilder {
        return OfferBuilder()
            .withAsset("ALICEUSDT")
            .withQuantity(20.00)
            .withUnitPrice(40.00)
            .withTotalAmount(400.00)
            .withUser(anyUser().build())
            .withOperation(OfferType.SELL)
            .withActive(true)
            .withCreated(LocalDateTime.now())
    }

    @Test
    fun `should create a offer shopping when it has valid data`() {
        assertDoesNotThrow { anyOfferBuy().build() }
    }

    @Test
    fun `should create a offer selling when it has valid data`() {
        assertDoesNotThrow { anyOfferSell().build() }
    }

    @Test
    fun `should throw an exception when asset name is null in shop`() {
        val user = anyOfferBuy().withAsset(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when asset name is null in sale`() {
        val user = anyOfferSell().withAsset(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when asset name is empty in shop`() {
        val user = anyOfferBuy().withAsset("").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when asset name is empty in sale`() {
        val user = anyOfferSell().withAsset("").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the asset name has numbers in the shop`() {
        val user = anyOfferBuy().withAsset("AA5").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the asset name has numbers in the sale`() {
        val user = anyOfferSell().withAsset("AA5").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the asset name has special characters in the shop`() {
        val user = anyOfferBuy().withAsset("AA@").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the asset name has special characters in the sale`() {
        val user = anyOfferSell().withAsset("AA@").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the asset name has lower case in the shop`() {
        val user = anyOfferBuy().withAsset("AAa").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the asset name has lower case in the sale`() {
        val user = anyOfferSell().withAsset("AAa").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the nominal amount is negative in the shop`() {
        val user = anyOfferBuy().withQuantity(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the nominal amount is negative in the sale`() {
        val user = anyOfferSell().withQuantity(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the crypto price is negative in the shop`() {
        val user = anyOfferBuy().withUnitPrice(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the crypto price is negative in the sale`() {
        val user = anyOfferSell().withUnitPrice(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the amount in pesos is negative in the shop`() {
        val user = anyOfferBuy().withTotalAmount(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the amount in pesos is negative in the sale`() {
        val user = anyOfferSell().withTotalAmount(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the user is null in the shop`() {
        val user = anyOfferBuy().withUser(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the user is null in the sale`() {
        val user = anyOfferSell().withUser(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the active is null in the shop`() {
        val user = anyOfferBuy().withActive(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the active is null in the sale`() {
        val user = anyOfferSell().withActive(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the created is null in the shop`() {
        val user = anyOfferBuy().withCreated(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the created is null in the sale`() {
        val user = anyOfferSell().withCreated(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }



}
