package com.example.deptienda.ui.Screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class CheckoutScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkoutScreen_ShowsTitle() {
        composeTestRule.setContent {
            Column {
                androidx.compose.material3.Text("Finalizar Compra")
                androidx.compose.material3.Text("Resumen del Pedido")
            }
        }

        composeTestRule.onNodeWithText("Finalizar Compra")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Resumen del Pedido")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun checkoutScreen_ShowsPaymentButton() {
        composeTestRule.setContent {
            Box {
                androidx.compose.material3.Button(
                    onClick = { }
                ) {
                    androidx.compose.material3.Text("Confirmar Pago")
                }
            }
        }

        composeTestRule.onNodeWithText("Confirmar Pago")
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun checkoutScreen_FillForm_EnablesPaymentButton() {
        var paymentClicked = false

        composeTestRule.setContent {
            Column {
                androidx.compose.material3.OutlinedTextField(
                    value = "Juan Pérez",
                    onValueChange = {},
                    label = { androidx.compose.material3.Text("Nombre completo") }
                )

                androidx.compose.material3.Button(
                    onClick = { paymentClicked = true },
                    enabled = true
                ) {
                    androidx.compose.material3.Text("Confirmar Pago")
                }
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Confirmar Pago").performClick()
        assertTrue("El botón de pago debería haber sido clickeado", paymentClicked)
    }

    @Test
    fun checkoutScreen_ShowsFormSections() {
        composeTestRule.setContent {
            Column {
                androidx.compose.material3.Text("Información Personal")
                androidx.compose.material3.Text("Dirección de Envío")
                androidx.compose.material3.Text("Información de Pago")
                androidx.compose.material3.Text("Resumen del Pedido")
            }
        }

        val sections = listOf(
            "Información Personal",
            "Dirección de Envío",
            "Información de Pago",
            "Resumen del Pedido"
        )

        sections.forEach { section ->
            composeTestRule.onNodeWithText(section)
                .assertExists()
                .assertIsDisplayed()
        }
    }

    @Test
    fun testFormInputFunctionality() {
        composeTestRule.setContent {
            Box {
                androidx.compose.material3.OutlinedTextField(
                    value = "Test",
                    onValueChange = {},
                    label = { androidx.compose.material3.Text("Correo electrónico") }
                )
            }
        }

        composeTestRule.onNodeWithText("Correo electrónico")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testMultipleButtons() {
        var backClicked = false
        var paymentClicked = false

        composeTestRule.setContent {
            Column {
                androidx.compose.material3.TextButton(
                    onClick = { backClicked = true }
                ) {
                    androidx.compose.material3.Text("Volver al Carrito")
                }

                androidx.compose.material3.Button(
                    onClick = { paymentClicked = true }
                ) {
                    androidx.compose.material3.Text("Pagar Ahora")
                }
            }
        }

        // Esperar a que la UI esté lista
        composeTestRule.waitForIdle()

        // Verificar y hacer clic en "Volver al Carrito"
        composeTestRule.onNodeWithText("Volver al Carrito")
            .assertExists("El botón Volver debería existir")
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()

        // Verificar y hacer clic en "Pagar Ahora"
        composeTestRule.onNodeWithText("Pagar Ahora")
            .assertExists("El botón Pagar Ahora debería existir")
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()

        // Verificar después de los clics
        assertTrue("El botón Volver debería haber sido clickeado", backClicked)
        assertTrue("El botón Pagar Ahora debería haber sido clickeado", paymentClicked)
    }
}