import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.tcs.codingassignmenttcs.R
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.presentation.productlist.ProductViewModel
import com.tcs.codingassignmenttcs.utils.ErrorMessage
import com.tcs.codingassignmenttcs.utils.LoadingNextPageItem
import com.tcs.codingassignmenttcs.utils.PageLoader
import com.tcs.codingassignmenttcs.utils.route.AppScreen

@Composable
fun ProductScreen(
    navController: NavController,
) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val products: LazyPagingItems<Product> = productViewModel.products.collectAsLazyPagingItems()
    Scaffold(topBar = { ScaffoldWithTopBar(navController = navController) }) {
        Box(modifier = Modifier.padding(it)) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                items(products.itemCount) {
                    ItemLayout(product = products[it]!!,
                        onClick = { index ->
                            navController.navigate(AppScreen.DetailsScreen.route.plus("/${index}"))
                        })
                }
                products.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                PageLoader(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = products.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillMaxWidth(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = products.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier,
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemLayout(
    product: Product, onClick: (category: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
            .padding(16.dp)
            .clickable {
                onClick(product.id!!)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        )
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .weight(10f)

        ) {
            Image(
                painter = rememberAsyncImagePainter(product.thumbnail),
                //painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "ProductImage",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.dp)
                    .weight(.6f),
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = product?.title!!,
                color = Color.Black,
                maxLines = 1,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp)
                    .weight(.2f)
            )
            Text(
                text = "Rs " + product.price.toString(),
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 1.dp)
                    .weight(.2f)
            )
        }
    }
}

@Preview
@Composable
fun prev(){
    ProductScreen(navController = rememberNavController())
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar(navController: NavController) {

            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.product_list))
                },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = { navController.navigate(AppScreen.CartScreen.route) }) {
                        Icon(Icons.Filled.ShoppingCart, tint = Color.White, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            )
}

