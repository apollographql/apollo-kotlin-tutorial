// tell Compose to remember our state across recompositions
val state = remember {
    // our state will be of type UiState and default to the Loading state
    mutableStateOf<UiState>(UiState.Loading)
}

// launch a coroutine and cancel it when needed
LaunchTask {
    try {
        // execute the GraphQL query with ApolloClient
        val launchList = apolloClient(context).query(LaunchListQuery()).execute()
            .data
            .launchConnection
            .launches
        // and update the state
        state.value = UiState.Success(launchList)
    } catch (e: ApolloException) {
        // if there's an error, display an error
        state.value = UiState.Error
    }
}

// Display the UiState
when (val value = state.value) {
    is UiState.Success -> LaunchList(launchList = value.launchList)