package com.tcs.codingassignmenttcs.domain.usecase

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import coil.compose.rememberAsyncImagePainter
import com.tcs.codingassignmenttcs.genric.usecase.BaseUseCase
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.repository.ProductRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityRetainedScoped
class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) : BaseUseCase<Unit, Flow<PagingData<Product>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<Product>> {
        return repository.getProducts()
    }
}