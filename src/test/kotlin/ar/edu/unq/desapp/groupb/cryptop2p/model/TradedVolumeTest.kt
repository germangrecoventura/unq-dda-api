package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.model.builder.UserBuilder
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TradedVolumeTest {
    fun anyTradedVolume(): TradedVolume {
        return TradedVolume.toTradedVolume(listOf())
    }

    @Test
    fun `should create a user when it has valid data`() {
        assertDoesNotThrow { anyTradedVolume() }
    }

}
