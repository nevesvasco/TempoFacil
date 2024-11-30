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
import pt.umaia.tempofacil.R // Certifique-se de que o caminho está correto

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Carregar os recursos de imagem
    val logo: Painter = painterResource(id = R.mipmap.logo_foreground) // Substituir pelo nome correto do ficheiro do logotipo
    val wallpaper: Painter = painterResource(id = R.mipmap.wallpaper_foreground) // Substituir pelo nome correto do ficheiro do wallpaper

    Box(
        modifier = Modifier.fillMaxSize()
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
                    .size(200.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Login",
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
                    .background(Color(0xFF003366)), // Azul escuro consistente
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
                    .background(Color(0xFF003366)), // Azul escuro consistente
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                onLoginSuccess() // Navegar para a página home
                            } else {
                                errorMessage = task.exception?.message ?: "Erro ao fazer login"
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA726) // Amarelo torrado consistente
                )
            ) {
                Text("Login", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mensagem de erro
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botão para registo
            Button(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2) // Azul consistente com a aplicação
                )
            ) {
                Text("Registar-se", color = Color.White)
            }
        }
    }
}
