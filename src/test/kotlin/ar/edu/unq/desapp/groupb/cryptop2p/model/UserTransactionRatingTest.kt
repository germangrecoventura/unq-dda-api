package ar.edu.unq.desapp.groupb.cryptop2p.model

import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class UserTransactionRatingTest {
    @Autowired
    lateinit var validator: Validator

    fun anyUserTransactionRating(): UserTransactionRating {
        val now = LocalDateTime.now()
        return UserTransactionRating(id = 1, user = User(), transaction = Transaction(), rating = 5, created = now)
    }

    @Test
    fun `should create a user transaction rating`() {
        val userTransactionRating = anyUserTransactionRating()
        val violations = validator.validate(userTransactionRating)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should have an error when the user is null`() {
        val userTransactionRating = anyUserTransactionRating()
        userTransactionRating.user = null
        val violations = validator.validate(userTransactionRating)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the transaction is null`() {
        val userTransactionRating = anyUserTransactionRating()
        userTransactionRating.transaction = null
        val violations = validator.validate(userTransactionRating)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the rating is lower than zero`() {
        val userTransactionRating = anyUserTransactionRating()
        userTransactionRating.rating = -1
        val violations = validator.validate(userTransactionRating)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the rating is greater than ten`() {
        val userTransactionRating = anyUserTransactionRating()
        userTransactionRating.rating = 11
        val violations = validator.validate(userTransactionRating)
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should have an error when the created date is null`() {
        val userTransactionRating = anyUserTransactionRating()
        userTransactionRating.created = null
        val violations = validator.validate(userTransactionRating)
        assertTrue(violations.isNotEmpty())
    }
}
