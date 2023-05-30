package ar.edu.unq.desapp.groupb.cryptop2p.persistence

import ar.edu.unq.desapp.groupb.cryptop2p.model.UserTransactionRating
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserTransactionRatingRepository : CrudRepository<UserTransactionRating, Long> {

    @Query(value = "SELECT u.user.id AS userId, sum(u.rating) AS rating, count(u.user.id) AS totalOperations FROM UserTransactionRating AS u WHERE u.user.id IN :userIds GROUP BY u.user.id")
    fun getByUserIn(@Param("userIds") userIds: Set<Long>): List<UserRating>

}

interface UserRating {
    val userId: Long
    val rating: Double
    val totalOperations: Int
}
