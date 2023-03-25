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
    fun `should add a user when you have valid credentials`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco Ventura").whitEmail("prueba@gmail.com")
            .whitAddress("sas").whitPassword("s4asd").whitCVU(4564).whitCyptoWallet(4646).build()

        var user =
            userService.save(userRequest)
        Assertions.assertTrue(user.getId() != null)
    }

}