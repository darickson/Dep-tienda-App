package com.example.deptienda.ui.Screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun postScreen_ShowsTitle() {
        composeTestRule.setContent {
            PostScreen()
        }

        // Verificar que muestra el título correcto
        composeTestRule.onNodeWithText("Listado de Posts").assertExists()
    }

    @Test
    fun postScreen_ShowsLoadingState() {
        composeTestRule.setContent {
            PostScreen()
        }

        // Verificar que muestra estado de carga
        composeTestRule.onNodeWithText("Cargando posts...").assertExists()
    }

    @Test
    fun postScreen_RendersWithoutErrors() {
        // Test simple que verifica que la pantalla se renderiza sin crashes
        composeTestRule.setContent {
            PostScreen()
        }

        // Si llega aquí sin errores, el test pasa
        assert(true)
    }
}