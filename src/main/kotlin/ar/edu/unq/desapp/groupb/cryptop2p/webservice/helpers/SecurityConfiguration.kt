package ar.edu.unq.desapp.groupb.cryptop2p.webservice.helpers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfiguration {
    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain? {
        http.cors().and().csrf().disable()
            .authorizeHttpRequests().requestMatchers("/**").permitAll()
        http.headers().frameOptions().disable()
        return http.build()
    }
}
