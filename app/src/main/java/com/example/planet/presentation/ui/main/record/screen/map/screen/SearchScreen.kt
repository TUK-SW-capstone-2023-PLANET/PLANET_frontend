package com.example.planet.presentation.ui.main.record.screen.map.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.map.SearchPlace
import com.example.planet.presentation.ui.component.EmptyRecentlySearch
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onBack: () -> Unit,
    returnMainActivity: (DoubleArray) -> Unit
) {
    LaunchedEffect(Unit) {
        searchViewModel.readRecentlySearch(searchViewModel.userId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTextField(onBack = { onBack() }) { text ->
            searchViewModel.searchPlace(text) { returnMainActivity(it) }
        }
        HorizontalDivider(
            thickness = 3.dp,
            color = colorResource(id = R.color.font_background_color3)
        )
        if (searchViewModel.recentlySearch is ApiState.Success<*>) {
            val recentSearchList = (searchViewModel.recentlySearch as ApiState.Success<*>).value as List<SearchPlace>
            if (recentSearchList.isEmpty()) {
                EmptyRecentlySearch()
            } else {
                recentSearchList.forEach { item ->
                    RecentSearchItem(
                        item,
                        onSearch = { searchViewModel.searchPlace(it) { returnMainActivity(it) } },
                        onDelete = { searchViewModel.delete(it)
                        })
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun SearchTextField(onBack: () -> Unit, onClick: suspend (String) -> Unit) {
    var scope = rememberCoroutineScope()
    var text by remember {
        mutableStateOf("")
    }

    val placeholderStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.font_background_color3)
    )

    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable { onBack() }
            )
        },
        placeholder = {
            Text(text = "장소, 주소 검색", style = placeholderStyle)
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .size(26.dp)
                    .clickable { scope.launch { onClick(text) } })
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            imeAction = androidx.compose.ui.text.input.ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                scope.launch { onClick(text) }
            }
        )
    )
}

@Composable
fun RecentSearchItem(
    item: SearchPlace,
    onSearch: suspend (String) -> Unit,
    onDelete: (String) -> Unit
) {
    val dateStyle = TextStyle(
        fontSize = 9.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.font_background_color2)
    )

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .clickable { coroutineScope.launch { onSearch(item.text) } }
            .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (item.addressCheck) Icons.Outlined.LocationOn else Icons.Outlined.Search,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(20.dp)
            )
            Text(text = item.text)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = item.date, style = dateStyle, modifier = Modifier.padding(end = 13.dp))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .noRippleClickable { onDelete(item.text) },
                tint = colorResource(id = R.color.font_background_color3)
            )

        }

    }
}