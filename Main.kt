package signature

import java.io.File
import java.util.*
import kotlin.math.abs

fun main() {

    print("Enter name and surname: ")
    val name = readLine()!!
    print("Enter person's status: ")
    val status = readLine()!!

    // Сюда просится ООП
    val scannerRoman = Scanner(File("/fonts/roman.txt"))  // "C:/fonts/roman.txt"
    val (heightOfFontRomanStr) = scannerRoman.nextLine().split(" ")
    val heightOfFontRoman = heightOfFontRomanStr.toInt()
    val allCharsRoman = getAllChars(scannerRoman)
    val dataForName = getCodesAndWidthsOfCharacters(name, allCharsRoman, heightOfFontRoman, 10)

    val scannerMedium = Scanner(File("/fonts/medium.txt")) // "C:/fonts/medium.txt"
    val (heightOfFontMediumStr) = scannerMedium.nextLine().split(" ")
    val heightOfFontMedium = heightOfFontMediumStr.toInt()
    val allCharsMedium = getAllChars(scannerMedium)
    val dataForStatus = getCodesAndWidthsOfCharacters(status, allCharsMedium, heightOfFontMedium, 5)

    var lengthName = 0
    for (width in dataForName[1]) {
        lengthName += width
    }

    var lengthStatus = 0
    for (width in dataForStatus[1]) {
        lengthStatus += width
    }

    val isNameLonger = lengthStatus < lengthName
    val lengthStr: Int
    val lineDifference = abs(lengthStatus - lengthName)
    var spacesBeforeName = 0
    var spacesAfterName = 0
    var spacesBeforeStatus = 0
    var spacesAfterStatus = 0

    if (isNameLonger) {
        lengthStr = lengthName
        spacesBeforeStatus = lineDifference / 2
        spacesAfterStatus = lineDifference - spacesBeforeStatus
    } else {
        lengthStr = lengthStatus
        spacesBeforeName = lineDifference / 2
        spacesAfterName = lineDifference - spacesBeforeName
    }

    // Верхняя граница *******************************
    printBorder(lengthStr + 8, '8')

    // name and surname ******************************
    printText(dataForName, allCharsRoman, heightOfFontRoman, "88  ", "  88", spacesBeforeName, spacesAfterName)

    // status ****************************************
    printText(dataForStatus, allCharsMedium, heightOfFontMedium, "88  ", "  88", spacesBeforeStatus, spacesAfterStatus)

    // Нижняя граница ********************************
    printBorder(lengthStr + 8, '8')
}

fun getAllChars(scanner: Scanner): Array<String> {
    var allChars = emptyArray<String>()
    while (scanner.hasNextLine()) {
        allChars += scanner.nextLine()
    }
    return allChars
}

fun getCodesAndWidthsOfCharacters(
    text: String,
    allChars: Array<String>,
    heightOfFont: Int,
    spaceLength: Int
): Array<IntArray> {
    // Находим позиции символов (ячеек, где описывается буква) в массиве шрифта
    val textLength = text.length
    val letterCodesOfName = IntArray(textLength)
    val letterWidthsOfName = IntArray(textLength)
    var i = 0
    val stepOfFont = heightOfFont + 1
    for (char in text) {
        val charStr = char.toString()
        // Символа пробел нет в шрифтах
        if (charStr == " ") {
            letterCodesOfName[i] = -1
            letterWidthsOfName[i] = spaceLength
        } else {
            for (j in allChars.indices step stepOfFont) {
                val (ch, width) = allChars[j].split(" ")
                if (charStr == ch) {
                    letterCodesOfName[i] = j
                    letterWidthsOfName[i] = width.toInt()
                    break
                }
            }
        }
        i++
    }
    return arrayOf(letterCodesOfName, letterWidthsOfName)
}

fun printText(
    dataText: Array<IntArray>,
    allChars: Array<String>,
    heightOfFont: Int,
    beforeText: String,
    afterText: String,
    qtySpacesBefore: Int,
    qtySpacesAfter: Int
) {
    for (i in 1..heightOfFont) {
        println()
        print(beforeText)
        for (j in 1..qtySpacesBefore) {
            print(" ")
        }
        for (k in dataText[0].indices) {
            if (dataText[0][k] == -1) {
                // Пробел
                for (s in 1..dataText[1][k]) {
                    print(" ")
                }
            } else {
                print(allChars[dataText[0][k] + i])
            }
        }
        for (j in 1..qtySpacesAfter) {
            print(" ")
        }
        print(afterText)
    }
}

fun printBorder(length: Int, char: Char) {
    println()
    repeat(length) {
        print(char)
    }
}