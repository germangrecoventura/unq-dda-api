package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.model.User

class UserBuilder {
    private var firstName: String? = null
    private var lastName: String? = null
    private var emailAddress: String? = null
    private var address: String? = null
    private var password: String? = null
    private var cvu: String? = null
    private var cryptoWalletAddress: String? = null

    fun build(): User {
        var user = User()
        user.firstName = firstName
        user.lastName = lastName
        user.emailAddress = emailAddress
        user.address = address
        user.password = password
        user.cvu = cvu
        user.cryptoWalletAddress = cryptoWalletAddress
        return user
    }

    fun withFirstName(name: String?): UserBuilder {
        this.firstName = name
        return this
    }

    fun withLastName(lastname: String?): UserBuilder {
        this.lastName = lastname
        return this
    }

    fun withEmail(email: String?): UserBuilder {
        this.emailAddress = email
        return this
    }

    fun withAddress(address: String?): UserBuilder {
        this.address = address
        return this
    }

    fun withPassword(password: String?): UserBuilder {
        this.password = password
        return this
    }


    fun withCVU(cvu: String?): UserBuilder {
        this.cvu = cvu
        return this
    }

    fun withCryptoWallet(cryptoWallet: String?): UserBuilder {
        this.cryptoWalletAddress = cryptoWallet
        return this
    }
}
