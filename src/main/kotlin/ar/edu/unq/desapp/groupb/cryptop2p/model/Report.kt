package ar.edu.unq.desapp.groupb.cryptop2p.model

class Report {
    var nameAsset: String? = null
    var nominalAmountAsset: Double? = null
    var currentPriceAsset: Double? = null
    var quotationAmountInPesos: Double? = null

    companion object {
        fun fromModel(asset: String,nominalAmountAsset: Double,currentPriceAsset: Double,quotationAmountInPesos: Double): Report {
            var report = Report()
            report.nameAsset = asset
            report.nominalAmountAsset = nominalAmountAsset
            report.currentPriceAsset = currentPriceAsset
            report.quotationAmountInPesos = quotationAmountInPesos
            return report
        }
    }
}
