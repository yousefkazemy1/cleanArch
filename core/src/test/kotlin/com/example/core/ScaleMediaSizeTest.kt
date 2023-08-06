package com.example.core

import com.example.core.utils.getScaledMediaSize
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ScaleMediaSizeTest {

    @Test
    fun scaleMediaSize_withSmallerMediaHeightThanScreen_cacaulateCorrectly() {
        val screenSize = Pair<UShort, UShort>(1080u, 2177u) // height to width ratio: 2.01
        val mediaSize = Pair<UShort, UShort>(291u, 163u) // height to width ratio: 0.56

        val expectedSacleHeight = Math.round(
            screenSize.first.toDouble() * mediaSize.second.toDouble() / mediaSize.first.toDouble()
        )
        val expectedResult = Pair(screenSize.first, expectedSacleHeight.toInt().toUShort())

        val scaleSize = getScaledMediaSize(
            screenSize = screenSize, mediaSize = mediaSize, 0u
        )

        Assertions.assertEquals(expectedResult, scaleSize)
    }

    @Test
    fun scaleMediaSize_withLargerMediaHeightThanScreen_cacaulateCorrectly() {
        val screenSize = Pair<UShort, UShort>(1080u, 2177u) // height to width ratio: 2.01
        val mediaSize = Pair<UShort, UShort>(606u, 1366u) // height to width ratio: 2.25

        val expectedSacleWidth = Math.round(
            screenSize.second.toDouble() * mediaSize.first.toDouble() / mediaSize.second.toDouble()
        )
        val expectedResult = Pair(expectedSacleWidth.toUShort(), screenSize.second)

        val scaleSize = getScaledMediaSize(
            screenSize = screenSize, mediaSize = mediaSize, 0u
        )

        Assertions.assertEquals(expectedResult, scaleSize)
    }


    @Test
    fun scaleMediaSize_withLargerMediaHeightThanScreenWithExtraHeight_cacaulateCorrectly() {
        val screenSize = Pair<UShort, UShort>(1080u, 2177u) // height to width ratio: 2.01
        val mediaSize = Pair<UShort, UShort>(606u, 1366u) // height to width ratio: 2.25
        val extraHeight: UShort = 100u

        val expectedSacleWidth = Math.round(
            (screenSize.second.toDouble() + extraHeight.toDouble()) * mediaSize.first.toDouble() / mediaSize.second.toDouble()
        )
        val expectedResult = Pair(expectedSacleWidth.toUShort(), (screenSize.second - extraHeight).toUShort())

        val scaleSize = getScaledMediaSize(
            screenSize = screenSize, mediaSize = mediaSize, extraHeight = extraHeight
        )

        Assertions.assertEquals(expectedResult, scaleSize)
    }

    @Test
    fun scaleMediaSize_withInCorrectMediaSize_returnHeightWithZero() {
        val screenSize = Pair<UShort, UShort>(1080u, 2177u)
        val mediaSize = Pair<UShort, UShort>(291u, 0u)

        val expectedResult = Pair<UShort, UShort>(0u, 0u)

        val scaleSize = getScaledMediaSize(
            screenSize = screenSize, mediaSize = mediaSize, 0u
        )

        Assertions.assertEquals(expectedResult, scaleSize)
    }
}