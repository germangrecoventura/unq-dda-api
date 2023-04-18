package ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder

import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserCreationRequestDTO

class UserDTOBuilder {
    private var firstName: String? = null
    private var lastName: String? = null
    private var emailAddress: String? = null
    private var address: String? = null
    private var password: String? = null
    private var cvu: String? = null
    private var cryptoWalletAddress: String? = null

    fun build(): UserCreationRequestDTO {
        var userRequest = UserCreationRequestDTO()
        userRequest.firstName = firstName
        userRequest.lastName = lastName
        userRequest.emailAddress = emailAddress
        userRequest.address = address
        userRequest.password = password
        userRequest.cvu = cvu
        userRequest.cryptoWalletAddress = cryptoWalletAddress
        return userRequest
    }

    fun withFirstName(name: String?): UserDTOBuilder {
        this.firstName = name
        return this
    }

    fun withLastName(lastname: String?): UserDTOBuilder {
        this.lastName = lastname
        return this
    }

    fun withEmail(email: String?): UserDTOBuilder {
        this.emailAddress = email
        return this
    }

    fun withAddress(address: String?): UserDTOBuilder {
        this.address = address
        return this
    }

    fun withPassword(password: String?): UserDTOBuilder {
        this.password = password
        return this
    }


    fun withCVU(cvu: String?): UserDTOBuilder {
        this.cvu = cvu
        return this
    }

    fun withCryptoWallet(cryptoWallet: String?): UserDTOBuilder {
        this.cryptoWalletAddress = cryptoWallet
        return this
    }
}
