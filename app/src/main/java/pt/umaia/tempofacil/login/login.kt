package pt.umaia.tempofacil.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import pt.umaia.tempofacil.R

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val firebaseAuth = FirebaseAuth.getInstance()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fundo com wallpaper
        Image(
            painter = painterResource(id = R.mipmap.wallpaper_foreground),
            contentDescription = "Fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Conteúdo principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logotipo
            Image(
                painter = painterResource(id = R.mipmap.logo_foreground),
                contentDescription = "Logotipo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de password com visualTransformation
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Text(
                        text = if (passwordVisible) "Ocultar" else "Mostrar",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de login
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        navController.navigate("home")
                                    } else {
                                        errorMessage = task.exception?.message ?: "Erro desconhecido."
                                    }
                                }
                        } catch (e: Exception) {
                            errorMessage = "Erro ao conectar ao Firebase: ${e.message}"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Mensagem de erro
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botão de registo
            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registar-se")
            }
        }
    }
}
