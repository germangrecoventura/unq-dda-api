package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.AssetBuilder
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

    val buyer = anyBuyer().build()
    val seller = anySeller().build()

    private fun anyTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withAsset(anyAsset().build())
            .withQuantity(20.00)
            .withUnitPrice(10.00)
            .withTotalAmount(100.00)
            .withSeller(seller)
            .withBuyer(buyer)
            .withOffer(anyOffer().build())
            .withCreated(LocalDateTime.now())
            .withStatus(TransactionStatus.WAITING)
    }

    private final fun anySeller(): UserBuilder {
        return UserBuilder()
            .withFirstName("Homero")
            .withLastName("Simpson")
            .withEmail("homero.seller@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Super!")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678")
    }

    private final fun anyBuyer(): UserBuilder {
        return UserBuilder()
            .withFirstName("Homero")
            .withLastName("Simpson")
            .withEmail("homero.buyer@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Super!")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678")
    }

    private fun anyAsset(): AssetBuilder {
        val now = LocalDateTime.now()

        return AssetBuilder()
            .withName("ALICEUSDT")
            .withCreated(now)
            .withUpdated(now)
    }

    private fun anyOffer(): OfferBuilder {
        return OfferBuilder()
            .withAsset(anyAsset().build())
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
    fun `should throw an exception when asset is null`() {
        Assertions.assertThrows(RuntimeException::class.java) { anyTransaction().withAsset(null).build() }
    }

    @Test
    fun `should throw an exception when the quantity is negative`() {
        val user = anyTransaction().withQuantity(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the unit price is negative`() {
        val user = anyTransaction().withUnitPrice(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the total amount is negative`() {
        val user = anyTransaction().withTotalAmount(-50.00).build()
        val violations = validator.validate(user)
        Assertions.assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should throw an exception when the buyer is null in the transaction`() {
        Assertions.assertThrows(RuntimeException::class.java) { anyTransaction().withBuyer(null).build() }
    }

    @Test
    fun `should throw an exception when the buyer and the seller are the same user`() {
        var offer = anyOffer().withUser(seller).withOperation(OfferType.BUY).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(
                RuntimeException::class.java
            )
            { anyTransaction().withOffer(offer).withSeller(seller).withBuyer(seller).build() }

        Assertions.assertEquals(
            "The seller cannot be the same buyer",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when the seller is null`() {
        Assertions.assertThrows(RuntimeException::class.java) { anyTransaction().withSeller(null).build() }
    }

    @Test
    fun `should throw an exception when the offer is null`() {
        Assertions.assertThrows(RuntimeException::class.java) { anyTransaction().withOffer(null).build() }
    }
}
