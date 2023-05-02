package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.Transaction
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.AssetValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetPriceRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.TransactionRepository
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime

@Service
@Transactional
class TransactionService(
    private val transactionRepository: TransactionRepository,
) {
    fun save(assetName: String): Asset {
        return TODO()
    }


    fun clear() {
        transactionRepository.deleteAll()
    }
}