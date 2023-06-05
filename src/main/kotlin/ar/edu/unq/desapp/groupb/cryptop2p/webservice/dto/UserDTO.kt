package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

data class UserDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val totalOperations: Int,
    val rating: String,
)
