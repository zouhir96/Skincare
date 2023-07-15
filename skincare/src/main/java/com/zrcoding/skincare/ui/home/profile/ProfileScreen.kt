package com.zrcoding.skincare.ui.home.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zrcoding.skincare.R
import com.zrcoding.skincare.common.exts.localizedText
import com.zrcoding.skincare.data.domain.model.GENDER
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.BrownWhite80
import com.zrcoding.skincare.ui.theme.ColumbiaBlue
import com.zrcoding.skincare.ui.theme.SkincareTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileRoute(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {

    val viewState = viewModel.viewState.collectAsState()
    ProfileScreen(
        viewState = viewState.value,
        onProfileImageUploaded = viewModel::onProfileImageUploaded,
        onRedirectionClicked = navigateTo,
        onLogout = viewModel::onLogoutClicked
    )
    LaunchedEffect(Unit) {
        viewModel.goToAuth.collectLatest {
            // TODO Add route to the auth navgraph
            navigateTo("")
        }
    }
}

@Composable
fun ProfileScreen(
    viewState: ProfileScreenViewState,
    onProfileImageUploaded: (Uri) -> Unit,
    onRedirectionClicked: (String) -> Unit,
    onLogout: () -> Unit
) {
    when (viewState) {
        ProfileScreenViewState.Loading -> Unit

        is ProfileScreenViewState.Info -> ProfileScreenContent(
            accountInfo = viewState,
            onProfileImageUploaded = onProfileImageUploaded,
            onRedirectionClicked = onRedirectionClicked,
            onLogout = onLogout
        )
    }
}

@Composable
fun ProfileScreenContent(
    accountInfo: ProfileScreenViewState.Info,
    onProfileImageUploaded: (Uri) -> Unit,
    onRedirectionClicked: (String) -> Unit,
    onLogout: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (info, spacer, redirections, logout) = createRefs()
        AccountInfo(
            modifier = Modifier.constrainAs(info) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            imageUrl = accountInfo.imageUrl,
            fullName = accountInfo.fullName,
            gender = accountInfo.gender,
            phoneNumber = accountInfo.phoneNumber,
            onProfileImageUploaded = onProfileImageUploaded
        )
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .constrainAs(spacer) {
                    top.linkTo(info.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Redirections(
            modifier = Modifier.constrainAs(redirections) {
                top.linkTo(spacer.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            },
            redirections = profileScreenRedirections,
            onRedirectionClicked = onRedirectionClicked
        )
        Button(
            onClick = { onLogout() },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Brown,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(bottom = 14.dp)
                .height(45.dp)
                .padding(horizontal = 21.dp)
                .fillMaxWidth()
                .constrainAs(logout) {
                    bottom.linkTo(parent.bottom)
                }

        ) {
            Text(
                text = stringResource(id = R.string.common_log_out),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SkincareTheme(darkTheme = false) {
        ProfileScreenContent(
            accountInfo = ProfileScreenViewState.Info(
                imageUrl = "",
                fullName = "Dua LIPA",
                gender = GENDER.FEMALE,
                phoneNumber = "+212 666 666666"
            ),
            onProfileImageUploaded = {},
            onRedirectionClicked = {},
            onLogout = {}
        )
    }
}

@Composable
private fun AccountInfo(
    modifier: Modifier = Modifier,
    imageUrl: String,
    fullName: String,
    phoneNumber: String,
    gender: GENDER,
    onProfileImageUploaded: (Uri) -> Unit
) {
    val imageLoadLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { it?.let { onProfileImageUploaded(it) } }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.img_avatar),
            )
            Image(
                painter = painterResource(id = R.drawable.ic_galery),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        imageLoadLauncher.launch("image/*")
                    }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${stringResource(id = gender.localizedText())}. $fullName",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = phoneNumber,
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountInfoPreview() {
    SkincareTheme(darkTheme = false) {
        AccountInfo(
            imageUrl = "",
            fullName = "Dua LIPA",
            phoneNumber = "+212 666 666666",
            gender = GENDER.FEMALE
        ) {}
    }
}

@Composable
fun Redirections(
    modifier: Modifier = Modifier,
    redirections: List<ProfileScreenRedirection>,
    onRedirectionClicked: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(BrownWhite80, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn {
            items(items = redirections, key = { it.route }) {
                RedirectionItem(
                    redirection = it,
                    onClick = { onRedirectionClicked(it.route) }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RedirectionsPreview() {
    SkincareTheme(darkTheme = false) {
        Redirections(
            redirections = profileScreenRedirections,
            onRedirectionClicked = {}
        )
    }
}

@Composable
fun RedirectionItem(
    redirection: ProfileScreenRedirection,
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
                painter = painterResource(id = redirection.icon),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = stringResource(id = redirection.title),
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = ColumbiaBlue,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RedirectionItemPreview() {
    SkincareTheme(darkTheme = false) {
        RedirectionItem(redirection = profileScreenRedirections[0]) {}
    }
}