package com.zrcoding.skincare.ui.base

import io.mockk.MockKAnnotations
import org.junit.Before

open class BaseTest {

    @Before
    open fun beforeTest() {
        MockKAnnotations.init(this)
    }
}