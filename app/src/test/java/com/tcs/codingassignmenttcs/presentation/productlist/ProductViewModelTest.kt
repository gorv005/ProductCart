package com.tcs.codingassignmenttcs.presentation.productlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.tcs.codingassignmenttcs.domain.usecase.GetProductsUseCase
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.repository.ProductDetailRepository
import com.tcs.codingassignmenttcs.repository.ProductRepository
import com.tcs.codingassignmenttcs.utils.DataState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class ProductViewModelTest{

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var productViewModel: ProductViewModel

    private lateinit var productRepository: ProductRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        productRepository = mockk<ProductRepository>()
        productViewModel = ProductViewModel(
            getProductsUseCase = GetProductsUseCase(productRepository)
        )
    }

    @Test
    fun test_getProducts() = runBlocking {
        val data = PagingData.from(
            listOf(
                Product(title = "Phone", price = 222, brand = "Samsung"),
                Product(title = "perfume Oil", price = 13, brand = "Impression of Acqua Di Gio"),
            )
        )
        coEvery{productRepository.getProducts()} returns flowOf(data)

        productViewModel.products.value = data

        val emittedData = productViewModel.products.first()
        TestCase.assertEquals(data, emittedData)
    }

}