package com.tcs.codingassignmenttcs.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.tcs.codingassignmenttcs.R
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.utils.CircularIndeterminateProgressBar
import com.tcs.codingassignmenttcs.utils.DataState
import com.tcs.codingassignmenttcs.utils.pagingLoadingState

@Composable
fun CartList(
    navController: NavController,
) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartProductList = cartViewModel.cartProductList
    val progressBar = remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        cartViewModel.getAllProducts()
    }
    Scaffold(topBar = { ScaffoldWithTopBar(navController = navController) }) {
        Box(modifier = Modifier.padding(it)) {
            CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
            cartProductList.value?.let { it ->
                if (it is DataState.Success<List<Product>>) {
                    val productList = it.data
                    LazyColumn(modifier = Modifier.padding(5.dp)) {
                        items(productList) { product ->
                            CartItemView(product)
                        }
                    }
                }
            }
            cartProductList.pagingLoadingState {
                progressBar.value = it
            }
        }
    }
}


@Composable
fun CartItemView(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.White)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        )
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)

        ) {
            Image(
                painter = rememberAsyncImagePainter(product.thumbnail),
                //painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "ProductImage",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .weight(.4f),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = product?.title!!,
                    color = Color.Black,
                    maxLines = 1,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                )
                Text(
                    text = "Rs " + product.price.toString(),
                    color = Color.Black,
                    fontSize = 14.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 1.dp)
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar(navController: NavController) {

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.product_cart))
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    stringResource(id = R.string.back_button),
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
        ),
    )
}