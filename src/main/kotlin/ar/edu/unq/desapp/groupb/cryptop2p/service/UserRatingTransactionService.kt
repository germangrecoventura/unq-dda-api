package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserTransactionRatingRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UserRatingTransactionService(
    private val userTransactionRatingRepository: UserTransactionRatingRepository,
) {
    fun clear() {
        userTransactionRatingRepository.deleteAll()
    }
}