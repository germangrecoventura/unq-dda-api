package ar.edu.unq.desapp.groupb.backenddesappapi.service

import ar.edu.unq.desapp.groupb.backenddesappapi.model.User
import ar.edu.unq.desapp.groupb.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun save(user: UserRequestDTO): User{
        var newUser = User()
        newUser.fromModel(user.firstName,user.lastName,user.emailAddress,user.address,user.password,user.cvump,user.cyptoWalletAddress)
        return userRepository.save(newUser)
    }

}