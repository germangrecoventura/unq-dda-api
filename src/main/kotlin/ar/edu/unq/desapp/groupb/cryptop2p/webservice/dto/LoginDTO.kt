package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


data class LoginDTO(
    @field:NotBlank(message = "The email cannot be blank")
    @field:Email(message = "The email address is not valid")
    val email: String,

    @field:NotBlank(message = "The password cannot be blank")
    val password: String
) {
    override fun toString() = "${this.javaClass.simpleName}(email=$email, password=********)"
}
