package com.example.fridgepals.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fridgepals.R
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.repository.FridgeRepository
import com.example.fridgepals.repository.UserRepository
import com.example.fridgepals.ui.view_model.MainViewModel

@SuppressLint("SuspiciousIndentation", "StateFlowValueCalledInComposition")
@Composable
fun OwnFridge(
    mainViewModel: MainViewModel,
    navController: NavController,
    userId: String,
    onDeleteItem: (FridgeItem) -> Unit,
    onEditItem: (FridgeItem) -> Unit,
) {
    var username by remember { mutableStateOf("Loading...") }

    UserRepository.getUsername { name ->
        username = name
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
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
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            ) {
                Text("Hi, $username!", style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.width(275.dp)
                )
                ProfileDropdownMenu(mainViewModel, { UserRepository.logoutUser() }, userId)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 85.dp, start = 15.dp, end = 15.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    "Your fridge wants to get rid of:",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 190.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { mainViewModel.openDialog() },
                modifier = Modifier
                    .width(225.dp)
                    .height(70.dp)
                    .shadow(
                        5.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = MaterialTheme.colorScheme.onSecondary
                    )
                    .clip(RoundedCornerShape(20.dp)),
                shape = RectangleShape
            ) {
                Text(
                    "Add Item",
                    fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Icon(
                    painter = painterResource(R.drawable.icon_plus),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(top = 265.dp)
        ) {

            items(mainViewModel.mainViewState.value.ownFridgeItemsNotReserved) { index ->
                RoundedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    buttonContent = { ButtonContentOwnFridge(mainViewModel,onDelete = onDeleteItem, index, onEdit = onEditItem) },
                    item = index
                )
                if (mainViewModel.mainViewState.value.currentItemToEdit != null)
                PopUp_Edit(
                    mainViewModel,
                    userId,
                    item = mainViewModel.mainViewState.value.currentItemToEdit!!,
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .background(MaterialTheme.colorScheme.tertiary),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .background(MaterialTheme.colorScheme.tertiary),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "People have reserved:",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            items(mainViewModel.mainViewState.value.ownFridgeItemsReserved) { index ->
                RoundedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    buttonContent = { ButtonContentOwnFridge(mainViewModel,onDelete = onDeleteItem, index, onEdit = onEditItem) },
                    item = index
                )
                if (mainViewModel.mainViewState.value.currentItemToEdit != null)
                PopUp_Edit(
                    mainViewModel,
                    userId,
                    item = mainViewModel.mainViewState.value.currentItemToEdit!!,
                )
            }
        }
    }
    Column() {
        PopUp(
            mainViewModel,
            userId
        )
    }
}

