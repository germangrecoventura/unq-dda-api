package ar.edu.unq.desapp.groupb.cryptop2p

import ar.edu.unq.desapp.groupb.cryptop2p.service.*
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder.UserCreateRequestDTOBuilder

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!test")
@Component
class Initializer : CommandLineRunner {
    @Autowired
    lateinit var assetService: AssetService

    @Autowired
    lateinit var offerService: OfferService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var ratingService: UserRatingTransactionService

    @Autowired
    lateinit var transactionService: TransactionService

    private fun loadAssets() {
        assetNames().forEach { asset ->
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
        val userNames = listOf(
            Pair("Homero", "Simpson"),
            Pair("Bart", "Simpson"),
            Pair("Lisa", "Simpson"),
            Pair("Marge", "Simpson"),
            Pair("Maggie", "Simpson"),
            Pair("Ned", "Flanders"),
            Pair("Moe", "Szyslak"),
            Pair("Apu", "Nahasapeemapetilon"),
        )

        userNames.forEach {
            val user = anyUser()
                .withFirstName(it.first)
                .withLastName(it.second)
                .withEmail("${it.first.lowercase()}@springfield.com")
                .withAddress("Evergreen 123")
                .withPassword("Super!")
                .withCVU("0011223344556677889900")
                .withCryptoWallet("12345678")
                .build()
            userService.save(user)
        }
    }

    override fun run(vararg args: String?) {
        loadUsers()
        loadAssets()
    }
}
