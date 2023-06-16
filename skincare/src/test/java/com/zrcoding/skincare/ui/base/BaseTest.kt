package com.zrcoding.skincare.ui.base

import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
open class BaseTest {

    @Before
    open fun beforeTest() {
        MockKAnnotations.init(this)
    }
}