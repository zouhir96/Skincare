package com.zrcoding.skincare.features.connected.home.explore

import com.zrcoding.skincare.common.domain.model.Filter
import com.zrcoding.skincare.common.domain.model.filterAll
import com.zrcoding.skincare.data.domain.model.Product

sealed interface ExploreScreenViewState {
    data object Loading : ExploreScreenViewState
    data object EmptyState : ExploreScreenViewState
    data class ExploreState(
        val selectedFilter: Filter = filterAll,
        val filters: List<Filter> = emptyList(),
        val products: List<Product> = emptyList()
    ) : ExploreScreenViewState
}