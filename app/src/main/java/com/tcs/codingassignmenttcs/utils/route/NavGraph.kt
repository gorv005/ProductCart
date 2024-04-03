package com.tcs.codingassignmenttcs.utils.route

import ProductScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tcs.codingassignmenttcs.presentation.cart.CartItemView
import com.tcs.codingassignmenttcs.presentation.cart.CartList
import com.tcs.codingassignmenttcs.presentation.productdetails.ProductDetails
import com.tcs.codingassignmenttcs.utils.Constants.PRODUCT_ID

@Composable
fun NavGraph() {
    val productDetailsArguments = listOf(
        navArgument(PRODUCT_ID) {
            type = NavType.IntType
            defaultValue = 0
        },
    )
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.ProductScreen.route,
    ) {

        composable(route = AppScreen.ProductScreen.route) {
            ProductScreen(
                navController = navController
            )
        }

        composable(
            route = AppScreen.DetailsScreen.route.plus("/{$PRODUCT_ID}"),
            arguments = productDetailsArguments
        ) { entry ->

            var id = entry.arguments?.getInt(PRODUCT_ID)
            ProductDetails(
                navController = navController, id
            )
        }
        composable(
            route = AppScreen.CartScreen.route
        ) { entry ->

            CartList(
                navController = navController
            )

        }
    }
}