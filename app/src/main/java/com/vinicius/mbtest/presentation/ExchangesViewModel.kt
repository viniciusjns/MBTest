import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicius.mbtest.domain.model.Exchange
import com.vinicius.mbtest.domain.useCase.GetExchangesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ExchangesViewState(
    val isLoading: Boolean = false,
    val exchanges: List<Exchange> = emptyList(),
    val error: String? = null
)

sealed class ExchangesViewEffect {
    data class ShowErrorSnackbar(val message: String) : ExchangesViewEffect()
    // Add other effects like navigation, etc.
}

class ExchangesViewModel(
    private val getExchangesUseCase: GetExchangesUseCase
) : ViewModel() {

    // StateFlow for UI state
    private val _state = MutableStateFlow(ExchangesViewState()) // Initial state
    val state: StateFlow<ExchangesViewState> = _state.asStateFlow()

    // SharedFlow for one-time UI events (effects)
    private val _effect = MutableSharedFlow<ExchangesViewEffect>()
    val effect: SharedFlow<ExchangesViewEffect> = _effect.asSharedFlow()

    fun mockExchanges() {
        // Mock data for testing purposes
        val mockExchanges = listOf(
            Exchange(
                exchangeId = "1",
                website = "https://exchange1.com",
                name = "Exchange One",
                dataQuoteStart = "2020-01-01",
                dataQuoteEnd = "2024-01-01",
                dataOrderBookStart = "2020-01-01",
                dataOrderBookEnd = "2024-01-01",
                dataTradeStart = "2020-01-01",
                dataTradeEnd = "2024-01-01",
                dataSymbolsCount = 100,
                volume1hrsUsd = 5000.0,
                volume1dayUsd = 120000.0,
                volume1mthUsd = 3000000.0
            ),
            Exchange(
                exchangeId = "2",
                website = "https://exchange2.com",
                name = "Exchange Two",
                dataQuoteStart = "2019-06-01",
                dataQuoteEnd = "2024-06-01",
                dataOrderBookStart = "2019-06-01",
                dataOrderBookEnd = "2024-06-01",
                dataTradeStart = "2019-06-01",
                dataTradeEnd = "2024-06-01",
                dataSymbolsCount = 50,
                volume1hrsUsd = 2500.0,
                volume1dayUsd = 60000.0,
                volume1mthUsd = 1500000.0
            ),
            Exchange(
                exchangeId = "3",
                website = "https://exchange3.com",
                name = "Exchange Three",
                dataQuoteStart = null,
                dataQuoteEnd = null,
                dataOrderBookStart = null,
                dataOrderBookEnd = null,
                dataTradeStart = null,
                dataTradeEnd = null,
                dataSymbolsCount = null,
                volume1hrsUsd = null,
                volume1dayUsd = null,
                volume1mthUsd = null
            )
        )
        _state.update { it.copy(exchanges = mockExchanges) }
    }

    fun fetchExchanges() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) } // Set loading state

            getExchangesUseCase()
                .catch { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.localizedMessage ?: "An error occurred"
                        )
                    }
                    _effect.emit(
                        ExchangesViewEffect.ShowErrorSnackbar(
                            exception.localizedMessage ?: "Failed to fetch exchanges"
                        )
                    )
                }
                .collect { exchangesList ->
                    _state.update {
                        it.copy(isLoading = false, exchanges = exchangesList, error = null)
                    }
                }
        }
    }
}