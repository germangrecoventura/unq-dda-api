package ar.edu.unq.desapp.groupb.cryptop2p.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint) {

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors().and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
            .requestMatchers("/docs/**").permitAll()
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .anyRequest().authenticated().and()
            .httpBasic()
        http.headers().frameOptions().disable()
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers(AntPathRequestMatcher("/h2-console/**"))
        }
    }
}
