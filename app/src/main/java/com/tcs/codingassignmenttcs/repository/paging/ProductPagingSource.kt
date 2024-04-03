package com.tcs.codingassignmenttcs.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tcs.codingassignmenttcs.api.ProductAPI
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.utils.Constants.MAX_PAGE_SIZE
import com.tcs.codingassignmenttcs.utils.Constants.PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class ProductPagingSource(
    private val productAPI: ProductAPI,
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: PAGE_INDEX
            productAPI.getProducts(10, currentPage).body().let { resp ->
                LoadResult.Page(
                    data = resp?.products ?: listOf(),
                    prevKey = if (currentPage == PAGE_INDEX) null else currentPage - MAX_PAGE_SIZE,
                    nextKey = if (resp?.products!!.isEmpty()) null else resp.skip?.plus(MAX_PAGE_SIZE)
                )
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

}