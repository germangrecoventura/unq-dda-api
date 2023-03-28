package ar.edu.unq.desapp.groupb.backenddesappapi.model

import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.Validator
import jakarta.persistence.*

@Entity
@Table(name = "registered_user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null

    @Column(nullable = false, length = 30)
    private var firstName: String? = null

    @Column(nullable = false, length = 30)
    private var lastName: String? = null

    @Column(nullable = false, unique = true)
    private var emailAddress: String? = null

    @Column(nullable = false, length = 30)
    private var address: String? = null

    @Column(nullable = false)
    private var password: String? = null

    @Column(nullable = false, length = 22)
    private var cvump: String? = null

    @Column(nullable = false, length = 8)
    private var cryptoWalletAddress: Int? = null

    fun getId(): Long? {
        return id
    }

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String?) {
        validatePerson(firstName, "firstname")
        this.firstName = firstName
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String?) {
        validatePerson(lastName, "lastname")
        this.lastName = lastName
    }

    fun getEmailAddress(): String? {
        return emailAddress
    }

    fun setEmailAddress(emailAddress: String?) {
        validateEmail(emailAddress)
        this.emailAddress = emailAddress
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        validateAddress(address)
        this.address = address
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        validatePassword(password)
        this.password = password
    }

    fun getCvump(): String? {
        return cvump
    }

    fun setCvump(cvu: String?) {
        validateCVU(cvu)
        this.cvump = cvu
    }

    fun getCryptoWalletAddress(): Int? {
        return cryptoWalletAddress

    }

    fun setCryptoWalletAddress(cryptoWalletAddress: Int?) {
        validateCryptoWallet(cryptoWalletAddress)
        this.cryptoWalletAddress = cryptoWalletAddress
    }

    fun fromModel(
        firstName: String?,
        lastName: String?,
        email: String?,
        address: String?,
        password: String?,
        cvump: String?,
        cryptoWalletAddress: Int?
    ) {
        setFirstName(firstName)
        setLastName(lastName)
        setEmailAddress(email)
        setAddress(address)
        setPassword(password)
        setCvump(cvump)
        setCryptoWalletAddress(cryptoWalletAddress)
    }

    private fun validatePerson(element: String?, field: String) {
        if (element.isNullOrBlank()) {
            throw RuntimeException("The $field cannot be empty")
        }
        if (Validator.containsNumber(element)) {
            throw RuntimeException("The $field cannot contain numbers")
        }
        if (Validator.containsSpecialCharacter(element)) {
            throw RuntimeException("The $field cannot contain special characters")
        }
        if (element.length < 3 || element.length > 30) {
            throw RuntimeException("The $field must be between 3 and 30 characters")
        }
    }

    private fun validateEmail(email_address: String?) {
        if (email_address.isNullOrBlank()) {
            throw RuntimeException("The email cannot be empty")
        }
        if (!Validator.isValidEMail(email_address)) {
            throw RuntimeException("The email is not valid")
        }
    }

    private fun validateAddress(address: String?) {
        if (address.isNullOrBlank()) {
            throw RuntimeException("The address cannot be empty")
        }
        if (Validator.containsSpecialCharacter(address)) {
            throw RuntimeException("The address cannot contain special characters")
        }
        if (address.length < 10 || address.length > 30) {
            throw RuntimeException("The address must be between 10 and 30 characters")
        }
    }

    private fun validatePassword(password: String?) {
        if (password.isNullOrBlank()) {
            throw RuntimeException("The password cannot be empty")
        }
        if (password.length < 6) {
            throw RuntimeException("The password must be at least 6 characters")
        }
        if (!isValidPassword(password)) {
            throw RuntimeException("The password must have at least one lowercase letter, one uppercase letter, and a special character")
        }
    }

    private fun validateCVU(cvu: String?) {
        if (cvu.isNullOrBlank()) {
            throw RuntimeException("The CVU cannot be empty")
        }

        if (!Validator.isAllNumbers(cvu)) {
            throw RuntimeException("The CVU can only contain numbers")
        }

        if (cvu.length != 22) {
            throw RuntimeException("The CVU must have 22 digits")
        }
    }

    private fun validateCryptoWallet(cryptoWalletAddress: Int?) {
        if (cryptoWalletAddress.toString().length != 8) {
            throw RuntimeException("The crypto wallet must have 8 digits")
        }
    }

    private fun isValidPassword(password: String): Boolean {
        if (password == null) {
            return false
        }
        return (areLowecase(password) && areCapitalLetters(password)) && Validator.containsSpecialCharacter(password)
    }

    private fun isCapitalLetter(letter: Char): Boolean {
        return letter.isUpperCase()
    }

    private fun isMinuscule(letter: Char): Boolean {
        return letter.isLowerCase()
    }

    private fun areLowecase(password: String): Boolean {
        return password.find { letter -> isMinuscule(letter) } != null
    }

    private fun areCapitalLetters(password: String): Boolean {
        return password.find { letter -> isCapitalLetter(letter) } != null
    }


    class Builder {
        private val user = User()
        fun whitFirstName(firstName: String) = apply { user.setFirstName(firstName) }
        fun whitLastName(lastName: String) = apply { user.setLastName(lastName) }
        fun whitEmail(email: String) = apply { user.setEmailAddress(email) }
        fun whitAddress(address: String) = apply { user.setAddress(address) }
        fun whitPassword(password: String) = apply { user.setPassword(password) }
        fun whitCVU(cvuUpdate: String) = apply { user.setCvump(cvuUpdate) }
        fun whitCryptoWallet(cryptoWalletAddress: Int) = apply { user.setCryptoWalletAddress(cryptoWalletAddress) }
        fun build() = user
    }
}
