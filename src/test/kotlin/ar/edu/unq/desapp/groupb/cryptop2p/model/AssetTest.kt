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
        return Asset(id = 1, name = "ALICEUSDT", created = now)
    }

    @Test
    fun `should create an asset`() {
        val asset = anyAsset()
        val violations = validator.validate(asset)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should have an error when the name is null`() {
        val asset = anyAsset()
        asset.name = null
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the name is empty`() {
        val asset = anyAsset()
        asset.name = ""
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the name has numbers`() {
        val asset = anyAsset()
        asset.name = "NEW0"
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the name has a special character`() {
        val asset = anyAsset()
        asset.name = "NEW@"
        val violations = validator.validate(asset)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the name has any lower case`() {
        val asset = anyAsset()
        asset.name = "New"
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
