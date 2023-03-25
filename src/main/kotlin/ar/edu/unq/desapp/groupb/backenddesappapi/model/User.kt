package ar.edu.unq.desapp.groupb.backenddesappapi.model

import jakarta.persistence.*

@Entity
@Table(name = "registered_user")
class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null
    @Column(nullable = false, length = 30) private var firstName: String? = null
    @Column(nullable = false,length = 30) private var lastName: String? = null
    @Column(nullable = false, unique = true) private var emailAddress: String? = null
    @Column(nullable = false, length = 30) private var address: String? = null
    @Column(nullable = false) private var password: String? = null
    @Column(nullable = false, length = 22) private var cvump: Int? = null
    @Column(nullable = false, length = 8) private var cyptoWalletAddress: Int? = null

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

    fun getCvu(): Int? {
        return cvump
    }

    fun setCvu(cvuUpdate: Int?) {
        cvump = cvuUpdate
    }

    fun getCyptoWalletAddress(): Int? {
        return cyptoWalletAddress

    }

    fun setCyptoWalletAddress(cryptoWallet: Int?) {
        cyptoWalletAddress = cryptoWallet
    }

    fun fromModel(first_name: String?, last_name: String?, email: String?, addressUpdated: String?, passwordUpdated: String?, cvumpUpdated: Int?, cyptoWalletUpdated: Int?) {
        setFirstName(first_name!!)
        setLastName(last_name!!)
        setEmailAddress(email!!)
        setAddress(addressUpdated!!)
        setPassword(passwordUpdated!!)
        setCvu(cvumpUpdated)
        setCyptoWalletAddress(cyptoWalletUpdated)
    }

}