package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.service.AssetService
import ar.edu.unq.desapp.groupb.cryptop2p.service.ExchangeService
import ar.edu.unq.desapp.groupb.cryptop2p.service.OfferService
import ar.edu.unq.desapp.groupb.cryptop2p.service.UserService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder.OfferCreateRequestDTOBuilder
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder.UserCreateRequestDTOBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringBootTest
@ExtendWith(SpringExtension::class)
class OfferControllerTest {
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var offerService: OfferService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var assetService: AssetService

    @MockBean
    @Autowired
    lateinit var exchangeService: ExchangeService

    @Autowired
    lateinit var context: WebApplicationContext
    private val mapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        offerService.clear()
        userService.clear()
        assetService.clear()
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

        val assetName = "ALICEUSDT"

        Mockito
            .`when`(exchangeService.getCryptoAssetPrice(assetName))
            .thenReturn(1.0);

        assetService.save("ALICEUSDT")
    }

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

    fun anyOffer(): OfferCreateRequestDTOBuilder {
        return OfferCreateRequestDTOBuilder()
            .withAsset("ALICEUSDT")
            .withQuantity(2.0)
            .withUnitPrice(1.0)
            .withUser(null)
            .withOperation("BUY")
    }

    @Test
    fun `should throw a 200 status when a valid offer is created`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isOk)
    }

    @Test
    fun `should throw a 400 status when a asset is null`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withAsset(null).build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a asset is empty`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withAsset("").build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a asset does not respect the valid format`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withAsset("ae").build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a quantity is negative`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withQuantity(-10.0).build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a unit price is negative`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withUnitPrice(-10.0).build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a user is null`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withUser(null).build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when the user is not registered`() {
        val offer = anyOffer().withUser(20).build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when operation type is null`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withOperation(null).build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when the type of operation is empty`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withOperation("").build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when the type of operation is not valid`() {
        val user = userService.save(anyUser().build())
        val offer = anyOffer().withUser(user.id).withOperation("INVALID_OPERATION").build()

        mockMvc.perform(
            post("/offers")
                .contentType("application/json")
                .content(mapper.writeValueAsString(offer))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }
}
