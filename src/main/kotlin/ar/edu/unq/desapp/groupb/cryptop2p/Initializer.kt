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
    lateinit var userService: UserService
    @Autowired
    lateinit var offerService: OfferService

    fun cleanDataBase() {
        assetService.clear()
        offerService.clear()
        userService.clear()
    }

    fun loadData() {
        loadAssets()
        loadUsers()
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


    private fun loadUsers() {
        /*val user1 = UserCreateRequestDTOBuilder()
            .withFirstName("Homero")
            .withLastName("Simpson")
            .withEmail("homero.simpson@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Homeroo!")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678").build()
        userService.save(user1)
*/
       /* userService.save(
            UserCreateRequestDTOBuilder()
                .withFirstName("Homero")
                .withLastName("Simpson")
                .withEmail("homero.simpson@sprinfield.com")
                .withAddress("Evergreen 123")
                .withPassword("Super!")
                .withCVU("0011223344556677889900")
                .withCryptoWallet("12345678").build()
        )*/
    }
}