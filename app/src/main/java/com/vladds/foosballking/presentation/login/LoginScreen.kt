package com.vladds.foosballking.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.vladds.foosballking.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccessful: () -> Unit,
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ClickableText(
                text = AnnotatedString(stringResource(R.string.sign_up)),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = {}
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val username = remember { mutableStateOf(TextFieldValue("")) }
            val password = remember { mutableStateOf(TextFieldValue("")) }

            Text(text = "Login", style = MaterialTheme.typography.displayLarge)

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                label = { Text(text = stringResource(R.string.email_address)) },
                value = username.value,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 30.dp, vertical = 0.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { username.value = it })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                label = { Text(text = stringResource(R.string.password)) },
                value = password.value,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 30.dp, vertical = 0.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { onLoginSuccessful() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 30.dp, vertical = 0.dp)
            ) {
                Text(text = stringResource(R.string.login))
            }
            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                text = AnnotatedString(stringResource(R.string.forgot_password)),
                onClick = {}
            )
        }
    }
}
