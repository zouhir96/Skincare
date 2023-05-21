package com.zrcoding.skincare.ui.home.featured

import com.zrcoding.skincare.common.utils.StringUtils.EMPTY
import com.zrcoding.skincare.ui.common.Filter
import com.zrcoding.skincare.ui.common.filterAll
import com.zrcoding.skincare.ui.home.featured.BuildFeaturedScreenViewStateUseCase.Request
import javax.inject.Inject

class BuildFeaturedScreenViewStateRequestUseCase @Inject constructor() {

    operator fun invoke(searchText: String = EMPTY, filter: Filter? = null): Request {
        return if (searchText.isNotBlank()) {
            filter?.let {
                if (it != filterAll) Request.SearchFiltered(
                    text = searchText,
                    filter = it
                ) else Request.SearchOnly(text = searchText)
            } ?: Request.SearchOnly(text = searchText)
        } else {
            filter?.let {
                if (it != filterAll) Request.FilterOnly(
                    filter = it
                ) else Request.Initial
            } ?: Request.Initial
        }
    }
}