package com.tcs.codingassignmenttcs.presentation.productdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.repository.CartRepository
import com.tcs.codingassignmenttcs.repository.ProductDetailRepository
import com.tcs.codingassignmenttcs.utils.DataState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class ProductDetailsViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var productDetailsViewModel: ProductDetailsViewModel

    private lateinit var productDetailRepository: ProductDetailRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        productDetailRepository = mockk<ProductDetailRepository>()

        productDetailsViewModel = ProductDetailsViewModel(
            productDetailRepository = productDetailRepository
        )
    }

    @Test
    fun test_getProductsDetails() {
        runBlocking {
            val testdata =
                Product(title = "perfume Oil", price = 13, brand = "Impression of Acqua Di Gio")

            coEvery{productDetailRepository.getProductDetail(1)} returns flowOf(DataState.Success(testdata))

            productDetailsViewModel.getProductDetails(1)
            delay(500)

            lateinit var emittedData: Product

            productDetailsViewModel.productDetail.value.let { it ->
                if (it is DataState.Success<Product>) {
                    emittedData = it.data
                }
            }
            TestCase.assertEquals(testdata, emittedData)
        }
    }

    @Test
    fun test_addToCart() {
        runBlocking {
            val testdata =
                Product(title = "perfume Oil", price = 13, brand = "Impression of Acqua Di Gio")


            coEvery{productDetailRepository.addProductToCart(testdata)} returns flowOf(DataState.Success(1))

            productDetailsViewModel.addToCart(product = testdata)
            delay(500)

            var emittedData = -1L

            productDetailsViewModel.addToCartResult.value.let { it ->
                if (it is DataState.Success<Long>) {
                    emittedData = it.data
                }
            }
            TestCase.assertEquals(1, emittedData)
        }
    }
}