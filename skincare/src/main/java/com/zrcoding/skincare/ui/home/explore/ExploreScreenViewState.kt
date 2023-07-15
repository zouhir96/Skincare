package com.zrcoding.skincare.ui.home.explore

import com.zrcoding.skincare.common.domain.model.Filter
import com.zrcoding.skincare.common.domain.model.filterAll
import com.zrcoding.skincare.data.domain.model.Product

sealed interface ExploreScreenViewState {
    object Loading : ExploreScreenViewState
    data class ExploreState(
        val selectedFilter: Filter = filterAll,
        val filters: List<Filter> = emptyList(),
        val products: List<Product> = emptyList()
    ) : ExploreScreenViewState
}