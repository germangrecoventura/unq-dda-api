package ar.edu.unq.desapp.groupb.backenddesappapi

import ar.edu.unq.desapp.groupb.backenddesappapi.service.UserService
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Test
    fun `should add a teacher`() {
        var user =
            userService.save(UserRequestDTO("German", "Greco Ventura", "prueba@gmail.com", "sas", "sda4", 4564, 4646))
        Assertions.assertTrue(user.getId() != null)
    }

}