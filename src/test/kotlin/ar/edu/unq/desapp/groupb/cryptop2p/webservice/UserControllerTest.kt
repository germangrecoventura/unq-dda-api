package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.Initializer
import ar.edu.unq.desapp.groupb.cryptop2p.service.UserService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder.UserCreateRequestDTOBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class UserControllerTest {
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var context: WebApplicationContext
    private val mapper = ObjectMapper()

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var initializer: Initializer

    @BeforeEach
    fun setUp() {
        initializer.cleanDataBase()
        userService.clear()
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
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

    @Test
    fun `should throw a 200 status when a valid user is created`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().build()))
                .accept("application/json")
        ).andExpect(status().isOk)
    }

    @Test
    fun `should throw a 400 status when inserting a user with null firstname`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withFirstName(null).build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with empty firstname`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withFirstName("").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with numbers in the firstname`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withFirstName("G3rman").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with special characters in the firstname`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withFirstName("Ger@n").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a firstname less than 3 characters`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withFirstName("Ge").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a firstname greater than 30 characters`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        anyUser().withFirstName("Geeeeeeeeerrrrrrrrrrmaaaaaaaaaaaaannnn").build()
                    )
                )
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with null lastname`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withLastName(null).build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with empty lastname`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withLastName("").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with numbers in the lastname`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withLastName("G3rman").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with special characters in the lastname`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withLastName("Ger@n").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a lastname less than 3 characters`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withLastName("Ge").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a lastname greater than 30 characters`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        anyUser().withLastName("Geeeeeeeeerrrrrrrrrrmaaaaaaaaaaaaannnn").build()
                    )
                )
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with null email`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withEmail(null).build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with empty email`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withEmail("").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with an invalid email`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withEmail("josecom").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with null address`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withAddress(null).build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with empty address`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withAddress("").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }


    @Test
    fun `should throw a 400 status when inserting a user with special characters in the address`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withAddress("Andradee @").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a address less than 10 characters`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withAddress("Ge").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a address greater than 30 characters`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        anyUser().withAddress("Geeeeeeeeerrrrrrrrrrmaaaaaaaaaaaaannnn").build()
                    )
                )
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }


    @Test
    fun `should throw a 400 status when inserting a user with null password`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withPassword(null).build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with empty password`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withPassword("").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }


    @Test
    fun `should throw a 400 status when a password less than 6 characters is entered`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withPassword("5d").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }


    @Test
    fun `should throw a 400 status when a password without at least 1 lowercase is entered`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withPassword("12AS#D").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a password without at least 1 capital letter is entered`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withPassword("12aa#d").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a password is entered without at least 1 special character`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withPassword("12aaAd").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with null CVU`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCVU(null).build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with empty CVU`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCVU("").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a CVU other than just numbers is inserted`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCVU("11111111111111111111sa").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a CVU less than 22 digit`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCVU("11").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a CVU greater than 30 characters`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCVU("111111111111111111111111111111").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with null crypto wallet`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCryptoWallet(null).build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with empty crypto wallet`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCryptoWallet("").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when a crypto wallet other than just numbers is inserted`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCryptoWallet("1111111a").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a crypto wallet less than 8 digit`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCryptoWallet("11").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a 400 status when inserting a user with a crypto wallet greater than 8 characters`() {
        mockMvc.perform(
            post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(anyUser().withCryptoWallet("111111111").build()))
                .accept("application/json")
        ).andExpect(status().isBadRequest)
    }


}
