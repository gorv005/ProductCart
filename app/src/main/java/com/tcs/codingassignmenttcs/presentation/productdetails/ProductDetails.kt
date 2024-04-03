package com.tcs.codingassignmenttcs.presentation.productdetails

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.tcs.codingassignmenttcs.R
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.utils.CircularIndeterminateProgressBar
import com.tcs.codingassignmenttcs.utils.DataState
import com.tcs.codingassignmenttcs.utils.common.PagerIndicator
import com.tcs.codingassignmenttcs.utils.pagingLoadingState
import com.tcs.codingassignmenttcs.utils.route.AppScreen
import kotlinx.coroutines.launch

@Composable
fun ProductDetails(
    navController: NavController,
    productId: Int? = null,
) {
    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
    val productDetail = productDetailsViewModel.productDetail
    val progressBar = remember { mutableStateOf(false) }
    val product =  remember { mutableStateOf(Product()) }

    LaunchedEffect(true) {
        productDetailsViewModel.getProductDetails(productId!!)
    }
    LaunchedEffect(productDetail.value) {
        productDetail.value?.let { it ->
            if (it is DataState.Success<Product>) {
                product.value = it.data
            }
        }
    }


    Scaffold(topBar = { ScaffoldWithTopBar(navController = navController) }) {
        Box(modifier = Modifier.padding(it)) {
            CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if(product.value!=null && product.value.title!=null) {
                    ImageViewPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        images = product?.value?.images!!
                    )

                    productDetailsView(product = product?.value!!)
                    addToCartButton(
                        product = product?.value!!,
                        productDetailsViewModel = productDetailsViewModel,
                        navController
                    )
                }
            }

        }
        productDetail.pagingLoadingState {
            progressBar.value = it
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageViewPager(modifier: Modifier, images: List<String>) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { images.size })
    Box {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            Box(
                modifier = modifier
            ) {

                Image(
                    painter = rememberAsyncImagePainter(images[page]),
                    //painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "ProductImage",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Fit,
                )
            }
        }
        PagerIndicator(modifier = Modifier.align(Alignment.BottomCenter), pagerState = pagerState) {
            scope.launch {
                pagerState.scrollToPage(it)
            }
        }
    }

}



@Composable
fun addToCartButton(product: Product, productDetailsViewModel: ProductDetailsViewModel, navController: NavController) {

    val progressBar = remember { mutableStateOf(false) }
    val addToCart = productDetailsViewModel.addToCartResult
    val context = LocalContext.current
    val text=stringResource(id = R.string.added_to_cart)
    LaunchedEffect(key1 = addToCart.value) {
        addToCart.value?.let { it ->
            if (it is DataState.Success<Long>) {
                val result = it.data
                if( result>0){
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                    navController.navigate(AppScreen.CartScreen.route)
                }
            }
        }
    }

    addToCart.pagingLoadingState {
        progressBar.value = it
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                productDetailsViewModel.addToCart(product)
            }
        ) {
            Text(stringResource(id = R.string.add_to_cart))
        }
    }
}


@Composable
fun productDetailsView(product: Product) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = product.title!!,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "$" + product.price,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(text = product.description!!, style = MaterialTheme.typography.bodyMedium)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar(navController: NavController) {

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.product_details))
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
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = {
                navController.popBackStack()
                navController.navigate(AppScreen.CartScreen.route)
            }) {
                Icon(Icons.Filled.ShoppingCart, tint = Color.White, contentDescription = "Cart")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
        ),
    )
}


