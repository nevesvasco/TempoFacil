package pt.umaia.tempofacil.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import pt.umaia.tempofacil.R // Substituir pelo caminho correto do ficheiro de recursos

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onNavigateToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Carregar recursos de imagens e wallpaper (igual ao anterior)
    val logo: Painter = painterResource(id = R.mipmap.logo_foreground)
    val wallpaper: Painter = painterResource(id = R.mipmap.wallpaper_foreground)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = wallpaper,
            contentDescription = "Fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = onNavigateToLogin, // Botão para voltar ao login
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "Voltar para Login",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = logo, contentDescription = "Logotipo", modifier = Modifier.size(200.dp))

            Text(
                text = "Registo",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF003366)),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF003366)),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Password", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF003366)),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (password == confirmPassword) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onRegisterSuccess() // Sucesso no registo
                                } else {
                                    errorMessage = task.exception?.message ?: "Erro ao registar"
                                }
                            }
                    } else {
                        errorMessage = "As passwords não coincidem"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA726) // Amarelo torrado
                )
            ) {
                Text("Registar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = errorMessage, color = Color.Red)
        }
    }
}
