package ar.edu.unq.desapp.groupb.cryptop2p.security

import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService : UserDetailsService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository!!.findByEmailAddress(email)
            .orElseThrow { UsernameNotFoundException(String.format("User: %s, not found", email)) }
        return User(user.emailAddress, BCryptPasswordEncoder().encode(user.password), listOf())
    }
}