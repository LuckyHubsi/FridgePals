package com.example.fridgepals.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.ui.view_model.MainViewModel

@Composable
fun CommunityFridge(mainViewModel: MainViewModel, onReserve: (String, String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .shadow(
                10.dp,
                shape = RoundedCornerShape(bottomStart = 54.dp, bottomEnd = 54.dp),
                ambientColor = MaterialTheme.colorScheme.onSecondary
            )
            .clip(RoundedCornerShape(bottomStart = 54.dp, bottomEnd = 54.dp))
            .background(MaterialTheme.colorScheme.primary)
        ,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
                Row(
                    modifier = Modifier.width(450.dp).height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text("Change your location:", style = MaterialTheme.typography.titleMedium)
                }
                Row(
                    modifier = Modifier
                ){
                    LocationDropdownMenu()
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ){
        CustomListView(mainViewModel)
    }

    LazyColumn(
        modifier = Modifier
            .padding(top = 250.dp)
    ) {


        items(mainViewModel.mainViewState.value.communityFridgeItems) { index ->
            RoundedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                buttonContent = { ButtonContentCommunityFridge(index, onReserve) },
                item = index
            )
        }
    }
}