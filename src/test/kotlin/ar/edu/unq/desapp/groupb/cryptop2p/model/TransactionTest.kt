package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.OfferBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.TransactionBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.UserBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class TransactionTest {
    @Autowired
    lateinit var validator: Validator

    var seller = anySeller().build()
    var buyer = anyBuyer().build()

    fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withAsset("ALICEUSDT")
            .withQuantity(20.00)
            .withUnitPrice(10.00)
            .withTotalAmount(100.00)
            .withSeller(seller)
            .withBuyer(buyer)
            .withOffer(anyOffer().build())
            .withCreated(LocalDateTime.now())
            .withStatus(TransactionStatus.WAITING)
    }

    fun anySeller(): UserBuilder {
        return UserBuilder()
            .withFirstName("Homero")
            .withLastName("Simpson")
            .withEmail("homero.seller@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Super!")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678")
    }

    fun anyBuyer(): UserBuilder {
        return UserBuilder()
            .withFirstName("Homero")
            .withLastName("Simpson")
            .withEmail("homero.buyer@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Super!")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678")
    }

    fun anyOffer(): OfferBuilder {
        return OfferBuilder()
            .withAsset("ALICEUSDT")
            .withQuantity(20.00)
            .withUnitPrice(40.00)
            .withTotalAmount(400.00)
            .withUser(buyer)
            .withOperation(OfferType.BUY)
            .withActive(true)
            .withCreated(LocalDateTime.now())
    }

    @Test
    fun `should create a purchase transaction when it has valid data`() {
        assertDoesNotThrow { anyTransaction().build() }
    }

    @Test
    fun `should throw an exception when asset name is null in transaction`() {
        Assertions.assertThrows(RuntimeException::class.java) { anyTransaction().withAsset(null).build() }
    }


    @Test
    fun `should throw an exception when asset name is empty in transaction `() {
        val transaction = anyTransaction().withAsset("").build()
        val violations = validator.validate(transaction)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the asset name has numbers in transaction`() {
        val user = anyTransaction().withAsset("AA5").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the asset name has special characters in the transaction`() {
        val user = anyTransaction().withAsset("AA@").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the asset name has lower case in the transaction`() {
        val user = anyTransaction().withAsset("AAa").build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the nominal amount is negative in the transaction`() {
        val user = anyTransaction().withQuantity(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the crypto price is negative in the transaction`() {
        val user = anyTransaction().withUnitPrice(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the amount in pesos is negative in the transaction`() {
        val user = anyTransaction().withTotalAmount(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the buyer is null in the transaction`() {
        Assertions.assertThrows(RuntimeException::class.java) { anyTransaction().withBuyer(null).build() }
    }

    @Test
    fun `should throw an exception when a purchase transaction does not have as buyer the one of the offer`() {
        var offer = anyOffer().withUser(seller).withOperation(OfferType.BUY).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(
                RuntimeException::class.java
            )
            { anyTransaction().withOffer(offer).withBuyer(buyer).build() }

        Assertions.assertEquals(
            "The buyer is not the same as the offer",
            thrown.message
        )
    }

    @Test
    fun `the buyer and the seller are the same people`() {
        var offer = anyOffer().withUser(seller).withOperation(OfferType.BUY).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(
                RuntimeException::class.java
            )
            { anyTransaction().withOffer(offer).withSeller(seller).withBuyer(seller).build()}

        Assertions.assertEquals(
            "The seller cannot be the same buyer",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when the seller is null in the transaction`() {
        Assertions.assertThrows(RuntimeException::class.java) { anyTransaction().withSeller(null).build() }
    }

    @Test
    fun `should throw an exception when a sell transaction does not have as seller the one of the offer`() {
        var offer = anyOffer().withUser(buyer).withOperation(OfferType.SELL).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(
                RuntimeException::class.java
            )
            { anyTransaction().withSeller(seller).withBuyer(buyer).withOffer(offer).build() }

        Assertions.assertEquals(
            "The seller is not the same as the offer",
            thrown.message
        )
    }


    @Test
    fun `should throw an exception when the offer is null in the transaction`() {
        Assertions.assertThrows(RuntimeException::class.java) { anyTransaction().withOffer(null).build() }
    }

    @Test
    fun `should throw an exception when the created is null in the transaction`() {
        val user = anyTransaction().withCreated(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the status is null in the transaction`() {
        val user = anyTransaction().withStatus(null).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }
}
