package ar.edu.unq.desapp.groupb.cryptop2p.model.validator

import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferCreateRequestDTO
import org.springframework.stereotype.Component

@Component
class OfferPriceVariationValidator {

    fun isPriceWithinThreshold(offerRequest: OfferCreateRequestDTO, assetPrice: AssetPrice): Boolean {
        val minPrice = assetPrice.unitPrice * 0.95
        val maxPrice = assetPrice.unitPrice * 1.05
        if (minPrice > offerRequest.unitPrice!! || offerRequest.unitPrice!! > maxPrice) {
            throw UnitPriceOutOfRangeException("offer.unitPrice", minPrice, maxPrice)
        }
        return true
    }

}

class UnitPriceOutOfRangeException(source: String, minPrice: Double, maxPrice: Double) :
    ModelException("The offer price must be between $ $minPrice and $ $maxPrice", source)
