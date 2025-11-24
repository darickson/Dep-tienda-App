package com.example.deptienda.ui.Screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UploadFileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun uploadFileScreen_ShowsTitle() {
        composeTestRule.setContent {
            UploadFileScreen()
        }

        // Verificar que muestra el título
        composeTestRule.onNodeWithText("Subir imagen").assertExists()
    }

    @Test
    fun uploadFileScreen_ShowsCameraButton() {
        composeTestRule.setContent {
            UploadFileScreen()
        }

        // Verificar que muestra el botón de cámara
        composeTestRule.onNodeWithText("Tomar foto").assertExists()
    }

    @Test
    fun uploadFileScreen_ShowsGalleryButton() {
        composeTestRule.setContent {
            UploadFileScreen()
        }

        composeTestRule.onNodeWithText("Elegir de galería").assertExists()
    }

    @Test
    fun uploadFileScreen_CameraButton_CanBeClicked() {
        composeTestRule.setContent {
            UploadFileScreen()
        }

        composeTestRule.onNodeWithText("Tomar foto").assertExists().performClick()
    }

    @Test
    fun uploadFileScreen_GalleryButton_CanBeClicked() {
        composeTestRule.setContent {
            UploadFileScreen()
        }

        composeTestRule.onNodeWithText("Elegir de galería").assertExists().performClick()
    }

    @Test
    fun uploadFileScreen_BothButtonsExist() {
        composeTestRule.setContent {
            UploadFileScreen()
        }

        composeTestRule.onNodeWithText("Tomar foto").assertExists()
        composeTestRule.onNodeWithText("Elegir de galería").assertExists()
    }
}