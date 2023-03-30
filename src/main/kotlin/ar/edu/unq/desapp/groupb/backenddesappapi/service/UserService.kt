package ar.edu.unq.desapp.groupb.backenddesappapi.service

import ar.edu.unq.desapp.groupb.backenddesappapi.model.User
import ar.edu.unq.desapp.groupb.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.validation.Validator
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
@Transactional
class UserService(val userRepository: UserRepository, val validator: Validator) {

    fun save(@Valid user: UserRequestDTO): User {
        val emailFound = userRepository.findByEmailAddress(user.emailAddress!!)
        if (!emailFound.isEmpty) {
            throw UserEmailAddressAlreadyRegisteredException()
        }
        val newUser = User.fromDTO(user)
        return userRepository.save(newUser)
    }

    fun clear() {
        userRepository.deleteAll()
    }
}

class UserEmailAddressAlreadyRegisteredException : RuntimeException("The email address is already registered") {
    val source = "user.email"
}


