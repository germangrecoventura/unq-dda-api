package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.ConstraintViolation

data class ValidationErrorDTO(
    @Schema(example = "user.emailAddress")
    val source: String,
    @Schema(example = "The email address is already registered")
    val message: String
) {
    companion object {
        fun of(constraintViolation: ConstraintViolation<*>): ValidationErrorDTO {
            val propertyPath = constraintViolation.propertyPath.toString().substringAfter(".")
            return ValidationErrorDTO(
                propertyPath,
                constraintViolation.message
            )
        }
    }
}
