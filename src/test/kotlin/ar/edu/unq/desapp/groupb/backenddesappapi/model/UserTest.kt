package ar.edu.unq.desapp.groupb.backenddesappapi.model

import ar.edu.unq.desapp.groupb.backenddesappapi.model.builder.BuilderUser.Companion.aUser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserTest {

    @Test
    fun `should create the user when it has valid credentials`() {
        assertDoesNotThrow { aUser() }
    }

    @Test
    fun `should throw an exception when a user doesn't have firstname`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withFirstName(null).build() }

        assertEquals("The firstname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty firstname field`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withFirstName("").build() }

        assertEquals("The firstname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field firstname numbers`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withFirstName("456").build() }

        assertEquals("The firstname cannot contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field firstname special characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withFirstName("Germ@n").build() }

        assertEquals("The firstname cannot contain special characters", thrown.message)
    }

    @Test
    fun `should throw an exception when firstname has less than 3 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withFirstName("G").build() }

        assertEquals("The firstname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when firstname has more than 30 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) {
                aUser().withFirstName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
            }

        assertEquals("The firstname must be between 3 and 30 characters", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have lastname`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withLastName(null).build() }

        assertEquals("The lastname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty lastname field`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withLastName("").build() }

        assertEquals("The lastname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field lastname numbers`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withLastName("Grec0 Ventura").build() }

        assertEquals("The lastname cannot contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field lastname special characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withLastName("Grec# Ventura").build() }

        assertEquals("The lastname cannot contain special characters", thrown.message)
    }


    @Test
    fun `should throw an exception when lastname has less than 3 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withLastName("Gr").build() }

        assertEquals("The lastname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when lastname has more than 30 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) {
                aUser().withLastName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
            }

        assertEquals("The lastname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when a user doesn't have email`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withEmail(null).build() }

        assertEquals("The email cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty email field`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withEmail("").build() }

        assertEquals("The email cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when it is not a valid mail`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withEmail("pruebagmailcom").build() }

        assertEquals("The email is not valid", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have empty address`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withAddress(null).build() }

        assertEquals("The address cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty address field`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withAddress("").build() }

        assertEquals("The address cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field address special characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withAddress("sas@").build() }

        assertEquals("The address cannot contain special characters", thrown.message)
    }


    @Test
    fun `should throw an exception when address has less than 10 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withAddress("sas").build() }

        assertEquals("The address must be between 10 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 30 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) {
                aUser().withAddress("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
            }

        assertEquals("The address must be between 10 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when a user doesn't have password`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withPassword(null).build() }

        assertEquals("The password cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty password field`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withPassword("").build() }

        assertEquals("The password cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when password has less than 6 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withPassword("1").build() }

        assertEquals("The password must be at least 6 characters", thrown.message)
    }


    @Test
    fun `should throw an exception when the password does not have a lower case`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withPassword("11A1@1").build() }

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when the password does not have a capital letter`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withPassword("11a1@1").build() }

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when the password does not have a special character`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withPassword("11a1A1").build() }

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when a user doesn't have cvu`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withCvu(null).build() }

        assertEquals("The CVU cannot be empty", thrown.message)
    }


    @Test
    fun `should throw an exception when a user has an empty cvu field`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withCvu("").build() }

        assertEquals("The CVU cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the cvu does not only have numbers`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) {
                aUser().withCvu("111111111a111111111111").build()
            }

        assertEquals("The CVU can only contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when cvu has less than 22 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withCvu("11111111111").build() }

        assertEquals("The CVU must have 22 digits", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 22 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) {
                aUser().withCvu("111111111111111111111111").build()
            }

        assertEquals("The CVU must have 22 digits", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have crypto wallet`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withCryptoWallet(null).build() }

        assertEquals("The crypto wallet must have 8 digits", thrown.message)
    }

    @Test
    fun `should throw an exception when crypto wallet has less than 8 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withCryptoWallet(4646).build() }

        assertEquals("The crypto wallet must have 8 digits", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 8 characters`() {
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java) { aUser().withCryptoWallet(464646465).build() }

        assertEquals("The crypto wallet must have 8 digits", thrown.message)
    }
}
