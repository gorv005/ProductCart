package com.tcs.codingassignmenttcs.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.tcs.codingassignmenttcs.database.ProductDao
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.repository.CartRepository
import com.tcs.codingassignmenttcs.utils.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.bytebuddy.matcher.ElementMatchers.returns
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cartRepository: CartRepository
    private lateinit var cartViewModel: CartViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        cartRepository = mockk<CartRepository>()
        cartViewModel = CartViewModel(cartRepository)

    }


    @Test
    fun test_getProductsList() {
        runBlockingTest {
            val list = listOf(
                Product(title = "Apple phone", price = 13000, brand = "Apple"),
                Product(title = "perfume Oil", price = 13, brand = "Impression of Acqua Di Gio")
            )
            coEvery{cartRepository.getAllCartProduct()} returns flowOf(DataState.Success(list))
            cartViewModel.getAllProducts()
            delay(500)

            cartViewModel.cartProductList.value.let { it ->
                if (it is DataState.Success<List<Product>>) {
                    assertThat(it?.data).isEqualTo(list)
                }

            }
        }
    }
}