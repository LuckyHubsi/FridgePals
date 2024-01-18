package com.example.fridgepals.ui.view

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fridgepals.R
import com.example.fridgepals.ui.view_model.MainViewModel


@SuppressLint("SuspiciousIndentation")
@Composable
fun OwnFridge(mainViewModel: MainViewModel) {
    val username = "Ivy"

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
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(150.dp, Alignment.CenterHorizontally),
            ) {
                Text("Hi, $username !", style = MaterialTheme.typography.titleLarge)
                Icon(
                    painter = painterResource(R.drawable.icon_menu),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(64.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 85.dp, start = 15.dp, end = 15.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Text("Your fridge wants to get rid of:", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
            }
        }


    LazyColumn(
        modifier = Modifier
            .padding(top = 175.dp)
    ) {


        items(2) { index ->
            RoundedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .padding(start = 10.dp, end = 10.dp)
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
                        .background(MaterialTheme.colorScheme.tertiary)
                    ,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("People have reserved:", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
                }
            }
        }

        items(4) { index ->
            RoundedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .padding(start = 10.dp, end = 10.dp)
            )
        }
    }
}

