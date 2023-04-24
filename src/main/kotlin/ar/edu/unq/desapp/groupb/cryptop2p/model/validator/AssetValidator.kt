package ar.edu.unq.desapp.groupb.cryptop2p.model.validator

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import org.springframework.stereotype.Component

@Component
class AssetValidator(val assetRepository: AssetRepository) {
    private val assets = mutableSetOf(
        "ALICEUSDT",
        "MATICUSDT",
        "AXSUSDT",
        "AAVEUSDT",
        "ATOMUSDT",
        "NEOUSDT",
        "DOTUSDT",
        "ETHUSDT",
        "CAKEUSDT",
        "BTCUSDT",
        "BNBUSDT",
        "ADAUSDT",
        "TRXUSDT",
        "AUDIOUSDT"
    )

    fun isCreationRequestValid(assetName: String): Boolean {
        if (!assets.contains(assetName)) throw AssetNotBelongingToSystemException()
        val optionalAsset = assetRepository.findByName(assetName)
        if (!optionalAsset.isEmpty) {
            throw AssetNameAlreadyRegisteredException()
        }
        return true
    }
}

class AssetNotBelongingToSystemException :
    ModelException("The asset does not belong to the system", "asset.name")

class AssetNameAlreadyRegisteredException :
    ModelException("The asset name is already registered", "asset.name")
