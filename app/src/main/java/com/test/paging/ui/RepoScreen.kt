package com.test.paging.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.test.paging.data.entities.RepositoryResponseItem
import com.test.paging.ui.viewmodel.RepoViewModel


@Composable
fun RepoScreen(
    modifier: Modifier = Modifier,
    viewModel: RepoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val repoList = viewModel.repoPager.collectAsLazyPagingItems()

    when (repoList.loadState.refresh) {
        is LoadState.NotLoading -> Unit
        LoadState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                ErrorItem(modifier = modifier, message = "Error Refreshing")
            }
        }
    }

    LazyColumn {
        items(repoList) { item ->
            item?.let { RepoCard(repo = it, modifier = modifier) }
        }

        when (repoList.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item {
                    LoadingItem(modifier = modifier)
                }
            }
            is LoadState.Error -> {
                item {
                    ErrorItem(message = "Some error occurred", modifier = modifier)
                }
            }
        }

    }
}


@Composable
fun RepoCard(
    modifier: Modifier, repo: RepositoryResponseItem
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 10.dp
    ) {
        Column(modifier = modifier.padding(20.dp)) {
            Text(
                text = repo.name,
                modifier = Modifier.padding(bottom = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            repo.description?.let { Text(text = it) }



            Column(modifier.padding(top = 8.dp)) {
                val textSize = 14.sp
                Row() {
                    Text(text = "Created at :", fontSize = textSize)
                    Text(text = repo.createdAt, fontSize = textSize)
                }
                Row() {
                    Text(text = "Updated at :", fontSize = textSize)
                    Text(text = repo.updatedAt, fontSize = textSize)
                }
                Row() {
                    Text(text = "Pushed at :", fontSize = textSize)
                    Text(text = repo.pushedAt, fontSize = textSize)
                }
            }
        }
    }
}

@Composable
fun LoadingItem(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Center
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .width(42.dp)
                .height(42.dp)
                .padding(8.dp),
            strokeWidth = 5.dp
        )

    }
}

@Composable
fun ErrorItem(
    modifier: Modifier,
    message: String
) {
    Card(
        elevation = 2.dp,
        modifier = modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(8.dp)
        ) {

            Text(
                color = Color.White,
                text = message,
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
    }
}

