package com.example.movieappcompose.movieList.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieappcompose.models.ListItem
import com.example.movieappcompose.screens.retrofit.ApiService
import com.example.movieappcompose.utils.MockData

class MoviePagingSource(private val apiService: ApiService): PagingSource<Int, ListItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        return try {
            val pageNumber = params.key ?: 1

            val response = apiService.getPopularMovie(pageNumber)
            val advert = MockData().data

            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            var nextKey = if (pageNumber < response.totalPages) pageNumber + 1 else null

            var list = buildList<ListItem> {
                addAll(response.results.map { movie -> ListItem.Movie(movie) })
                add(ListItem.Ad(advert[(0..4).random()]))
                add(10, ListItem.Ad(advert[(0..4).random()]))
            }
//            list.toMutableList().add(10, ListItem.Ad(advert[(0..4).random()]))

            LoadResult.Page(
                data = list,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}