package ar.edu.unq.desapp.groupb.cryptop2p.persistence

import ar.edu.unq.desapp.groupb.cryptop2p.model.Transaction
import ar.edu.unq.desapp.groupb.cryptop2p.model.TransactionStatus
import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository : CrudRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE (t.seller = :user OR t.buyer = :user) AND t.created BETWEEN :created AND :created2 AND t.status = :status")
    fun getAllBySellerOrBuyerAndCreatedBetweenAndStatus(
        @Param("user") user: User,
        @Param("created") created: LocalDateTime,
        @Param("created2") created2: LocalDateTime,
        @Param("status") status: TransactionStatus
    ): List<Transaction>
}
