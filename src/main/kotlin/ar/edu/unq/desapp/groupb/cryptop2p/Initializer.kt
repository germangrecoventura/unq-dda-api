package ar.edu.unq.desapp.groupb.cryptop2p

import ar.edu.unq.desapp.groupb.cryptop2p.service.AssetService
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

    fun cleanDataBase() {
        assetService.clear()
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
        fun anyUser(): UserCreateRequestDTOBuilder {
            return UserCreateRequestDTOBuilder()
                .withFirstName("Homero")
                .withLastName("Simpson")
                .withEmail("homero.simpson@sprinfield.com")
                .withAddress("Evergreen 123")
                .withPassword("Super!")
                .withCVU("0011223344556677889900")
                .withCryptoWallet("12345678")
        }
        userService.save(anyUser().build())
        userService.save(
            anyUser().withFirstName("German")
                .withLastName("Greco")
                .withEmail("german@gmail.com")
                .withAddress("Andrade 40")
                .withPassword("Super!")
                .withCVU("0011223344556677289900")
                .withCryptoWallet("12355678").build()
        )
        userService.save(
            anyUser().withFirstName("Jose")
                .withLastName("Fernandez")
                .withEmail("fer@gmail.com")
                .withAddress("Sarmiento 820")
                .withPassword("Super!")
                .withCVU("0011222344556677289900")
                .withCryptoWallet("12355638").build()
        )
    }
}