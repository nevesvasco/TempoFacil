package pt.umaia.tempofacil.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import pt.umaia.tempofacil.R

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Carregar os recursos de imagem
    val logo: Painter = painterResource(id = R.mipmap.logo_foreground)
    val wallpaper: Painter = painterResource(id = R.mipmap.wallpaper_foreground)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Fundo com wallpaper
        Image(
            painter = wallpaper,
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
                painter = logo,
                contentDescription = "Logotipo",
                modifier = Modifier
                    .size(400.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Login",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
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
                    // Alternar visibilidade da password
                    Text(
                        text = if (passwordVisible) "Mostrar" else "Ocultar",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        errorMessage = "Por favor, preencha todos os campos."
                        return@Button
                    }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate("home")
                            } else {
                                val error = task.exception?.message ?: "Erro ao fazer login"
                                errorMessage = error
                                // Log para ajudar a depurar o erro
                                task.exception?.printStackTrace()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth() // O botão ocupa toda a largura do ecrã
            ) {
                Text("Login")
            }


            Spacer(modifier = Modifier.height(8.dp))

            Text(text = errorMessage, color = androidx.compose.ui.graphics.Color.Red)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier.fillMaxWidth() // O botão ocupa toda a largura do ecrã
            ) {
                Text("Registar-se")
            }
        }
    }
}