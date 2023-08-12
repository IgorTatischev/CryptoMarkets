package com.example.coinview.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coinview.domain.domain.CoinsRepository
import com.example.coinview.domain.model.Market
import retrofit2.HttpException
import java.io.IOException

class MarketsPageSource(
    private val repository: CoinsRepository, private val id: String
) : PagingSource<Int, Market>() {

    override fun getRefreshKey(state: PagingState<Int, Market>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Market> {
        return try {

            val nextPage = params.key ?: 0
            val response = repository.getMarkets(id, nextPage, limit = params.loadSize)

            if (response.isSuccessful) {
                val markets = response.body()?.listMarkets ?: emptyList()
                return LoadResult.Page(
                    data = markets,
                    prevKey = if (nextPage == 0) null else -10,
                    nextKey = if (markets.isNotEmpty()) nextPage.plus(10) else null
                )
            } else {
                LoadResult.Error(throwable = Throwable(message = response.code().toString()))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}