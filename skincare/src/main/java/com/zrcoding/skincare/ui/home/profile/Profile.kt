package com.zrcoding.skincare.ui.home.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.BrownWhite80
import com.zrcoding.skincare.ui.theme.SkincareTheme

@Composable
fun Profile(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (infos, spacer, redirections) = createRefs()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.constrainAs(infos) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Box {
                Image(
                    painter = painterResource(id = R.drawable.img_avatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_galery),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(MaterialTheme.shapes.large)
                        .clickable { }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Md Maria charapova",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "+212 6 66666666",
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .constrainAs(spacer) {
                    top.linkTo(infos.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(BrownWhite80, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .constrainAs(redirections) {
                    top.linkTo(spacer.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Redirection(icon = R.drawable.ic_manage_account, text = R.string.my_profile) {}

            Spacer(modifier = Modifier.height(10.dp))
            Redirection(icon = R.drawable.ic_order, text = R.string.my_orders) {}

            Spacer(modifier = Modifier.height(10.dp))
            Redirection(icon = R.drawable.ic_refund, text = R.string.my_refunds) {}

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Brown,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(45.dp)
                    .padding(horizontal = 21.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.common_log_out),
                    style = MaterialTheme.typography.button
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    SkincareTheme(darkTheme = false) {
        Profile(modifier = Modifier.background(MaterialTheme.colors.background))
    }
}

@Composable
fun Redirection(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 21.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(MaterialTheme.shapes.large)
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = stringResource(id = text), style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = Color(0xFFCCD2E3),
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview
@Composable
fun RedirectionPreview() {
    SkincareTheme(darkTheme = false) {
        Redirection(icon = R.drawable.ic_user, text = R.string.profile) {}
    }
}