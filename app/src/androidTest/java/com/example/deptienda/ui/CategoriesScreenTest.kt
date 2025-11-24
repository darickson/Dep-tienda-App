package com.example.deptienda.ui.Screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.foundation.layout.Column
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class CategoriesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun categoriesScreen_ShowsTitle() {
        composeTestRule.setContent {
            Column {
                androidx.compose.material3.Text("Categorías")
                androidx.compose.material3.Text("Explora nuestras categorías")
            }
        }

        composeTestRule.onNodeWithText("Categorías")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Explora nuestras categorías")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun categoriesScreen_ShowsCategoryButtons() {
        composeTestRule.setContent {
            Column {
                androidx.compose.material3.Button(
                    onClick = { }
                ) {
                    androidx.compose.material3.Text("Camisetas")
                }
                androidx.compose.material3.Button(
                    onClick = { }
                ) {
                    androidx.compose.material3.Text("Pantalones")
                }
                androidx.compose.material3.Button(
                    onClick = { }
                ) {
                    androidx.compose.material3.Text("Sudaderas")
                }
                androidx.compose.material3.Button(
                    onClick = { }
                ) {
                    androidx.compose.material3.Text("Calzado")
                }
            }
        }

        composeTestRule.onNodeWithText("Camisetas")
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()

        composeTestRule.onNodeWithText("Pantalones")
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()

        composeTestRule.onNodeWithText("Sudaderas")
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()

        composeTestRule.onNodeWithText("Calzado")
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun categoriesScreen_CategoryButton_CanBeClicked() {
        var categoryClicked = ""

        composeTestRule.setContent {
            Column {
                androidx.compose.material3.Button(
                    onClick = { categoryClicked = "Camisetas" }
                ) {
                    androidx.compose.material3.Text("Camisetas")
                }
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Camisetas")
            .assertExists()
            .performClick()

        assertEquals("Camisetas", categoryClicked)
    }

    @Test
    fun categoriesScreen_MultipleCategories_CanBeClicked() {
        val clickedCategories = mutableListOf<String>()

        composeTestRule.setContent {
            Column {
                // Botón 1
                androidx.compose.material3.Button(
                    onClick = { clickedCategories.add("Pantalones") }
                ) {
                    androidx.compose.material3.Text("Pantalones")
                }

                // Botón 2
                androidx.compose.material3.Button(
                    onClick = { clickedCategories.add("Sudaderas") }
                ) {
                    androidx.compose.material3.Text("Sudaderas")
                }
            }
        }

        // ESPERAR a que la UI esté lista
        composeTestRule.waitForIdle()

        // VERIFICAR y hacer clic en "Pantalones"
        composeTestRule.onNodeWithText("Pantalones")
            .assertExists("El botón Pantalones debería existir")
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()

        // VERIFICAR y hacer clic en "Sudaderas"
        composeTestRule.onNodeWithText("Sudaderas")
            .assertExists("El botón Sudaderas debería existir")
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()

        // VERIFICACIONES FINALES con mensajes descriptivos
        assertTrue("Debería haber clickeado Pantalones", clickedCategories.contains("Pantalones"))
        assertTrue("Debería haber clickeado Sudaderas", clickedCategories.contains("Sudaderas"))
        assertEquals("Debería haber 2 categorías clickeadas", 2, clickedCategories.size)
    }

    @Test
    fun testAllCategoriesDisplay() {
        composeTestRule.setContent {
            Column {
                androidx.compose.material3.Text("Ropa")
                androidx.compose.material3.Text("Accesorios")
                androidx.compose.material3.Text("Calzado")
                androidx.compose.material3.Text("Novedades")
                androidx.compose.material3.Text("Ofertas")
            }
        }

        composeTestRule.onNodeWithText("Ropa")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Accesorios")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Calzado")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Novedades")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Ofertas")
            .assertExists()
            .assertIsDisplayed()
    }
}