package ar.edu.unq.desapp.groupb.backenddesappapi.persistence

import ar.edu.unq.desapp.groupb.backenddesappapi.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long>