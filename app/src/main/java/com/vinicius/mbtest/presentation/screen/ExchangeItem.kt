package com.vinicius.mbtest.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vinicius.mbtest.domain.model.Exchange
import com.vinicius.mbtest.presentation.model.ExchangeDataUi

@Composable
fun ExchangeItem(exchangeDataUi: ExchangeDataUi) {
    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .clickable { Log.i("ExchangeItem", "${exchangeDataUi.name}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
//                AsyncImage(
//                    modifier = modifier
//                        .size(56.dp)
//                        .clip(RoundedCornerShape(12.dp)),
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(exchange.iconUrl)
//                        .crossfade(true)
//                        .build(),
//                    contentScale = ContentScale.Crop,
//                    placeholder = painterResource(R.drawable.illu_placeholder),
//                    error = painterResource(R.drawable.illu_placeholder),
//                    contentDescription = null,
//                )
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        text = exchangeDataUi.name!!,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        text = "ID: ${exchangeDataUi.exchangeId}",
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "USD Volume",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "1hr",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .background(
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.primary
                        )
                        .padding(horizontal = 8.dp),
                    text = exchangeDataUi.volume1hrsUsd.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    }
}