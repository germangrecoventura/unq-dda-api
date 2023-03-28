package ar.edu.unq.desapp.groupb.backenddesappapi.persistence

import ar.edu.unq.desapp.groupb.backenddesappapi.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : CrudRepository<User, Long>{
    fun findByEmailAddress(string: String): Optional<User>
}