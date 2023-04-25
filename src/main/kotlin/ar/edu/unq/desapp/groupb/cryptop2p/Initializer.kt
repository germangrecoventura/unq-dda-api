package ar.edu.unq.desapp.groupb.cryptop2p

import ar.edu.unq.desapp.groupb.cryptop2p.service.AssetService
import ar.edu.unq.desapp.groupb.cryptop2p.service.OfferService
import ar.edu.unq.desapp.groupb.cryptop2p.service.UserService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder.UserCreateRequestDTOBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Initializer {
    @Autowired
    lateinit var assetService: AssetService

    @Autowired
    lateinit var offerService: OfferService

    @Autowired
    lateinit var userService: UserService


    fun cleanDataBase() {
        offerService.clear()
        userService.clear()
        assetService.clear()
    }

    fun loadData() {
       // loadUsers()
        loadAssets()
    }


    private fun loadAssets() {
        val assets = mutableSetOf(
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

        assets.forEach { asset ->
            assetService.save(asset)
        }
    }

    private fun anyUser(): UserCreateRequestDTOBuilder {
        return UserCreateRequestDTOBuilder()
            .withFirstName("Homero")
            .withLastName("Simpson")
            .withEmail("homero.simpson@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Hom#ero")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678")
    }

    private fun loadUsers() {
        userService.save(anyUser().build())
    }
}