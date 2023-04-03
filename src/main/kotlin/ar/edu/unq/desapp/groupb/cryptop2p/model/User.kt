package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserRequestDTO
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(name = "registered_user")
@JsonPropertyOrder("id", "emailAddress", "firstname", "lastname")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "1")
    @JsonProperty
    var id: Long? = null

    @Column(nullable = false, length = 30)
    @NotBlank(message = "The first name cannot be blank")
    @Size(min = 3, max = 30, message = "The first name must be between 3 and 30 characters long")
    @Pattern(regexp = "[a-zA-Z ]+", message = "The first name cannot contain special characters")
    @Schema(example = "Homero")
    @JsonProperty
    var firstName: String? = null

    @Column(nullable = false, length = 30)
    @NotBlank(message = "The last name cannot be blank")
    @Size(min = 3, max = 30, message = "The last name must be between 3 and 30 characters long")
    @Pattern(regexp = "[a-zA-Z ]+", message = "The last name cannot contain special characters or numbers")
    @Schema(example = "Simpson")
    @JsonProperty
    var lastName: String? = null

    @Column(nullable = false, unique = true)
    @Email(message = "The email address is not valid")
    @NotBlank(message = "The email address cannot be blank")
    @Schema(example = "homero.simpson@springfield.com")
    @JsonProperty
    var emailAddress: String? = null

    @Column(nullable = false, length = 30)
    @NotBlank(message = "The address cannot be blank")
    @Size(min = 10, max = 30, message = "The address must be between 10 and 30 characters long")
    @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "The address cannot contain special characters")
    @Schema(example = "Evergreen 123")
    @JsonProperty
    var address: String? = null

    @Column(nullable = false)
    @NotBlank(message = "The password cannot be blank")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    @Pattern(
        regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[-+_!@#\$%^&*.,? ])[a-zA-Z0-9-+_!@#\$%^&*.,? ]{6,}$",
        message = "The password must have at least one lowercase letter, one uppercase letter and a special character"
    )
    @JsonIgnore
    var password: String? = null

    @Column(nullable = false, length = 22)
    @NotBlank(message = "The CVU cannot be blank")
    @Size(min = 22, max = 22, message = "The CVU must be 22 digits long")
    @Pattern(regexp = "[0-9]+", message = " The CVU can only contain numbers")
    @Schema(example = "0011223344556677889900")
    @JsonProperty
    var cvu: String? = null

    @Column(nullable = false, length = 8)
    @NotNull(message = "The crypto wallet address cannot be blank")
    @Size(min = 8, max = 8, message = "The crypto wallet address must be 8 digits long")
    @Pattern(regexp = "[0-9]+", message = " The crypto wallet address can only contain numbers")
    @Schema(example = "12345678")
    @JsonProperty
    var cryptoWalletAddress: String? = null

    companion object {
        fun fromDTO(userRequest: UserRequestDTO): User {
            val user = User()
            user.firstName = userRequest.firstName
            user.lastName = userRequest.lastName
            user.emailAddress = userRequest.emailAddress
            user.address = userRequest.address
            user.password = userRequest.password
            user.cvu = userRequest.cvu
            user.cryptoWalletAddress = userRequest.cryptoWalletAddress
            return user
        }
    }
}
