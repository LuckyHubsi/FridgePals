package com.example.fridgepals.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.data.model.Reservations
import com.example.fridgepals.ui.view_model.MainViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun ReservedItems(mainViewModel: MainViewModel, reservedItems: List<FridgeItem>, reservationsList: List<Reservations>) {
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
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("You have reserved these items:", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
    }

    LazyColumn(
        modifier = Modifier
            .padding(top = 180.dp)
    ) {


        items(reservedItems) { index ->
            val reservation = reservationsList.find {it.itemId == index.itemId}
            reservation?.let {
                RoundedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                buttonContent = { ButtonContentReservedItems() },
                item = index
            ) }
        }
    }
}
