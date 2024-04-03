package com.tcs.codingassignmenttcs.utils.route

sealed class AppScreen(val route: String) {
    object ProductScreen : AppScreen(ConstantAppScreenName.PRODUCT_SCREEN)
    object DetailsScreen : AppScreen(ConstantAppScreenName.DETAILS_SCREEN)
    object CartScreen : AppScreen(ConstantAppScreenName.CART_SCREEN)

}


object ConstantAppScreenName {
    const val PRODUCT_SCREEN = "product_screen"
    const val DETAILS_SCREEN = "details_screen"
    const val CART_SCREEN = "cart_screen"

}