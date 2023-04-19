package ar.edu.unq.desapp.groupb.cryptop2p.model.validator

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import org.springframework.stereotype.Component

@Component
class UserValidator(val userRepository: UserRepository) {
    fun isCreationRequestValid(emailAddress: String): Boolean {
        val optionalUser = userRepository.findByEmailAddress(emailAddress)
        if (!optionalUser.isEmpty) {
            throw UserEmailAddressAlreadyRegisteredException()
        }
        return true
    }
}

class UserEmailAddressAlreadyRegisteredException :
    ModelException("The email address is already registered", "user.emailAddress")
