package ar.edu.unq.desapp.groupb.backenddesappapi.model.builder

import ar.edu.unq.desapp.groupb.backenddesappapi.model.User

class BuilderUser {
    private var firstName: String? = "German"
    private var lastName: String? = "Fernandez"
    private var emailAddress: String? = "german@gmail.com"
    private var address: String? = "Andrade 456482"
    private var password: String? = System.getenv("PASSWORD_USER")
    private var cvump: String? = "1111111111111111111111"
    private var cryptoWalletAddress: Int? = 11111111

    fun build(): User {
        var user = User()
        user.setFirstName(firstName)
        user.setLastName(lastName)
        user.setEmailAddress(emailAddress)
        user.setAddress(address)
        user.setPassword(password)
        user.setCvump(cvump)
        user.setCryptoWalletAddress(cryptoWalletAddress)
        return user
    }

    fun withFirstName(name: String?): BuilderUser {
        this.firstName = name
        return this
    }

    fun withLastName(lastname: String?): BuilderUser {
        this.lastName = lastname
        return this
    }

    fun withEmail(email: String?): BuilderUser {
        this.emailAddress = email
        return this
    }

    fun withAddress(address: String?): BuilderUser {
        this.address = address
        return this
    }

    fun withPassword(password: String?): BuilderUser {
        this.password = password
        return this
    }


    fun withCvu(cvump: String?): BuilderUser {
        this.cvump = cvump
        return this
    }

    fun withCryptoWallet(cryptoWallet: Int?): BuilderUser {
        this.cryptoWalletAddress = cryptoWallet
        return this
    }

    companion object {
        fun aUser(): BuilderUser {
            return BuilderUser()
        }
    }
}