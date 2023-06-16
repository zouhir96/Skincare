package com.zrcoding.skincare.ui.home.featured

import com.zrcoding.skincare.common.utils.StringUtils.EMPTY
import com.zrcoding.skincare.ui.common.domain.model.Filter
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BuildFeaturedScreenViewStateRequestUseCaseTest {

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
    }

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