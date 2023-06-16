package com.zrcoding.skincare.ui.home.featured

import com.zrcoding.skincare.common.utils.StringUtils.EMPTY
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.ui.common.domain.model.Filter
import com.zrcoding.skincare.ui.common.domain.model.filterAll

data class FeaturedScreenViewState(
    val searchText: String = EMPTY,
    val products: List<Product> = emptyList(),
    val filters: List<Filter> = emptyList(),
    val selectedFilter: Filter = filterAll,
    val newestProduct: Product? = null
)