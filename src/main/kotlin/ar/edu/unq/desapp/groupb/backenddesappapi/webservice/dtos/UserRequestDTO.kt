package ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos

class UserRequestDTO(
    val firstName: String?,
    val lastName: String?,
    val emailAddress: String?,
    val address: String?,
    val password: String?,
    val cvump: Int?,
    val cyptoWalletAddress: Int?
) {
    companion object {
        fun fromModel(
            firstName: String?,
            lastName: String?,
            emailAddress: String?,
            address: String?,
            password: String?,
            cvump: Int?,
            cyptoWalletAddress: Int?
        ): UserRequestDTO {
            return UserRequestDTO(firstName, lastName, emailAddress, address, password, cvump, cyptoWalletAddress)
        }
    }
}
