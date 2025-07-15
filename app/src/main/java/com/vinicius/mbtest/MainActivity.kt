package com.vinicius.mbtest

import ExchangesViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vinicius.mbtest.domain.model.Exchange
import com.vinicius.mbtest.ui.theme.MBTestTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MBTestTheme {
                ExchangeScreen()
            }
        }
    }
}

@Composable
fun ExchangeScreen(
    viewModel: ExchangesViewModel = koinViewModel()
) {
    val viewState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.mockExchanges()
    }

    if (viewState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (viewState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Erro: ${viewState.error}")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(viewState.exchanges, key = { it.exchangeId!! }) { exchange ->
                ExchangeItem(exchange)
            }
        }
    }
}

@Composable
fun ExchangeItem(exchange: Exchange) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
//        AsyncImage(
//            model = exchange.website,
//            contentDescription = null,
//            modifier = Modifier.size(64.dp)
//        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = exchange.name ?: "Sem nome")
            Text(text = "Símbolos: ${exchange.dataSymbolsCount ?: 0}")
        }
    }
}