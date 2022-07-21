package com.test.paging.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.test.paging.data.remote.ApiService
import com.test.paging.data.remote.RepoRemoteDataSourceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepoViewModel
@Inject constructor(
    apiService: ApiService
) : ViewModel() {

    val repoPager = Pager(
        PagingConfig(pageSize = 10)
    ) {
        RepoRemoteDataSourceImpl(apiService)
    }.flow.cachedIn(viewModelScope)

}
