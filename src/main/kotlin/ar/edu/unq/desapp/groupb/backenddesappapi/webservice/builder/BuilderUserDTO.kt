package ar.edu.unq.desapp.groupb.backenddesappapi.webservice.builder

import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO

class BuilderUserDTO {
    private var firstName: String? = "German"
    private var lastName: String? = "Fernandez"
    private var emailAddress: String? = "german@gmail.com"
    private var address: String? = "Andrade 456482"
    private var password: String? = System.getenv("PASSWORD_USER")
    private var cvump: String? = "1111111111111111111111"
    private var cryptoWalletAddress: Int? = 11111111

    fun build(): UserRequestDTO {
        var userRequest = UserRequestDTO()
        userRequest.firstName = firstName
        userRequest.lastName = lastName
        userRequest.emailAddress = emailAddress
        userRequest.address = address
        userRequest.password = password
        userRequest.cvump = cvump
        userRequest.cryptoWalletAddress = cryptoWalletAddress
        return userRequest
    }

    fun withFirstName(name: String?): BuilderUserDTO {
        this.firstName = name
        return this
    }

    fun withLastName(lastname: String?): BuilderUserDTO {
        this.lastName = lastname
        return this
    }

    fun withEmail(email: String?): BuilderUserDTO {
        this.emailAddress = email
        return this
    }

    fun withAddress(address: String?): BuilderUserDTO {
        this.address = address
        return this
    }

    fun withPassword(password: String?): BuilderUserDTO {
        this.password = password
        return this
    }


    fun withCvu(cvump: String?): BuilderUserDTO {
        this.cvump = cvump
        return this
    }

    fun withCryptoWallet(cryptoWallet: Int?): BuilderUserDTO {
        this.cryptoWalletAddress = cryptoWallet
        return this
    }

    companion object {
        fun aUserDTO(): BuilderUserDTO {
            return BuilderUserDTO()
        }
    }
}