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
        fun withFirstName(first_name: String?) = apply { userDTO.firstName = first_name }
        fun withLastName(last_name: String?) = apply { userDTO.lastName = last_name }
        fun withEmail(email: String?) = apply { userDTO.emailAddress = email }
        fun withAddress(addressUpdate: String?) = apply { userDTO.address = addressUpdate }
        fun withPassword(passwordUpdate: String?) = apply { userDTO.password = passwordUpdate }
        fun withCVU(cvuUpdate: String?) = apply { userDTO.cvump = cvuUpdate }
        fun whitCyptoWallet(cryptoWallet: Int?) = apply { userDTO.cryptoWalletAddress = cryptoWallet }
        fun build() = userDTO
    }
}