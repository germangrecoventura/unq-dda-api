package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.AssetBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class AssetTest {
    @Autowired
    lateinit var validator: Validator

    fun anyAsset(): AssetBuilder {
        var time = LocalDateTime.now()
        return AssetBuilder()
            .withName("ALICEUSDT")
            .withUnitPrice(200.00)
            .withCreated(time)
            .withUpdated(time)
    }

    @Test
    fun `should create a asset when it has valid data`() {
        assertDoesNotThrow { anyAsset().build() }
    }

    @Test
    fun `should throw an exception when name is null`() {
        val asset = anyAsset().withName(null).build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the name is empty`() {
        val asset = anyAsset().withName("").build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the name has numbers`() {
        val asset = anyAsset().withName("NUEV0").build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the name has a special character`() {
        val asset = anyAsset().withName("NUEV@").build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the name has any lower case`() {
        val asset = anyAsset().withName("NUevo").build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the unit price is null`() {
        val asset = anyAsset().withUnitPrice(null).build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the price is negative`() {
        val asset = anyAsset().withUnitPrice(-50.00).build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the creation date is empty`() {
        val asset = anyAsset().withCreated(null).build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should throw an exception when the updated date is empty`() {
        val asset = anyAsset().withUpdated(null).build()
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }
}
