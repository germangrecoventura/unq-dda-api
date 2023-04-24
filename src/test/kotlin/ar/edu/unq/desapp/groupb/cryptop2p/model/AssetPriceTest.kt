package ar.edu.unq.desapp.groupb.cryptop2p.model

import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class AssetPriceTest {
    @Autowired
    lateinit var validator: Validator

    fun anyAssetPrice(): AssetPrice {
        val now = LocalDateTime.now()
        return AssetPrice(name = "ALICEUSDT", unitPrice = 3.14, created = now, updated = now)
    }

    @Test
    fun `should create an asset price`() {
        val assetPrice = anyAssetPrice()
        val validations = validator.validate(assetPrice)
        assertTrue(validations.isEmpty())
    }

    @Test
    fun `should have an error when asset is null`() {
        val assetPrice = anyAssetPrice()
        assetPrice.name = null
        val validations = validator.validate(assetPrice)
        assertTrue(validations.isNotEmpty())
    }

    @Test
    fun `should have an error when unit price is null`() {
        val assetPrice = anyAssetPrice()
        assetPrice.unitPrice = null
        val validations = validator.validate(assetPrice)
        assertTrue(validations.isNotEmpty())
    }

    @Test
    fun `should have an error when unit price is negative`() {
        val assetPrice = anyAssetPrice()
        assetPrice.unitPrice = -3.14
        val validations = validator.validate(assetPrice)
        assertTrue(validations.isNotEmpty())
    }

    @Test
    fun `should have an error when created is null`() {
        val assetPrice = anyAssetPrice()
        assetPrice.created = null
        val validations = validator.validate(assetPrice)
        assertTrue(validations.isNotEmpty())
    }

    @Test
    fun `should have an error when updated is null`() {
        val assetPrice = anyAssetPrice()
        assetPrice.updated = null
        val validations = validator.validate(assetPrice)
        assertTrue(validations.isNotEmpty())
    }
}
