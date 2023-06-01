package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.UserValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserTransactionRatingRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserCreateRequestDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserDTO
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import kotlin.math.max

@Service
@Validated
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator,
    private val userTransactionRatingRepository: UserTransactionRatingRepository
) {
    fun save(@Valid userCreateRequest: UserCreateRequestDTO): User {
        userValidator.isCreationRequestValid(userCreateRequest)
        val newUser = userCreateRequest.toDomain()
        return userRepository.save(newUser)
    }

    fun getAll(): List<UserDTO> {
        val users = userRepository.findAll()
        val userIds = users.map { it.id!! }.toSet()
        val userRatings = userTransactionRatingRepository.getByUserIn(userIds)
        val ratingsByUser = userRatings.associateBy { it.userId }

        val usersDTOs = users.map {
            val userRating = ratingsByUser.getOrDefault(it.id!!, null)
            var rating = 0.0
            var totalOperations = 0
            if (userRating != null) {
                rating = max(userRating.rating, 0.0)
                totalOperations = userRating.totalOperations
            }
            val printableUserRating = if (rating == 0.0) "No Operations" else rating.toString()
            UserDTO(
                it.id!!,
                it.firstName!!,
                it.lastName!!,
                totalOperations,
                printableUserRating,
            )
        }
        return usersDTOs
    }

    fun clear() {
        userRepository.deleteAll()
    }
}
