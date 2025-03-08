package app.helium.heliumapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import app.helium.heliumapp.R
import app.helium.heliumapp.ui.navigation.Screen
import app.helium.heliumapp.ui.theme.PrimaryColor
import app.helium.heliumapp.ui.theme.TertiaryColor
import app.helium.heliumapp.ui.theme.TransparentColor
import app.helium.heliumapp.ui.theme.createGradientEffect

@Destination
@Composable
fun RegistrationScreen(
    navController: NavController
    // TODO-FIXME navigator: DestinationsNavigator
) {
    val gradientColors = listOf(
        PrimaryColor,
        PrimaryColor
    )
    var showExpandedText by remember {
        mutableStateOf(false)
    }
    val passwordVector = painterResource(id = R.drawable.password_eye)
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = createGradientEffect(
                    colors = gradientColors,
                    isVertical = true
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.appicon),
                contentDescription = stringResource(id = R.string.helium_logo_content_description),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
            )
            ClickableText(
                text = AnnotatedString("Contacts"),
                style = MaterialTheme.typography.h3.copy(color = Color.White),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    showExpandedText = !showExpandedText
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = showExpandedText) {
                Text(
                    text = "Powered by Helium",
                    color = Color.White,
                    style = MaterialTheme.typography.body1.copy(color = Color.White),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                OutlinedTextField(
                    value = emailValue.value,
                    onValueChange = { emailValue.value = it },
                    label = { Text(text = "Email", color = Color.White) },
                    placeholder = { Text(text = "Enter email address", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.8f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        textColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = passwordValue.value,
                    onValueChange = { passwordValue.value = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
                            Icon(
                                painter = passwordVector, contentDescription = "Password icon",
                                tint = if (passwordVisibility.value) PrimaryColor else Color.Gray
                            )
                        }
                    },
                    label = { Text(text = "Password", color = Color.White) },
                    placeholder = { Text(text = "Enter the password", color = Color.White) },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        textColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {

                        // navController.popBackStack()

                        navController.navigate(Screen.MainScreen.route) {

                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .wrapContentHeight(), contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "Forgot my password",
                        modifier = Modifier.clickable(onClick = {
                        }), color = PrimaryColor, fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Create an account",
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate(Screen.MainScreen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }

                    })
                )
                Spacer(modifier = Modifier.padding(20.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        /* TODO-FIXME
                    navigator.navigate(
                    )
                    */
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(TransparentColor),
                    colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
                ) {
                    Text(
                        text = "Back"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }

}

@Composable
@Preview
fun RegistrationScreenPreview() {
    // TODO-FIXME-CLEANUP LandingScreen(navigator = MockDestinationsNavigator())
    RegistrationScreen(navController = rememberNavController())
}
