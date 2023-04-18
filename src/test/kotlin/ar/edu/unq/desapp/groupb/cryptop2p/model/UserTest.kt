package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.UserBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserTest {
    @Autowired
    lateinit var validator: Validator

    fun anyUser(): UserBuilder {
        return UserBuilder()
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
        assertDoesNotThrow { anyUser().build() }
    }

    @Test
    fun `should have an error when a user's first name is null`() {
        val user = anyUser().withFirstName(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's first name is blank`() {
        val user = anyUser().withFirstName("").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's first name has numbers`() {
        val user = anyUser().withFirstName("456").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's first name has special characters`() {
        val user = anyUser().withFirstName("Germ@n").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's first name has less than 3 characters`() {
        val user = anyUser().withFirstName("G").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's first name has more than 30 characters`() {
        val user = anyUser().withFirstName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should have an error when a user's las name is null`() {
        val user = anyUser().withLastName(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's las name is blank`() {
        val user = anyUser().withLastName("").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's last name contains numbers`() {
        val user = anyUser().withLastName("Grec0 Ventura").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's last name contains special characters`() {
        val user = anyUser().withLastName("Grec# Ventura").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should have an error when a user's last name has less than 3 characters`() {
        val user = anyUser().withLastName("Gr").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's last name has more than 30 characters`() {
        val user = anyUser().withLastName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's email address is null`() {
        val user = anyUser().withEmail(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's email address is blank`() {
        val user = anyUser().withEmail("").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's address is null`() {
        val user = anyUser().withAddress(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should have an error when a user's email address is not valid`() {
        val user = anyUser().withEmail("testgmailcom").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's address is blank`() {
        val user = anyUser().withAddress("").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user has the field address special characters`() {
        val user = anyUser().withAddress("sas@").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should have an error when a user's address has less than 10 characters`() {
        val user = anyUser().withAddress("sas").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's address has more than 30 characters`() {
        val user = anyUser().withAddress("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's password is null`() {
        val user = anyUser().withPassword(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's password is blank`() {
        val user = anyUser().withPassword("").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's password has less than 6 characters`() {
        val user = anyUser().withPassword("1").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should have an error when a user's password does not have a lower case letter`() {
        val user = anyUser().withPassword("11A1@1").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's password does not have an upper case letter`() {
        val user = anyUser().withPassword("11a1@1").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's password does not have a special character`() {
        val user = anyUser().withPassword("11a1A1").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's CVU is null`() {
        val user = anyUser().withCVU(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should have an error when a user's CVU is blank`() {
        val user = anyUser().withCVU("").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's CVU contains characters`() {
        val user = anyUser().withCVU("111111111a111111111111").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's CVU has less than 22 characters`() {
        val user = anyUser().withCVU("11111111111").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's CVU has more than 22 characters`() {
        val user = anyUser().withCVU("111111111111111111111111").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }


    @Test
    fun `should have an error when a user's crypto wallet address is null`() {
        val user = anyUser().withCryptoWallet(null).build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's crypto wallet has less than 8 characters`() {
        val user = anyUser().withCryptoWallet("4646").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when a user's crypt wallet has more than 8 characters`() {
        val user = anyUser().withCryptoWallet("464646465").build()
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
    }
}
