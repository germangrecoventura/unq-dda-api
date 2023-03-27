package ar.edu.unq.desapp.groupb.backenddesappapi.model

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

    class Builder {
        private val user = User()
        fun whitFirstName(firstName: String) = apply { user.firstName = firstName }
        fun whitLastName(lastName: String) = apply { user.lastName = lastName }
        fun whitEmail(email: String) = apply { user.emailAddress = email }
        fun whitAddress(address: String) = apply { user.address = address }
        fun whitPassword(password: String) = apply { user.password = password }
        fun whitCVU(cvuUpdate: String) = apply { user.cvump = cvuUpdate }
        fun whitCryptoWallet(cryptoWalletAddress: Int) = apply { user.cryptoWalletAddress = cryptoWalletAddress }
        fun build() = user
    }

    fun getId(): Long? {
        return id
    }

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getEmailAddress(): String? {
        return emailAddress
    }

    fun setEmailAddress(emailAddress: String) {
        this.emailAddress = emailAddress
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getCvump(): String? {
        return cvump
    }

    fun setCvump(cvu: String?) {
        this.cvump = cvu
    }

    fun getCryptoWalletAddress(): Int? {
        return cryptoWalletAddress

    }

    fun setCryptoWalletAddress(cryptoWalletAddress: Int?) {
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
        setFirstName(firstName!!)
        setLastName(lastName!!)
        setEmailAddress(email!!)
        setAddress(address!!)
        setPassword(password!!)
        setCvump(cvump!!)
        setCryptoWalletAddress(cryptoWalletAddress!!)
    }

}
