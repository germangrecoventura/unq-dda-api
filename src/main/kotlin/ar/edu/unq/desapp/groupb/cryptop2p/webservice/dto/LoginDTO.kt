package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


class LoginDTO {
    @NotBlank(message = "The email cannot be blank")
    @Email(message = "The email address is not valid")
    var email: String? = null

    @NotBlank(message = "The password cannot be blank")
    var password: String? = null
}