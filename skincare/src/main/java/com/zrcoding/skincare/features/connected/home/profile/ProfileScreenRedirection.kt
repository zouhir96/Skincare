package com.zrcoding.skincare.features.connected.home.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.zrcoding.skincare.R
import com.zrcoding.skincare.features.navigation.Screen

data class ProfileScreenRedirection(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val route: String
)

val profileScreenRedirections = listOf(
    ProfileScreenRedirection(
        icon = R.drawable.ic_manage_account,
        title = R.string.my_profile,
        route = Screen.EditAccount.route
    ),
    ProfileScreenRedirection(
        icon = R.drawable.ic_order,
        title = R.string.my_orders,
        route = Screen.Orders.route
    ),
    ProfileScreenRedirection(
        icon = R.drawable.ic_refund,
        title = R.string.my_refunds,
        route = Screen.Refund.route
    ),
)