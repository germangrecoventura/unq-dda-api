package ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

class UserRequestDTO {
    @NotBlank(message = "The first name cannot be blank")
    @Size(min = 3, max = 30, message = "The first name must be between 3 and 30 characters long")
    @Pattern(regexp = "[a-zA-Z ]+", message = "The first name cannot contain special characters or numbers")
    @Schema(example = "Homero")
    var firstName: String? = null

    @NotBlank(message = "The last name cannot be blank")
    @Size(min = 3, max = 30, message = "The last name must be between 3 and 30 characters long")
    @Pattern(regexp = "[a-zA-Z ]+", message = "The last name cannot contain special characters or numbers")
    @Schema(example = "Simpson")
    var lastName: String? = null

    @Email(message = "The email address is not valid")
    @NotBlank(message = "The email address cannot be blank")
    @Schema(example = "homero.simpson@springfield.com")
    var emailAddress: String? = null

    @NotBlank(message = "The address cannot be blank")
    @Size(min = 10, max = 30, message = "The address must be between 10 and 30 characters long")
    @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "The address cannot contain special characters or numbers")
    @Schema(example = "Evergreen 123")
    var address: String? = null

    @NotBlank(message = "The password cannot be blank")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    @Pattern(
        regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[-+_!@#\$%^&*.,? ])[a-zA-Z0-9-+_!@#\$%^&*.,? ]{6,}$",
        message = "The password must have at least one lowercase letter, one uppercase letter and a special character"
    )
    @Schema(example = "Super!")
    var password: String? = null

    @NotBlank(message = "The CVU cannot be blank")
    @Size(min = 22, max = 22, message = "The CVU must be 22 digits long")
    @Pattern(regexp = "[0-9]+", message = "The CVU can only contain numbers")
    @Schema(example = "0011223344556677889900")
    var cvu: String? = null

    @NotNull(message = "The crypto wallet address cannot be blank")
    @Size(min = 8, max = 8, message = "The crypto wallet address must be 8 digits long")
    @Pattern(regexp = "[0-9]+", message = " The crypto wallet address can only contain numbers")
    @Schema(example = "12345678")
    var cryptoWalletAddress: String? = null
}
