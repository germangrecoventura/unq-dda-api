package ar.edu.unq.desapp.groupb.cryptop2p.model.validator

import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder.UserCreateRequestDTOBuilder
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class UserValidatorTest {
    @MockBean
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userValidator: UserValidator


    fun anyUserCreationRequest(): UserCreateRequestDTOBuilder {
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
    fun `should verify that the creation request is valid`() {
        assertDoesNotThrow {
            userValidator.isCreationRequestValid(anyUserCreationRequest().build())
        }
    }

    @Test
    fun `should throw an exception when the user is already registered`() {
        val userCreationRequest = anyUserCreationRequest().build()
        val optionalUser = Optional.of(userCreationRequest.toDomain())

        Mockito
            .`when`(userRepository.findByEmailAddress(userCreationRequest.emailAddress!!))
            .thenReturn(optionalUser)

        assertThrows(UserEmailAddressAlreadyRegisteredException::class.java) {
            userValidator.isCreationRequestValid(anyUserCreationRequest().build())
        }
    }
}
