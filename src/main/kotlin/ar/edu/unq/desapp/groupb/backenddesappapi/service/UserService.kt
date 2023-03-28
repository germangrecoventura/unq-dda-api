package ar.edu.unq.desapp.groupb.backenddesappapi.service

import ar.edu.unq.desapp.groupb.backenddesappapi.model.User
import ar.edu.unq.desapp.groupb.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(val userRepository: UserRepository) {

    fun save(user: UserRequestDTO): User {
        if (user.emailAddress != null) {
            var emailFound = userRepository.findByEmailAddress(user.emailAddress!!)
            if (!emailFound.isEmpty) {
                throw RuntimeException("The email is already registered")
            }
        }
        var newUser = User()
        newUser.fromModel(
            user.firstName,
            user.lastName,
            user.emailAddress,
            user.address,
            user.password,
            user.cvump,
            user.cryptoWalletAddress
        )
        return userRepository.save(newUser)
    }

    fun clear(){
        userRepository.deleteAll()
    }
}


