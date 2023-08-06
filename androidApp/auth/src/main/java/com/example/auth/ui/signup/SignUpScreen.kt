package com.example.auth.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.auth.R
import com.example.auth.ui.navigation.Screen
import com.example.auth.utils.ui.getSnackbarController
import com.example.core.utils.logger.ConsoleLogger
import com.example.core.utils.logger.Logger
import javax.inject.Named

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    @Named("console_logger") logger: Logger = ConsoleLogger(),
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarController = remember {
        getSnackbarController(coroutineScope)
    }

    val snackBarMessage = viewModel.snackBarMessage

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        logger.log("LoginScreen", "LaunchedEffect has called")
        snackBarMessage.collect { messageState ->
            if (messageState.messageId > 0) {
                snackbarController.showSnackbar(
                    scaffoldState = scaffoldState,
                    message = context.resources.getString(messageState.messageId),
                    actionLabel = context.resources.getString(R.string.action_hide)
                )
            } else if (messageState.message != "") {
                snackbarController.showSnackbar(
                    scaffoldState = scaffoldState,
                    message = messageState.message,
                    actionLabel = context.resources.getString(R.string.action_hide)
                )
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState, topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.text_signup))
        })
    }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 30.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.permission),
                    contentDescription = stringResource(id = R.string.text_signup),
                    modifier = Modifier
                        .width(96.dp)
                        .height(96.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                TextField(value = viewModel.email, label = {
                    Text(stringResource(id = R.string.enter_email_hint))
                }, onValueChange = {
                    viewModel.email = it
                }, modifier = Modifier.fillMaxWidth().testTag("username_input"))

                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = viewModel.password, label = {
                    Text(stringResource(id = R.string.enter_password_hint))
                }, onValueChange = {
                    viewModel.password = it
                }, modifier = Modifier.fillMaxWidth().testTag("password_input"))

                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = viewModel.name, label = {
                    Text(stringResource(id = R.string.enter_name_hint))
                }, onValueChange = {
                    viewModel.name = it
                }, modifier = Modifier.fillMaxWidth().testTag("name_input"))

                Button(
                    onClick = {
                        viewModel.register()
                    }, modifier = Modifier
                        .padding(
                            start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp
                        )
                        .testTag("sign_up_button")
                ) {
                    Text(text = stringResource(id = R.string.action_signup))
                }
            }

            TextButton(onClick = {
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.SignUpScreen.route) {
                        inclusive = true
                    }
                }
            }, modifier = Modifier.align(Alignment.BottomCenter).testTag("login_text_button")) {
                Text(text = stringResource(id = R.string.action_login_instead))
            }
        }
    }
}
