package ar.edu.unq.desapp.groupb.cryptop2p.service.helpers

class ExchangeHelper {
}

data class Casa(
    var compra: String?,
    var venta: String?,
    var nombre: String?,
    var fecha: String?,
)


data class Root(
    var casa: Casa?,
)

data class Symbol(
    var symbol: String?,
    var price: Double?,
)