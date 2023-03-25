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
    private var cyptoWalletAddress: Int? = null

    class Builder {
        private val user = User()
        fun whitFirstName(first_name: String) = apply { user.firstName = first_name }
        fun whitLastName(last_name: String) = apply { user.lastName = last_name }
        fun whitEmail(email: String) = apply { user.emailAddress = email }
        fun whitAddress(addressUpdate: String) = apply { user.address = addressUpdate }
        fun whitPassword(passwordUpdate: String) = apply { user.password = passwordUpdate }
        fun whitCVU(cvuUpdate: String) = apply { user.cvump = cvuUpdate }
        fun whitCyptoWallet(cryptoWallet: Int) = apply { user.cyptoWalletAddress = cryptoWallet }
        fun build() = user
    }

    fun getId(): Long? {
        return id
    }

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstname: String) {
        firstName = firstname
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastname: String) {
        lastName = lastname
    }

    fun getEmailAddress(): String? {
        return emailAddress
    }

    fun setEmailAddress(email: String) {
        emailAddress = email
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(addressUpdate: String) {
        address = addressUpdate
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(passwordUpdate: String) {
        password = passwordUpdate
    }

    fun getCvu(): String? {
        return cvump
    }

    fun setCvu(cvuUpdate: String?) {
        cvump = cvuUpdate
    }

    fun getCyptoWalletAddress(): Int? {
        return cyptoWalletAddress

    }

    fun setCyptoWalletAddress(cryptoWallet: Int?) {
        cyptoWalletAddress = cryptoWallet
    }

    fun fromModel(
        first_name: String?,
        last_name: String?,
        email: String?,
        addressUpdated: String?,
        passwordUpdated: String?,
        cvumpUpdated: String?,
        cyptoWalletUpdated: Int?
    ) {
        setFirstName(first_name!!)
        setLastName(last_name!!)
        setEmailAddress(email!!)
        setAddress(addressUpdated!!)
        setPassword(passwordUpdated!!)
        setCvu(cvumpUpdated)
        setCyptoWalletAddress(cyptoWalletUpdated)
    }

}