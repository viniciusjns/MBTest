import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinicius.mbtest.core.BaseViewModel
import com.vinicius.mbtest.core.IViewIntent
import com.vinicius.mbtest.domain.useCase.GetExchangesUseCase
import com.vinicius.mbtest.presentation.action.ExchangesAction
import com.vinicius.mbtest.presentation.mapper.toDataUi
import com.vinicius.mbtest.presentation.state.ExchangesSyncState
import com.vinicius.mbtest.presentation.state.ExchangesViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class ExchangesViewIntent : IViewIntent {
    object FetchExchanges : ExchangesViewIntent()
}

class ExchangesViewModel(
    private val getExchangesUseCase: GetExchangesUseCase
) : BaseViewModel<ExchangesViewState, ExchangesAction, ExchangesViewIntent>(ExchangesViewState()) {

    override fun dispatchViewIntent(intent: ExchangesViewIntent) {
        when (intent) {
            is ExchangesViewIntent.FetchExchanges -> fetchExchanges()
            else -> { throw RuntimeException("ViewIntent not mapped") }
        }
    }

    private fun fetchExchanges() {
        viewModelScope.launch {
            setState { this.copy(syncState = ExchangesSyncState.Loading) } // Set loading state

            getExchangesUseCase()
                .catch { exception ->
                    setState {
                        this.copy(syncState = ExchangesSyncState.Error(message = exception.localizedMessage ?: "An error occurred"))
                    }
                    sendAction {
                        ExchangesAction.ShowErrorSnackbar(
                            exception.localizedMessage ?: "Failed to fetch exchanges"
                        )
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
}