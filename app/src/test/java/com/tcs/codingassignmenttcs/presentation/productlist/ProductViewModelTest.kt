package com.tcs.codingassignmenttcs.presentation.productlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.tcs.codingassignmenttcs.domain.usecase.GetProductsUseCase
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.repository.ProductRepository
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

    @Mock
    private lateinit var productRepository: ProductRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        MockitoAnnotations.initMocks(this)
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
        Mockito.`when`(productRepository.getProducts()).thenReturn(flowOf(data))
        productViewModel.products.value = data

        val emittedData = productViewModel.products.first()
        TestCase.assertEquals(data, emittedData)
    }

}