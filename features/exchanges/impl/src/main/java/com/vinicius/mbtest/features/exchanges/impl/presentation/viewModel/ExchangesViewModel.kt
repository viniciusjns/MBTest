import androidx.lifecycle.viewModelScope
import com.vinicius.mbtest.core.extensions.launchFlow
import com.vinicius.mbtest.core.viewModel.BaseViewModel
import com.vinicius.mbtest.core.viewModel.IViewIntent
import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import com.vinicius.mbtest.features.exchanges.domain.useCase.GetExchangesUseCase
import com.vinicius.mbtest.features.exchanges.impl.presentation.action.ExchangesAction
import com.vinicius.mbtest.features.exchanges.impl.presentation.mapper.toDataUi
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesViewState

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
        if (state.value.exchanges.isNotEmpty()) return

        viewModelScope.launchFlow(
            data = { getExchangesUseCase() },
            onStart = {
                setState { copy(syncState = ExchangesSyncState.Loading) }
            },
            onSuccess = { exchangesList ->
                handleSuccess(exchangesList)
            },
            onError = { exception ->
                handleError(exception)
            }
        )
    }

    private fun handleSuccess(exchangesList: List<Exchange>) {
        val dataUi = exchangesList.map { it.toDataUi() }
        setState {
            copy(exchanges = dataUi, syncState = ExchangesSyncState.Success)
        }
    }

    private fun handleError(exception: Throwable) {
        setState {
            copy(
                syncState = ExchangesSyncState.Error(
                    message = exception.localizedMessage ?: "An error occurred"
                )
            )
        }
    }

    private fun navigateToDetails(exchangeId: String) {
        sendAction { ExchangesAction.NavigateToDetails(exchangeId = exchangeId) }
    }
}