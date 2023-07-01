package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

data class UserCreateRequestDTO(
    @field:NotBlank(message = "The first name cannot be blank")
    @field:Size(min = 3, max = 30, message = "The first name must be between 3 and 30 characters long")
    @field:Pattern(regexp = "[a-zA-Z ]+", message = "The first name cannot contain special characters or numbers")
    @field:Schema(example = "Homero")
    var firstName: String? = null,

    @field:NotBlank(message = "The last name cannot be blank")
    @field:Size(min = 3, max = 30, message = "The last name must be between 3 and 30 characters long")
    @field:Pattern(regexp = "[a-zA-Z ]+", message = "The last name cannot contain special characters or numbers")
    @field:Schema(example = "Simpson")
    var lastName: String? = null,

    @field:Email(message = "The email address is not valid")
    @field:NotBlank(message = "The email address cannot be blank")
    @field:Schema(example = "homero.simpson@springfield.com")
    var emailAddress: String? = null,

    @field:NotBlank(message = "The address cannot be blank")
    @field:Size(min = 10, max = 30, message = "The address must be between 10 and 30 characters long")
    @field:Pattern(regexp = "[a-zA-Z0-9 ]+", message = "The address cannot contain special characters or numbers")
    @field:Schema(example = "Evergreen 123")
    var address: String? = null,

    @field:NotBlank(message = "The password cannot be blank")
    @field:Size(min = 6, message = "The password must be at least 6 characters long")
    @field:Pattern(
        regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[-+_!@#\$%^&*.,? ])[a-zA-Z0-9-+_!@#\$%^&*.,? ]{6,}$",
        message = "The password must have at least one lowercase letter, one uppercase letter and a special character"
    )
    @field:Schema(example = "Super!")
    var password: String? = null,

    @field:NotBlank(message = "The CVU cannot be blank")
    @field:Size(min = 22, max = 22, message = "The CVU must be 22 digits long")
    @field:Pattern(regexp = "[0-9]+", message = "The CVU can only contain numbers")
    @field:Schema(example = "0011223344556677889900")
    var cvu: String? = null,

    @field:NotNull(message = "The crypto wallet address cannot be blank")
    @field:Size(min = 8, max = 8, message = "The crypto wallet address must be 8 digits long")
    @field:Pattern(regexp = "[0-9]+", message = " The crypto wallet address can only contain numbers")
    @field:Schema(example = "12345678")
    var cryptoWalletAddress: String? = null,
) {
    fun toDomain(): User {
        val user = User()
        user.firstName = this.firstName
        user.lastName = this.lastName
        user.emailAddress = this.emailAddress
        user.address = this.address
        user.password = this.password
        user.cvu = this.cvu
        user.cryptoWalletAddress = this.cryptoWalletAddress
        return user
    }
}
