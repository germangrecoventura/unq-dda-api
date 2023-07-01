package ar.edu.unq.desapp.groupb.cryptop2p.security

import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException

class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val json = ObjectMapper().writeValueAsString(
            ValidationErrorResponseDTO(
                listOf(
                    ValidationErrorDTO(
                        "user",
                        authException.message!!
                    )
                )
            )
        )
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(json)
    }
}
