package ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos

class UserRequestDTO {
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var address: String? = null
    var password: String? = null
    var cvump: String? = null
    var cryptoWalletAddress: Int? = null

    class BuilderUserDTO {
        private val userDTO = UserRequestDTO()
        fun withFirstName(firstName: String?) = apply { userDTO.firstName = firstName }
        fun withLastName(lastName: String?) = apply { userDTO.lastName = lastName }
        fun withEmail(email: String?) = apply { userDTO.emailAddress = email }
        fun withAddress(address: String?) = apply { userDTO.address = address }
        fun withPassword(password: String?) = apply { userDTO.password = password }
        fun withCVU(cvump: String?) = apply { userDTO.cvump = cvump }
        fun whitCryptoWallet(cryptoWalletAddress: Int?) = apply { userDTO.cryptoWalletAddress = cryptoWalletAddress }
        fun build() = userDTO
    }
}
