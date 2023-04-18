package ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder

import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserCreateRequestDTO

class UserCreateRequestDTOBuilder {
    private var firstName: String? = null
    private var lastName: String? = null
    private var emailAddress: String? = null
    private var address: String? = null
    private var password: String? = null
    private var cvu: String? = null
    private var cryptoWalletAddress: String? = null

    fun build(): UserCreateRequestDTO {
        val userRequest = UserCreateRequestDTO()
        userRequest.firstName = firstName
        userRequest.lastName = lastName
        userRequest.emailAddress = emailAddress
        userRequest.address = address
        userRequest.password = password
        userRequest.cvu = cvu
        userRequest.cryptoWalletAddress = cryptoWalletAddress
        return userRequest
    }

    fun withFirstName(name: String?): UserCreateRequestDTOBuilder {
        this.firstName = name
        return this
    }

    fun withLastName(lastname: String?): UserCreateRequestDTOBuilder {
        this.lastName = lastname
        return this
    }

    fun withEmail(email: String?): UserCreateRequestDTOBuilder {
        this.emailAddress = email
        return this
    }

    fun withAddress(address: String?): UserCreateRequestDTOBuilder {
        this.address = address
        return this
    }

    fun withPassword(password: String?): UserCreateRequestDTOBuilder {
        this.password = password
        return this
    }


    fun withCVU(cvu: String?): UserCreateRequestDTOBuilder {
        this.cvu = cvu
        return this
    }

    fun withCryptoWallet(cryptoWallet: String?): UserCreateRequestDTOBuilder {
        this.cryptoWalletAddress = cryptoWallet
        return this
    }
}
