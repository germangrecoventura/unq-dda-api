package ar.edu.unq.desapp.groupb.cryptop2p.model.validator

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferCreateRequestDTO
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
@ExtendWith(MockitoExtension::class)
internal class OfferPriceVariationValidatorTest {
    @Autowired
    lateinit var offerPriceVariationValidator: OfferPriceVariationValidator

    fun anyOfferRequest(): OfferCreateRequestDTO {
        return OfferCreateRequestDTO(
            assetId = 1,
            quantity = 10.0,
            unitPrice = 100.0,
            userId = 1,
            type = OfferType.BUY,
            created = LocalDateTime.now(),
        )
    }

    fun anyAsset(): Asset {
        return Asset(id = 1, symbol = "ALICEUSDT")
    }

    fun anyAssetPrice(): AssetPrice {
        val now = LocalDateTime.now()
        return AssetPrice(id = 1, asset = anyAsset(), unitPrice = 100.0, created = now, updated = now)
    }

    @Test
    fun `should verify that the creation request is valid`() {
        assertDoesNotThrow {
            offerPriceVariationValidator.isPriceWithinThreshold(anyOfferRequest(), anyAssetPrice())
        }
    }

    @Test
    fun `should throw an exception when the offer price is under five percent than the asset price`() {
        val offer = anyOfferRequest()
        offer.unitPrice = 90.0

        assertThrows(UnitPriceOutOfRangeException::class.java) {
            offerPriceVariationValidator.isPriceWithinThreshold(offer, anyAssetPrice())
        }
    }

    @Test
    fun `should throw an exception when the offer price is over five percent than the asset price`() {
        val offer = anyOfferRequest()
        offer.unitPrice = 110.0

        assertThrows(UnitPriceOutOfRangeException::class.java) {
            offerPriceVariationValidator.isPriceWithinThreshold(offer, anyAssetPrice())
        }
    }

}
