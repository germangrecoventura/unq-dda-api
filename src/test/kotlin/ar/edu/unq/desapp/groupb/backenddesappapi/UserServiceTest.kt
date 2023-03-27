package ar.edu.unq.desapp.groupb.backenddesappapi

import ar.edu.unq.desapp.groupb.backenddesappapi.service.UserService
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
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
        val userRequest = UserRequestDTO.BuilderUserDTO()
            .withFirstName("German")
            .withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235")
            .withPassword("sM#456")
            .withCVU("1111111111111111111111")
            .whitCryptoWallet(46464646)
            .build()

        var user = userService.save(userRequest)

        Assertions.assertTrue(user.getId() != null)
    }

    @Test
    fun `should throw an exception when a user doesn't have firstname`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName(null).build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The firstname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty firstname field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The firstname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field firstname numbers`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("456").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The firstname cannot contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field firstname special characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("Germ@n").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The firstname cannot contain special characters", thrown.message)
    }

    @Test
    fun `should throw an exception when firstname has less than 3 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("G").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The firstname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when firstname has more than 30 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The firstname must be between 3 and 30 characters", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have lastname`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName(null).build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The lastname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty lastname field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The lastname cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field lastname numbers`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Grec0 Ventura").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The lastname cannot contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field lastname special characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Grec# Ventura").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The lastname cannot contain special characters", thrown.message)
    }


    @Test
    fun `should throw an exception when lastname has less than 3 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Gr").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The lastname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when lastname has more than 30 characters`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Geeeeeeeeeeee eeeeeee eeeeeeeess")
                .build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The lastname must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when a user doesn't have email`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco").withEmail(null).build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The email cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty email field`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco").withEmail("").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The email cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when it is not a valid mail`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco").withEmail("pruebagmailcom")
                .build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("Is not a valid email", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have empty address`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco")
                .withEmail("prueba@gmail.com").withAddress(null).build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The address cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty address field`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco")
                .withEmail("prueba@gmail.com").withAddress("").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The address cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the field address special characters`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco")
                .withEmail("prueba@gmail.com").withAddress("sas@").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The address cannot contain special characters", thrown.message)
    }


    @Test
    fun `should throw an exception when address has less than 10 characters`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco")
                .withEmail("prueba@gmail.com").withAddress("sas").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The address must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 30 characters`() {
        val userRequest =
            UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco")
                .withEmail("prueba@gmail.com").withAddress("Geeeeeeeeeeee eeeeeee eeeeeeeess").build()
        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The address must be between 3 and 30 characters", thrown.message)
    }

    @Test
    fun `should throw an exception when a user doesn't have password`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword(null).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The password cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has an empty password field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The password cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when password has less than 6 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("1").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The password must be at least 6 characters", thrown.message)
    }


    // CORREGIR MAYUSCULAS Y MINUSCULAS

    @Test
    fun `should throw an exception when the password does not have a lower case`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11A1@1").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }

    @Test
    fun `should throw an exception when the password does not have a capital letter`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a1@1").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }


    // CORREGIR MAYUSCULAS Y MINUSCULAS

    @Test
    fun `should throw an exception when the password does not have a special character`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a1A1").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals(
            "The password must have at least one lowercase letter, one uppercase letter, and a special character",
            thrown.message
        )
    }

    //PASSWORD

    @Test
    fun `should throw an exception when a user doesn't have cvu`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a@A1").withCVU(null).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The CVU cannot be empty", thrown.message)
    }


    @Test
    fun `should throw an exception when a user has an empty cvu field`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a@A1").withCVU("").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The CVU cannot be empty", thrown.message)
    }

    @Test
    fun `should throw an exception when a user has the cvu does not only have numbers`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a@A1").withCVU("111111111a111111111111").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The cvu can only contain numbers", thrown.message)
    }

    @Test
    fun `should throw an exception when cvu has less than 22 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a@A1").withCVU("11111111111").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The cvu must have 22 digits", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 22 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a@A1").withCVU("111111111111111111111111").build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The cvu must have 22 digits", thrown.message)
    }


    @Test
    fun `should throw an exception when a user doesn't have crypto wallet`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a@A1").withCVU("1111111111111111111111").whitCryptoWallet(null)
            .build()

        assertThrows<RuntimeException> { userService.save(userRequest) }
    }

    @Test
    fun `should throw an exception when crypto wallet has less than 8 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a@A1").withCVU("1111111111111111111111").whitCryptoWallet(4646)
            .build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The crypto wallet must have 8 digits", thrown.message)
    }

    @Test
    fun `should throw an exception when address has more than 8 characters`() {
        val userRequest = UserRequestDTO.BuilderUserDTO().withFirstName("German").withLastName("Greco Ventura")
            .withEmail("prueba@gmail.com")
            .withAddress("Andrade 1235").withPassword("11a@A1").withCVU("1111111111111111111111")
            .whitCryptoWallet(464646465).build()

        val thrown: RuntimeException =
            Assertions.assertThrows(RuntimeException::class.java, { userService.save(userRequest) })

        assertEquals("The crypto wallet must have 8 digits", thrown.message)
    }

}
