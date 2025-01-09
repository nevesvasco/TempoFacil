package pt.umaia.tempofacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pt.umaia.tempofacil.data.WeatherRepository
import pt.umaia.tempofacil.login.LoginScreen
import pt.umaia.tempofacil.login.RegisterScreen
import pt.umaia.tempofacil.ui.home.HomeScreen
import pt.umaia.tempofacil.ui.home.HomeScreenWrapper
import pt.umaia.tempofacil.ui.home.HomeViewModel
import pt.umaia.tempofacil.ui.theme.TempoFacilTheme

@AndroidEntryPoint // Apenas necessário se estiver usando Hilt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val apiService = RetrofitInstance.apiService // Instancie seu Retrofit aqui
            val weatherRepository = WeatherRepository(apiService)

            AppNavigation(weatherRepository = weatherRepository)
        }
    }
}

@Composable
fun AppNavigation(weatherRepository: WeatherRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                navController,
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("login") { // Ir para login ao concluir registo
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() } // Voltar para login
            )
        }
        composable("home") {
            HomeScreenWrapper(
                navController = navController,
                weatherRepository = weatherRepository // Passando o WeatherRepository
            )
        }
    }
}

