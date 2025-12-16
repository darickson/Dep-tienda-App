package com.example.deptienda.ui.Screens

import org.junit.Assert
import org.junit.Test

class ScreensTest {

    @Test
    fun screens_RoutesAreCorrect() {
        // Verificar que todas las rutas están correctamente definidas
        Assert.assertEquals("login_screen", Screens.LoginScreen.route)
        Assert.assertEquals("home_screen", Screens.HomeScreen.route)
        Assert.assertEquals("cart_screen", Screens.CartScreen.route)
        Assert.assertEquals("checkout_screen", Screens.CheckoutScreen.route)
        Assert.assertEquals("product_detail_screen", Screens.ProductDetailScreen.route)
        Assert.assertEquals("profile_screen", Screens.ProfileScreen.route)
        Assert.assertEquals("categories_screen", Screens.CategoriesScreen.route)
    }

    @Test
    fun screens_WithArgs_GeneratesCorrectRoute() {
        // Verificar que withArgs genera rutas correctamente
        val routeWithArgs = Screens.ProductDetailScreen.withArgs("123")
        Assert.assertEquals("product_detail_screen/123", routeWithArgs)

        val routeWithMultipleArgs = Screens.ProductDetailScreen.withArgs("123", "blue")
        Assert.assertEquals("product_detail_screen/123/blue", routeWithMultipleArgs)
    }

    @Test
    fun screens_AllScreensExist() {
        // Verificar que todas las screens están definidas
        val screens = listOf(
            Screens.LoginScreen,
            Screens.HomeScreen,
            Screens.CartScreen,
            Screens.CheckoutScreen,
            Screens.ProductDetailScreen,
            Screens.ProfileScreen,
            Screens.CategoriesScreen
        )

        Assert.assertEquals(7, screens.size)
    }

    @Test
    fun screens_BasicTest() {
        // Test más simple - solo verifica algunas rutas principales
        Assert.assertEquals("home_screen", Screens.HomeScreen.route)
        Assert.assertEquals("cart_screen", Screens.CartScreen.route)
        Assert.assertEquals("profile_screen", Screens.ProfileScreen.route)
    }
}