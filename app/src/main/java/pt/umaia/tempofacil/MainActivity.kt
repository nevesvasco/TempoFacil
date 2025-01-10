package pt.umaia.tempofacil

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import pt.umaia.tempofacil.data.WeatherRepository
import pt.umaia.tempofacil.login.LoginScreen
import pt.umaia.tempofacil.login.RegisterScreen
import pt.umaia.tempofacil.ui.home.AirPollutionScreen
import pt.umaia.tempofacil.ui.home.AirPollutionScreenWrapper
import pt.umaia.tempofacil.ui.home.FiveDaysScreenWrapper
import pt.umaia.tempofacil.ui.home.HomeScreenWrapper

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        if (FirebaseApp.getApps(this).isEmpty()) {
            Log.e("MainActivity", "FirebaseApp não foi inicializado corretamente.")
        }
        enableEdgeToEdge()
        setContent {
            val apiService = RetrofitInstance.apiService
            val weatherRepository = WeatherRepository(apiService)

            AppNavigation(weatherRepository = weatherRepository)
        }
    }
}

@Composable
fun AppNavigation(weatherRepository: WeatherRepository) {
    val navController:NavHostController = rememberNavController()

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
                weatherRepository = weatherRepository
            )
        }
        composable("fivedays") {
            FiveDaysScreenWrapper(
                navController = navController,
                weatherRepository = weatherRepository
            )
        }
        composable("airpollution") {
            AirPollutionScreenWrapper(
                navController = navController,
                weatherRepository = weatherRepository
            )
        }
    }
}