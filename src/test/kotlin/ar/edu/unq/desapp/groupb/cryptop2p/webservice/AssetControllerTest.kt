package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.service.AssetService
import ar.edu.unq.desapp.groupb.cryptop2p.service.ExchangeService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ExtendWith(MockitoExtension::class)
@WithMockUser
class AssetControllerTest {
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var assetService: AssetService

    @MockBean
    @Autowired
    lateinit var exchangeService: ExchangeService

    @Autowired
    lateinit var context: WebApplicationContext

    @BeforeEach
    fun setUp() {
        assetService.clear()
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

        val assetName = "ALICEUSDT"

        Mockito
            .`when`(exchangeService.getCryptoAssetPrice(assetName))
            .thenReturn(1.31200000)
    }

    @Test
    fun `should throw a 200 status when a valid asset is created`() {
        mockMvc.perform(
            post("/assets")
                .contentType("application/json")
                .param("assetName", "ALICEUSDT")
        ).andExpect(status().isOk)
    }

    @Test
    fun `should throw a 400 status when the asset name does not respect the format`() {
        mockMvc.perform(
            post("/assets")
                .contentType("application/json")
                .param("assetName", "aliCEUSDT")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when the asset name is null`() {
        mockMvc.perform(
            post("/assets")
                .contentType("application/json")
                .param("assetName", null)
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when the asset name is empty`() {
        mockMvc.perform(
            post("/assets")
                .contentType("application/json")
                .param("assetName", "")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when the asset is already registered`() {
        assetService.save("ALICEUSDT")
        mockMvc.perform(
            post("/assets")
                .contentType("application/json")
                .param("assetName", "ALICEUSDT")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 200 status when returns the list of empty asset price`() {
        mockMvc.perform(
            get("/assets/prices")
                .contentType("application/json")
        ).andExpect(status().isOk)
    }
}
