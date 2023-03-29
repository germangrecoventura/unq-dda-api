package ar.edu.unq.desapp.groupb.backenddesappapi

import ar.edu.unq.desapp.groupb.backenddesappapi.service.UserService
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.builder.BuilderUserDTO.Companion.aUserDTO
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Test
    fun `should add a user when you have valid credentials`() {
        var user = userService.save(aUserDTO().build())

        Assertions.assertTrue(user.getId() != null)
    }

    @Test
    fun `should throw an exception when a user doesn't have firstname`() {
        val userRequest = aUserDTO().withFirstName(null).build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The firstname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty firstname field`() {
        val userRequest = aUserDTO().withFirstName("").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The firstname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field firstname numbers`() {
        val userRequest = aUserDTO().withFirstName("456").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The firstname cannot contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field firstname special characters`() {
        val userRequest = aUserDTO().withFirstName("Germ@n").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The firstname cannot contain special characters", thrown.message)
    }

    @Test
    fun `should throw an exception when firstname has less than 3 characters`() {
        val userRequest = aUserDTO().withFirstName("G").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The firstname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when firstname has more than 30 characters`() {
        val userRequest = aUserDTO().withFirstName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The firstname must be between 3 and 30 characters", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have lastname`() {
        val userRequest = aUserDTO().withLastName(null).build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The lastname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty lastname field`() {
        val userRequest = aUserDTO().withLastName("").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The lastname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field lastname numbers`() {
        val userRequest = aUserDTO().withLastName("Grec0 Ventura").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The lastname cannot contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field lastname special characters`() {
        val userRequest = aUserDTO().withLastName("Grec# Ventura").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The lastname cannot contain special characters", thrown.message)
    }


    @Test
    fun `should throw an exception when lastname has less than 3 characters`() {
        val userRequest = aUserDTO().withLastName("Gr").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The lastname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when lastname has more than 30 characters`() {
        val userRequest = aUserDTO().withLastName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The lastname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when a user doesn't have email`() {
        val userRequest = aUserDTO().withEmail(null).build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The email cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty email field`() {
        val userRequest = aUserDTO().withEmail("").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The email cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when it is not a valid mail`() {
        val userRequest = aUserDTO().withEmail("pruebagmailcom").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The email is not valid", thrown.message)
    }

    @Test
    fun `should throw an exception if you register with an already registered email`() {
        val userRequest1 = aUserDTO().build()
        val userRequest2 = aUserDTO().withEmail(userRequest1.emailAddress).build()
        userService.save(userRequest1)

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest2) }

        assertEquals("The email is already registered", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have empty address`() {
        val userRequest = aUserDTO().withAddress(null).build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The address cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty address field`() {
        val userRequest = aUserDTO().withAddress("").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The address cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field address special characters`() {
        val userRequest = aUserDTO().withAddress("sas@").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The address cannot contain special characters", thrown.message)
    }


    @Test
    fun `should throw an exception when address has less than 10 characters`() {
        val userRequest = aUserDTO().withAddress("sas").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The address must be between 10 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 30 characters`() {
        val userRequest = aUserDTO().withAddress("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The address must be between 10 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when a user doesn't have password`() {
        val userRequest = aUserDTO().withPassword(null).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The password cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty password field`() {
        val userRequest = aUserDTO().withPassword("").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The password cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when password has less than 6 characters`() {
        val userRequest = aUserDTO().withPassword("1").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The password must be at least 6 characters", thrown.message)
    }


    @Test
    fun `should throw an exception when the password does not have a lower case`() {
        val userRequest = aUserDTO().withPassword("11A1@1").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when the password does not have a capital letter`() {
        val userRequest = aUserDTO().withPassword("11a1@1").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when the password does not have a special character`() {
        val userRequest = aUserDTO().withPassword("11a1A1").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }

    //PASSWORD

    @Test
    fun `should throw an exception when a user doesn't have cvu`() {
        val userRequest = aUserDTO().withCvu(null).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The CVU cannot be empty", thrown.message)
    }


    @Test
    fun `should throw an exception when a user has an empty cvu field`() {
        val userRequest = aUserDTO().withCvu("").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The CVU cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the cvu does not only have numbers`() {
        val userRequest = aUserDTO().withCvu("111111111a111111111111").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The CVU can only contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when cvu has less than 22 characters`() {
        val userRequest = aUserDTO().withCvu("11111111111").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The CVU must have 22 digits", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 22 characters`() {
        val userRequest = aUserDTO().withCvu("111111111111111111111111").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The CVU must have 22 digits", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have crypto wallet`() {
        val userRequest = aUserDTO().withCryptoWallet(null)
            .build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when crypto wallet has less than 8 characters`() {
        val userRequest = aUserDTO().withCryptoWallet(4646).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The crypto wallet must have 8 digits", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 8 characters`() {
        val userRequest = aUserDTO().withCryptoWallet(464646465).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("The crypto wallet must have 8 digits", thrown.message)
    }

    @AfterEach
    fun clear() {
        userService.clear()
    }

}
