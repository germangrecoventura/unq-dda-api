package ar.edu.unq.desapp.groupb.backenddesappapi.service

import ar.edu.unq.desapp.groupb.backenddesappapi.model.User
import ar.edu.unq.desapp.groupb.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun save(user: UserRequestDTO): User {

        // firstname
        if (user.firstName.isNullOrBlank()) {
            throw RuntimeException("The firstname cannot be empty")
        }
        if (ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator.containsNumber(user.firstName)) {
            throw RuntimeException("The firstname cannot contain numbers")
        }
        if (ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator.containsSpecialCharacter(user.firstName)) {
            throw RuntimeException("The firstname cannot contain special characters.")
        }
        if (user.firstName!!.length < 3 || user.firstName!!.length > 30) {
            throw RuntimeException("The firstname must be between 3 and 30 characters")
        }

        // lastname

        if (user.lastName.isNullOrBlank()) {
            throw RuntimeException("The lastname cannot be empty")
        }
        if (ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator.containsNumber(user.lastName)) {
            throw RuntimeException("The lastname cannot contain numbers")
        }
        if (ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator.containsSpecialCharacter(user.lastName)) {
            throw RuntimeException("The lastname cannot contain special characters.")
        }
        if (user.lastName!!.length < 3 || user.lastName!!.length > 30) {
            throw RuntimeException("The lastname must be between 3 and 30 characters")
        }


        // email
        if (user.emailAddress.isNullOrBlank()) {
            throw RuntimeException("The email cannot be empty")
        }

        if (!ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator.isValidEMail(user.emailAddress)) {
            throw RuntimeException("It is not a valid email")
        }

        // adresss
        if (user.address.isNullOrBlank()) {
            throw RuntimeException("The address cannot be empty")
        }
        if (ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator.containsSpecialCharacter(user.address)) {
            throw RuntimeException("The address cannot contain special characters.")
        }

        if (user.address!!.length < 10 || user.address!!.length > 30) {
            throw RuntimeException("The address must be between 3 and 30 characters")
        }

        //password
        if (user.password!!.length < 6) {
            throw RuntimeException("The password must be at least 6 characters")
        }
        if (!isValidPassword(user.password!!)) {
            throw RuntimeException("The password must have at least one lowercase letter, one uppercase letter, and a special character")
        }
        //password

        // cvu
        if (user.cvump.isNullOrBlank()) {
            throw RuntimeException("The CVU cannot be empty")
        }

        if (!ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator.isAllNumbers(user.cvump)) {
            throw RuntimeException("The cvu can only contain numbers.")
        }

        if (user.cvump!!.length != 22) {
            throw RuntimeException("The cvu must have 22 digits")
        }

        // CRYPTO
        if (user.cryptoWalletAddress!!.toString().length != 8) {
            throw RuntimeException("The crypto wallet must have 8 digits")
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


    private fun isValidPassword(password: String): Boolean {
        if (password == null) {
            return false
        }
        return areLowecase(password) && areCapitalLetters(password) && Validator.containsSpecialCharacter(password)
    }

    private fun isCapitalLetter(letter: Char): Boolean {
        return letter === letter.toUpperCase();
    }

    private fun isMinuscule(letter: Char): Boolean {
        return letter === letter.toLowerCase();
    }

    private fun areLowecase(password: String): Boolean {
        return password.find { letter -> isMinuscule(letter) } != null
    }

    private fun areCapitalLetters(password: String): Boolean {
        return password.find { letter -> isCapitalLetter(letter) } != null
    }
}


