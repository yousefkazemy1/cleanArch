package com.example.auth.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.core.component.ui.compose.DialogBoxLoading
import com.example.core.component.ui.compose.navigation.BottomBarScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//@Preview
//@Composable
//fun LoginPreView() {
//    LoginUI(
//        modifier = Modifier.padding(),
//        onUsernameChanged = {},
//        onPasswordChanged = {},
//        onLogin = {},
//        onSignup = {}
//    )
//}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val snackbarController = remember {
        getSnackbarController(coroutineScope)
    }

    val loadingDialog by viewModel.loadingDialog

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.snackBarMessage.collect { messageState ->
            if (messageState.messageId > 0) {
                if (messageState.messageId == R.string.logged_in_successfully) {
//                    val intent = (context.applicationContext as EntryPointHolder).getMainIntent(context)
//                    context.startActivity(intent)
                    withContext(Dispatchers.Main) {
                        navController.navigate(BottomBarScreen.Home.route) {
                            popUpTo(Screen.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                } else {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = context.resources.getString(messageState.messageId),
                        actionLabel = context.resources.getString(R.string.action_hide)
                    )
                }
            } else if (messageState.message.isNotEmpty()) {
                snackbarController.showSnackbar(
                    scaffoldState = scaffoldState,
                    message = messageState.message,
                    actionLabel = context.resources.getString(R.string.action_hide)
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.text_login))
            })
        },
    ) { padding ->
        LoginUI(
            modifier = Modifier.padding(padding),
            username = viewModel.username,
            password = viewModel.password,
            loadingDialog = loadingDialog,
            onUsernameChanged = {
                viewModel.username = it
            },
            onPasswordChanged = {
                viewModel.password = it
            },
            onLogin = {
                viewModel.login()
            },
            onSignup = {
                navController.navigate(Screen.SignUpScreen.route) {
                    popUpTo(Screen.LoginScreen.route) {
                        inclusive = true
                    }
                }
            }
        )
    }

}

@Composable
fun LoginUI(
    modifier: Modifier = Modifier,
    username: String = "",
    password: String = "",
    loadingDialog: Boolean = false,
    onUsernameChanged: (value: String) -> Unit,
    onPasswordChanged: (value: String) -> Unit,
    onLogin: () -> Unit,
    onSignup: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.permission),
                contentDescription = stringResource(id = R.string.text_login),
                modifier = Modifier
                    .width(96.dp)
                    .height(96.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            LoginInput(
                modifier = Modifier.testTag("username_input"),
                hint = stringResource(id = R.string.enter_username_hint),
                inputValue = username
            ) {
                onUsernameChanged(it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LoginInput(
                modifier = Modifier.testTag("password_input"),
                hint = stringResource(id = R.string.enter_password_hint),
                inputValue = password
            ) {
                onPasswordChanged(it)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onLogin()
                }, modifier = Modifier
                    .padding(
                        start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp
                    )
                    .testTag("login_button")
            ) {
                Text(text = stringResource(id = R.string.action_login))
            }
        }

        TextButton(onClick = {
            onSignup()
        }, modifier = Modifier.align(Alignment.BottomCenter).testTag("sign_up_text_button")) {
            Text(text = stringResource(id = R.string.action_signup_instead))
        }

        if (loadingDialog) {
            DialogBoxLoading(
                modifier = Modifier.align(Alignment.Center).testTag("loading_dialog")
            ) {

            }
        }
    }
}

@Composable
fun LoginInput(
    modifier: Modifier = Modifier,
    hint: String,
    inputValue: String,
    onInputValueChanged: (value: String) -> Unit
) {
    TextField(
        value = inputValue,
        label = {
             Text(text = hint)
        },
        onValueChange = {
            onInputValueChanged(it)
        },
        modifier = modifier
            .fillMaxWidth()
    )
}