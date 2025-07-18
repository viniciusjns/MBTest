import androidx.lifecycle.viewModelScope
import com.vinicius.mbtest.core.viewModel.BaseViewModel
import com.vinicius.mbtest.core.viewModel.IViewIntent
import com.vinicius.mbtest.features.exchanges.domain.useCase.GetExchangesUseCase
import com.vinicius.mbtest.features.exchanges.impl.presentation.action.ExchangesAction
import com.vinicius.mbtest.features.exchanges.impl.presentation.mapper.toDataUi
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class ExchangesViewIntent : IViewIntent {
    object FetchExchanges : ExchangesViewIntent()
    data class OnExchangeClicked(val exchangeId: String) : ExchangesViewIntent()
}

class ExchangesViewModel(
    private val getExchangesUseCase: GetExchangesUseCase
) : BaseViewModel<ExchangesViewState, ExchangesAction, ExchangesViewIntent>(ExchangesViewState()) {

    override fun dispatchViewIntent(intent: ExchangesViewIntent) {
        when (intent) {
            is ExchangesViewIntent.FetchExchanges -> fetchExchanges()
            is ExchangesViewIntent.OnExchangeClicked -> navigateToDetails(intent.exchangeId)
            else -> { throw RuntimeException("ViewIntent not mapped") }
        }
    }

    private fun fetchExchanges() {
        viewModelScope.launch {
            setState { this.copy(syncState = ExchangesSyncState.Loading) }

            getExchangesUseCase()
                .catch { exception ->
                    setState {
                        this.copy(syncState = ExchangesSyncState.Error(message = exception.localizedMessage ?: "An error occurred"))
                    }
                }
                .collect { exchangesList ->
                    val dataUi = exchangesList.map { exchanges ->
                        exchanges.toDataUi()
                    }
                    setState {
                        this.copy(exchanges = dataUi, syncState = ExchangesSyncState.Success)
                    }
                }
        }
    }

    private fun navigateToDetails(exchangeId: String) {
        sendAction { ExchangesAction.NavigateToDetails(exchangeId = exchangeId) }
    }
}