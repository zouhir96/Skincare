package com.zrcoding.skincare.ui.home.explore

import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.ui.common.domain.model.Filter
import com.zrcoding.skincare.ui.common.domain.model.filterAll

sealed interface ExploreScreenViewState {
    object Loading : ExploreScreenViewState
    data class ExploreState(
        val selectedFilter: Filter = filterAll,
        val filters: List<Filter> = emptyList(),
        val products: List<Product> = emptyList()
    ) : ExploreScreenViewState
}