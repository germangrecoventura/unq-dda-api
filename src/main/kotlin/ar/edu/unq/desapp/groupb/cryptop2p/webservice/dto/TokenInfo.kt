package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

class TokenInfo(jwtToken: String){

    private val jwtToken = jwtToken

    fun getToken(): String{
        return jwtToken
    }
}