package ar.edu.unq.desapp.groupb.cryptop2p.model

import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class AssetTest {
    @Autowired
    lateinit var validator: Validator

    fun anyAsset(): Asset {
        val now = LocalDateTime.now()
        return Asset(id = 1, symbol = "ALICEUSDT", created = now)
    }

    @Test
    fun `should create an asset`() {
        val asset = anyAsset()
        val violations = validator.validate(asset)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should have an error when the symbol is null`() {
        val asset = anyAsset()
        asset.symbol = null
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the symbol is empty`() {
        val asset = anyAsset()
        asset.symbol = ""
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the symbol has numbers`() {
        val asset = anyAsset()
        asset.symbol = "NEW0"
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the symbol has any special character`() {
        val asset = anyAsset()
        asset.symbol = "NEW@"
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the symbol has any lower case character`() {
        val asset = anyAsset()
        asset.symbol = "New"
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the creation date is empty`() {
        val asset = anyAsset()
        asset.created = null
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }
}
