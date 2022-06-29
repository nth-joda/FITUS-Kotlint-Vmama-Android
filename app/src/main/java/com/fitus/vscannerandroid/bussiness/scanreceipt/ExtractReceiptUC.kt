package com.fitus.vscannerandroid.bussiness.scanreceipt

import androidx.core.text.isDigitsOnly
import com.fitus.vscannerandroid.data.model.Receipt
import com.google.mlkit.vision.text.Text

class ExtractReceiptUC {

    suspend operator fun invoke(visionText: Text): Receipt {
        try {

            val textBlocksLeft = mutableListOf<Text.TextBlock>()
            val textBlocksRight = mutableListOf<Text.TextBlock>()
            val textBlocksMiddle = mutableListOf<Text.TextBlock>()
            val textBlocks = visionText.textBlocks

            splitData(textBlocks, textBlocksLeft, textBlocksRight)
            splitData(textBlocksRight, textBlocksMiddle, textBlocksRight)

            val productName = getProductName(textBlocksLeft)
            val prices = getProductPrices(textBlocksRight)

            return Receipt(
                productName,
                prices,
                visionText.text
            )
        } catch (e: Exception) {
            return Receipt(text = visionText.text)
        }
    }

    private fun splitData(
        textBlocks: List<Text.TextBlock>,
        textBlocksLeft: MutableList<Text.TextBlock>,
        textBlocksRight: MutableList<Text.TextBlock>
    ) {
        val sortedTextBlock = textBlocks.sortedBy {
            it.boundingBox!!.left
        }

        val leftPosBoxes = sortedTextBlock.map {
            it.boundingBox!!.left
        }
        val dist = ArrayList<Int>()
        for (i in 0 until leftPosBoxes.size - 1) {
            dist.add(leftPosBoxes[i + 1] - leftPosBoxes[i])
        }

        val maxDist = dist.maxOf { it }
        val maxDistIndex = dist.indexOf(maxDist) + 1

        var textBlocksLeftTemp = sortedTextBlock.subList(0, maxDistIndex)
        var textBlocksRightTemp = sortedTextBlock.subList(maxDistIndex, sortedTextBlock.size)

        textBlocksLeftTemp = textBlocksLeftTemp.sortedBy {
            it.boundingBox!!.top
        }
        textBlocksRightTemp = textBlocksRightTemp.sortedBy {
            it.boundingBox!!.top
        }

        textBlocksLeft.clear()
        textBlocksRight.clear()
        textBlocksLeft.addAll(textBlocksLeftTemp)
        textBlocksRight.addAll(textBlocksRightTemp)

        println("====LEFT====")
        println(textBlocksLeft.map {
            it.text
        })
        println("====RIGHT====")
        println(textBlocksRight.map {
            it.text
        })
    }

    private fun getProductName(textBlocks: List<Text.TextBlock>): MutableList<String> {
        val lines = textBlocks.flatMap {
            it.text.split("\n")
        }.toMutableList()

        for (i in lines.indices.reversed()) {
            var line = lines[i]
            line = line.uppercase()

            if (lines[i].contains("[*|=|:]([*|=|:])+".toRegex())) {
                lines.removeAt(i)
            } else if (line.replace(".", "").trim().isDigitsOnly()) {
                lines.removeAt(i)
            } else if (line.contains("KHUYEN MAI")) {
                lines.removeAt(i)
            }
        }
        return lines
    }

    private fun getProductPrices(textBlocks: List<Text.TextBlock>): MutableList<String> {
        val lines = textBlocks.flatMap {
            it.text.split("\n")
        }

        val prices = mutableListOf<String>()

        for (text in lines) {
            if (text.contains(".")) {
                prices.add(text)
            }
        }
        return prices
    }
}