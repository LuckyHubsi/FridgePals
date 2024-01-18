package com.example.fridgepals.ui.view

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fridgepals.ui.view_model.MainViewModel

@Composable
fun OwnFridge(mainViewModel: MainViewModel){
    val username = "Ivy"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
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
        ){
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(150.dp, Alignment.CenterHorizontally),
                //verticalAlignment = Alignment.Top,
            ) {
                Text("Hi, $username !", style = MaterialTheme.typography.titleLarge)
                Icon(imageVector = Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 85.dp, start = 15.dp, end = 15.dp)
                ,
                verticalAlignment = Alignment.Top,
            ) {
                Text("Your fridge wants to get rid of:", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .background(Color.Black)
        ) {
            items(2) { index ->
                RoundedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(start = 10.dp, end = 10.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(top = 20.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .absolutePadding(top = 150.dp)
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("People have reserved:", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .background(Color.Black)
        ) {
            items(2) { index ->
                RoundedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(start = 10.dp, end = 10.dp)
                )
            }
        }
    }
}

@Composable
fun RoundedCard(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .shadow(
                5.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = MaterialTheme.colorScheme.onSecondary
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        // Top-left image
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = null,
            modifier = imageModifier
                .size(40.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Gray)
                .padding(8.dp)
        )

        // Texts and buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text("Item Name", style = MaterialTheme.typography.titleMedium)
            Text("Description", style = MaterialTheme.typography.titleSmall, color = Color.Gray)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Edit button
                TextButton(onClick = { onEditClick() }) {
                    Text("Edit")
                }

                // Remove button
                TextButton(onClick = { onRemoveClick() }) {
                    Text("Remove")
                }
            }
        }
    }
}
