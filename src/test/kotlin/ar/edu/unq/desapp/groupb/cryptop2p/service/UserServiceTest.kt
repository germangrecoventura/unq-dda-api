package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder.UserDTOBuilder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    fun anyUser(): UserDTOBuilder {
        return UserDTOBuilder()
            .withFirstName("Homero")
            .withLastName("Simpson")
            .withEmail("homero.simpson@sprinfield.com")
            .withAddress("Evergreen 123")
            .withPassword("Super!")
            .withCVU("0011223344556677889900")
            .withCryptoWallet("12345678")
    }

    @Test
    fun `should create a user when it has valid data`() {
        var user = userService.save(anyUser().build())

        assertTrue(user.id != null)
    }

    @Test
    fun `should throw an exception when a user's first name is null`() {
        val userRequest = anyUser().withFirstName(null).build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.firstName: The first name cannot be blank", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's first name is empty`() {
        val userRequest = anyUser().withFirstName("").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        val expected = listOf(
            "save.user.firstName: The first name must be between 3 and 30 characters long",
            "save.user.firstName: The first name cannot contain special characters or numbers",
            "save.user.firstName: The first name cannot be blank",
        )
        assertTrue(expected.all { thrown.message!!.contains(it) })
    }

    @Test
    fun `should throw an exception when a user's first name contains numbers`() {
        val userRequest = anyUser().withFirstName("456").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.firstName: The first name cannot contain special characters or numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's first name contains special characters`() {
        val userRequest = anyUser().withFirstName("Germ@n").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.firstName: The first name cannot contain special characters or numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when firstname has less than 3 characters`() {
        val userRequest = anyUser().withFirstName("G").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.firstName: The first name must be between 3 and 30 characters long", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's first name has more than 30 characters`() {
        val userRequest = anyUser().withFirstName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.firstName: The first name must be between 3 and 30 characters long", thrown.message)
    }


    @Test
    fun `should throw an exception when a user's last name is null`() {
        val userRequest = anyUser().withLastName(null).build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.lastName: The last name cannot be blank", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's last name is blank`() {
        val userRequest = anyUser().withLastName("").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        val expected = listOf(
            "save.user.lastName: The last name cannot contain special characters or numbers",
            "save.user.lastName: The last name must be between 3 and 30 characters long",
            "save.user.lastName: The last name cannot be blank",
        )
        assertTrue(expected.all { thrown.message!!.contains(it) })
    }

    @Test
    fun `should throw an exception when a user's last name contains numbers`() {
        val userRequest = anyUser().withLastName("Grec0 Ventura").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.lastName: The last name cannot contain special characters or numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's last name contains special characters`() {
        val userRequest = anyUser().withLastName("Grec# Ventura").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.lastName: The last name cannot contain special characters or numbers", thrown.message)
    }


    @Test
    fun `should throw an exception when a user's last name has less than 3 characters`() {
        val userRequest = anyUser().withLastName("Gr").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.lastName: The last name must be between 3 and 30 characters long", thrown.message)
    }

    @Test
    fun `should throw an exception when lastname has more than 30 characters`() {
        val userRequest = anyUser().withLastName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.lastName: The last name must be between 3 and 30 characters long", thrown.message)
    }

    @Test
    fun `should throw an exception when a user doesn't have email`() {
        val userRequest = anyUser().withEmail(null).build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.emailAddress: The email address cannot be blank", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty email field`() {
        val userRequest = anyUser().withEmail("").build()
        val thrown: RuntimeException =
            assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.emailAddress: The email address cannot be blank", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's email address is not valid`() {
        val userRequest = anyUser().withEmail("pruebagmailcom").build()
        val thrown: RuntimeException =
            assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.emailAddress: The email address is not valid", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's email is already registered`() {
        val userRequest1 = anyUser().build()
        val userRequest2 = anyUser().withEmail(userRequest1.emailAddress).build()
        userService.save(userRequest1)

        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest2) }

        assertEquals("The email address is already registered", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have empty address`() {
        val userRequest = anyUser().withAddress(null).build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.address: The address cannot be blank", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty address`() {
        val userRequest = anyUser().withAddress("").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        val expected = listOf(
            "save.user.address: The address must be between 10 and 30 characters long",
            "save.user.address: The address cannot contain special characters or numbers",
            "save.user.address: The address cannot be blank",
        )
        assertTrue(expected.all { thrown.message!!.contains(it) })
    }

    @Test
    fun `should throw an exception when a user has the field address special characters`() {
        val userRequest = anyUser().withAddress("Evergreen 1234@").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.address: The address cannot contain special characters or numbers", thrown.message)
    }


    @Test
    fun `should throw an exception when address has less than 10 characters`() {
        val userRequest = anyUser().withAddress("Ever").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.address: The address must be between 10 and 30 characters long", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 30 characters`() {
        val userRequest = anyUser().withAddress("Evergreen 1234 Evergreen 1234 Evergreen 1234").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.address: The address must be between 10 and 30 characters long", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's password is null`() {
        val userRequest = anyUser().withPassword(null).build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.password: The password cannot be blank", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's password is empty`() {
        val userRequest = anyUser().withPassword("").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        val expected = listOf(
            "save.user.password: The password cannot be blank",
            "save.user.password: The password must have at least one lowercase letter, one uppercase letter and a special character",
            "save.user.password: The password must be at least 6 characters long",
        )
        assertTrue(expected.all { thrown.message!!.contains(it) })
    }

    @Test
    fun `should throw an exception when a user's password has less than 6 characters`() {
        val userRequest = anyUser().withPassword("1").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        val expected = listOf(
            "save.user.password: The password must have at least one lowercase letter, one uppercase letter and a special character",
            "save.user.password: The password must be at least 6 characters long",
        )
        assertTrue(expected.all { thrown.message!!.contains(it) })
    }


    @Test
    fun `should throw an exception when a user's password does not contain a lowercase character`() {
        val userRequest = anyUser().withPassword("11A1@1").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals(
            "save.user.password: The password must have at least one lowercase letter, one uppercase letter and a special character",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when a user's password does not contain a uppercase character`() {
        val userRequest = anyUser().withPassword("11a1@1").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals(
            "save.user.password: The password must have at least one lowercase letter, one uppercase letter and a special character",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when a user's password does not contain a special character`() {
        val userRequest = anyUser().withPassword("11a1A1").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals(
            "save.user.password: The password must have at least one lowercase letter, one uppercase letter and a special character",
            thrown.message
        )
    }


    @Test
    fun `should throw an exception when a user's CVU is null`() {
        val userRequest = anyUser().withCVU(null).build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.cvu: The CVU cannot be blank", thrown.message)
    }


    @Test
    fun `should throw an exception when a user's CVU is blank`() {
        val userRequest = anyUser().withCVU("").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        val expected = listOf(
            "save.user.cvu: The CVU must be 22 digits long",
            "save.user.cvu: The CVU cannot be blank",
            "save.user.cvu: The CVU can only contain numbers"
        )
        assertTrue(expected.all { thrown.message!!.contains(it) })
    }

    @Test
    fun `should throw an exception when a user's CVU contains characters`() {
        val userRequest = anyUser().withCVU("111111111a111111111111").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.cvu: The CVU can only contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's cvu has less than 22 characters`() {
        val userRequest = anyUser().withCVU("11111111111").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.cvu: The CVU must be 22 digits long", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's address has more than 22 characters`() {
        val userRequest = anyUser().withCVU("111111111111111111111111").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.cvu: The CVU must be 22 digits long", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's crypto wallet address is null`() {
        val userRequest = anyUser().withCryptoWallet(null).build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.cryptoWalletAddress: The crypto wallet address cannot be blank", thrown.message)
    }

    @Test
    fun `should throw an exception when user's crypto wallet has less than 8 characters`() {
        val userRequest = anyUser().withCryptoWallet("4646").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.cryptoWalletAddress: The crypto wallet address must be 8 digits long", thrown.message)
    }

    @Test
    fun `should throw an exception when a user's address has more than 8 characters`() {
        val userRequest = anyUser().withCryptoWallet("464646465").build()
        val thrown = assertThrows(RuntimeException::class.java) { userService.save(userRequest) }

        assertEquals("save.user.cryptoWalletAddress: The crypto wallet address must be 8 digits long", thrown.message)
    }

    @AfterEach
    fun clear() {
        userService.clear()
    }
}