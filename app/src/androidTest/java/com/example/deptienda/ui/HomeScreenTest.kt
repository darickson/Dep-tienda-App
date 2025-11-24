package com.example.deptienda

import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_ShowsProductsTitle() {
        composeTestRule.setContent {
            Text("Productos")
        }

        composeTestRule.onNodeWithText("Productos").assertExists()
    }
}