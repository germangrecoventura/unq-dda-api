package ar.edu.unq.desapp.groupb.cryptop2p

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class CryptoP2PApplication : CommandLineRunner {
    @Autowired
    private lateinit var initializer: Initializer
    override fun run(vararg args: String?) {
        initializer.cleanDataBase()
        initializer.loadData()
    }
}

fun main(args: Array<String>) {
    runApplication<CryptoP2PApplication>(*args)
}