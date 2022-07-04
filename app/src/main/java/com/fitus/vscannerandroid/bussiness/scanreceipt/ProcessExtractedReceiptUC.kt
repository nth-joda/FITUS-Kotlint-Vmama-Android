package com.fitus.vscannerandroid.bussiness.scanreceipt

import com.fitus.vscannerandroid.data.model.Receipt

class ProcessExtractedReceiptUC {

    suspend operator fun invoke(receipt: Receipt): Receipt {

        val products = receipt.products?.map {
            autoCorrectZeroAndCharO(it.trim().uppercase())
        }

        val prices = receipt.prices?.map {
            it.replace(" ", "")
        }

        return receipt.copy(
            products = products?.toMutableList(),
            prices = prices?.toMutableList()
        )
    }

    private suspend fun autoCorrectZeroAndCharO(product: String): String {
        var result = product[0].toString()
        for (i in 1 until product.length) {
            if (product[i] == 'O' || product[i] == 'o') {
                if (result.last().isDigit()) {
                    result += '0'
                }
            } else if (product[i] == '0') {
                if (!result.last().isDigit()) {
                    result += 'O'
                }
            } else {
                result += product[i]
            }
        }
        return result
    }
}