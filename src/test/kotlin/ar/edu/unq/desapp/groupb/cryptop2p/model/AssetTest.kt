package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.AssetBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalTime

@SpringBootTest
class AssetTest {
    @Autowired
    lateinit var validator: Validator

    fun anyAsset(): AssetBuilder {
        var time = LocalTime.now()
        return AssetBuilder()
            .withName("ALICEUSDT")
            .withUnitPrice(200.00)
            .withCreated(time)
            .withUpdated(time.plusNanos(1))
    }

    @Test
    fun `should create a user when it has valid data`() {
        assertDoesNotThrow { anyAsset().build() }
    }

    @Test
    fun `should throw an exception when name is null`() {
        val user = anyAsset().withName(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the name is empty`() {
        val user = anyAsset().withName("").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the name has numbers`() {
        val user = anyAsset().withName("NUEV0").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the name has a special character`() {
        val user = anyAsset().withName("NUEV@").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the name has any lower case`() {
        val user = anyAsset().withName("NUevo").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the unit price is null`() {
        val user = anyAsset().withUnitPrice(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the price is negative`() {
        val user = anyAsset().withUnitPrice(-50.00).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the creation date is empty`() {
        val user = anyAsset().withCreated(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the update date is empty`() {
        val user = anyAsset().withUpdated(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the update cannot occur before its creation date`() {
        var localtime = LocalTime.now()

        val thrown = Assertions.assertThrows(RuntimeException::class.java) {
            anyAsset().withCreated(localtime).withUpdated(localtime.minusNanos(1)).build()
        }

        Assertions.assertEquals(
            "The update cannot occur before its creation date",
            thrown.message
        )
    }
}
