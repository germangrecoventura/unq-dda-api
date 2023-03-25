package ar.edu.unq.desapp.groupb.backenddesappapi

import ar.edu.unq.desapp.groupb.backenddesappapi.service.UserService
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Test
    fun `should add a user when you have valid credentials`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        var user = userService.save(userRequest)

        Assertions.assertTrue(user.getId() != null)
    }

    @Test
    fun `should throw an exception when a user doesn't have firstname`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName(null).whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has an empty firstname field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has the field firstname numbers`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("456").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has the field firstname special characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("Germ@n").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when firstname has less than 3 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("G").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when firstname has more than 30 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("Geeeeeeeeeeee eeeeeee eeeeeeeess").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }


    @Test
    fun `should throw an exception when a user doesn't have lastname`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName(null)
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has an empty lastname field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has the field lastname numbers`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Grec0 Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has the field lastname special characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Grec# Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }


    @Test
    fun `should throw an exception when lastname has less than 3 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Gr")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when lastname has more than 30 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Geeeeeeeeeeee eeeeeee eeeeeeeess")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user doesn't have email`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail(null)
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has an empty email field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when it is not a valid mail`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco Ventura")
            .whitEmail("pruebagmailcom")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }


    @Test
    fun `should throw an exception when a user doesn't have empty address`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress(null).whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has an empty address field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has the field address special characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("sas@").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }


    @Test
    fun `should throw an exception when address has less than 10 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("sas").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when address has more than 30 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Geeeeeeeeeeee eeeeeee eeeeeeeess").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }


    //PASSWORD

    @Test
    fun `should throw an exception when a user doesn't have empty password`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword(null).whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when password has less than 6 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when the password does not have a lower case`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("11A1@1").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when the password does not have a capital letter`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("11a1@1").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when the password does not have a special character`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("11a1A1").whitCVU("1111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    //PASSWORD

    @Test
    fun `should throw an exception when a user doesn't have cvu`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU(null).whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }


    @Test
    fun `should throw an exception when a user has an empty cvu field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when a user has the cvu does not only have numbers`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("111111111a111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when cvu has less than 22 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("11111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when address has more than 22 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("111111111111111111111111").whitCyptoWallet(46464646).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }


    @Test
    fun `should throw an exception when a user doesn't have crypto wallet`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco Ventura")
            .whitEmail("prueba@gmail.com")
            .whitAddress("sas").whitPassword("sM#456").whitCVU("l1111111111111111111111").whitCyptoWallet(null).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when crypto wallet has less than 8 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("1111111111111111111111").whitCyptoWallet(4646).build()
        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when address has more than 8 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().whitFirstName("German").whitLastName("Greco")
            .whitEmail("prueba@gmail.com")
            .whitAddress("Andrade 1235").whitPassword("sM#456").whitCVU("111111111111111111111111").whitCyptoWallet(464646465).build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

}