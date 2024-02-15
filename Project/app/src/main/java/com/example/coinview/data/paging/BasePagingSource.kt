package com.example.coinview.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class BasePagingSource<V : Any>(
    private val totalPages: Int? = null,
    private val block: suspend (Int) ->  Response<List<V>>
) : PagingSource<Int, V>() {

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        return try {
            val nextPage = params.key ?: 0
            val response = block(nextPage)

            if (response.isSuccessful) {
                return LoadResult.Page(
                    data = response.body() ?: emptyList(),
                    prevKey = if (nextPage == 0) null else -10,
                    nextKey = if (totalPages != null && nextPage == totalPages) null else nextPage.plus(10)
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

private const val DEFAULT_PAGE_SIZE = 10

fun <V : Any> createPager(
    totalPages: Int? = null,
    pageSize: Int = DEFAULT_PAGE_SIZE,
    block: suspend (Int) -> Response<List<V>>
): Pager<Int, V> = Pager(
    config = PagingConfig(pageSize = pageSize),
    pagingSourceFactory = { BasePagingSource(totalPages, block) }
)
