package ar.edu.unq.desapp.groupb.cryptop2p.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private val customUserDetailService: CustomUserDetailService? = null

    @Autowired
    private val jwtUtilService: JwtUtilService? = null

    private fun getTokenRequest(request: HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            header.substring(7, header.length)
        } else null
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getTokenRequest(request)
        if (StringUtils.hasText(token) && jwtUtilService!!.isTokenValid(token!!)) {
            val username = jwtUtilService.extractUsername(token)
            val userDetails = customUserDetailService!!.loadUserByUsername(username)
            val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, ArrayList())
            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
        filterChain.doFilter(request, response)
    }
}