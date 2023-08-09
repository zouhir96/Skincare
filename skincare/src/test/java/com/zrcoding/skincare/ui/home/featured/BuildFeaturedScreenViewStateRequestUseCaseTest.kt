package com.zrcoding.skincare.ui.home.featured

import com.zrcoding.skincare.common.domain.model.Filter
import com.zrcoding.skincare.common.utils.StringUtils.EMPTY
import com.zrcoding.skincare.features.connected.home.featured.BuildFeaturedScreenViewStateRequestUseCase
import com.zrcoding.skincare.features.connected.home.featured.BuildFeaturedScreenViewStateUseCase
import com.zrcoding.skincare.ui.base.BaseTest
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Assert
import org.junit.Test

class BuildFeaturedScreenViewStateRequestUseCaseTest : BaseTest() {

    @InjectMockKs
    lateinit var useCase: BuildFeaturedScreenViewStateRequestUseCase

    private val searchText = "query"
    private val filter = Filter("someId", "someName")

    @Test
    fun searchEmptyFilterNull() {
        // GIVEN
        val expected = BuildFeaturedScreenViewStateUseCase.Request.Initial

        // WHEN
        val result = useCase(searchText = EMPTY, filter = null)

        // THEN
        Assert.assertEquals(expected, result)
    }

    @Test
    fun searchTextFilterNull() {
        // GIVEN
        val expected = BuildFeaturedScreenViewStateUseCase.Request.SearchOnly(searchText)

        // WHEN
        val result = useCase(searchText = searchText, filter = null)

        // THEN
        Assert.assertEquals(expected, result)
    }

    @Test
    fun searchEmptyFilterNotNull() {
        // GIVEN
        val expected = BuildFeaturedScreenViewStateUseCase.Request.FilterOnly(filter)

        // WHEN
        val result = useCase(searchText = EMPTY, filter = filter)

        // THEN
        Assert.assertEquals(expected, result)
    }

    @Test
    fun searchNotEmptyFilterNotNull() {
        // GIVEN
        val expected = BuildFeaturedScreenViewStateUseCase.Request.SearchFiltered(
            text = searchText,
            filter = filter
        )

        // WHEN
        val result = useCase(searchText = searchText, filter = filter)

        // THEN
        Assert.assertEquals(expected, result)
    }
}