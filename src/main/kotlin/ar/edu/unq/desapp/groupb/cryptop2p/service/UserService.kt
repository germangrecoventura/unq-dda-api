package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.UserValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserCreateRequestDTO
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class UserService(private val userRepository: UserRepository, private val userValidator: UserValidator) {
    fun save(@Valid userCreateRequest: UserCreateRequestDTO): User {
        userValidator.isCreationRequestValid(userCreateRequest.emailAddress!!)
        val newUser = userCreateRequest.toDomain()
        return userRepository.save(newUser)
    }

    fun clear() {
        userRepository.deleteAll()
    }
}
