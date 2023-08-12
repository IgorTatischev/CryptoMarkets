package com.example.coinview.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coinview.domain.domain.CoinsRepository
import com.example.coinview.domain.model.Coin
import retrofit2.HttpException
import java.io.IOException

class CoinsPageSource(
    private val repository: CoinsRepository,
) : PagingSource<Int, Coin>() {

    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        return try {

            val nextPage = params.key ?: 0
            val response = repository.getCoins(nextPage, limit = params.loadSize)

            if (response.isSuccessful) {
                val coins = response.body()?.listCoins ?: emptyList()
                return LoadResult.Page(
                    data = coins,
                    prevKey = if (nextPage == 0) null else -100,
                    nextKey = if (coins.isNotEmpty()) nextPage.plus(100) else null
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