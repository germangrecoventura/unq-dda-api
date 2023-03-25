package ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos

class UserRequestDTO {
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var address: String? = null
    var password: String? = null
    var cvump: Int? = null
    var cyptoWalletAddress: Int? = null

    class BuilderUserDTO {
        private val userDTO = UserRequestDTO()
        fun whitFirstName(first_name: String?) = apply { userDTO.firstName = first_name }
        fun whitLastName(last_name: String?) = apply { userDTO.lastName = last_name }
        fun whitEmail(email: String?) = apply { userDTO.emailAddress = email }
        fun whitAddress(addressUpdate: String?) = apply { userDTO.address = addressUpdate }
        fun whitPassword(passwordUpdate: String?) = apply { userDTO.password = passwordUpdate }
        fun whitCVU(cvuUpdate: Int?) = apply { userDTO.cvump = cvuUpdate }
        fun whitCyptoWallet(cryptoWallet: Int?) = apply { userDTO.cyptoWalletAddress = cryptoWallet }
        fun build() = userDTO
    }
}
