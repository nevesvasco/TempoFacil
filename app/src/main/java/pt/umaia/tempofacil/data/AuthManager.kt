package pt.umaia.tempofacil.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

public class AuthenticationManager {
    private val auth = Firebase.auth

    fun criarContaComEmail(
        email: String,
        password: String
    ): Flow<AuthResponse> =
        callbackFlow {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResponse.Success)
                    } else {
                        trySend(AuthResponse.Error(message = task.exception?.message ?: ""))
                    }
                }
            awaitClose()
        }

    fun loginComEmail(email: String, password: String):Flow<AuthResponse> =
        callbackFlow{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResponse.Success)
                    } else {
                        trySend(AuthResponse.Error(message = task.exception?.message ?: ""))
                    }
                }
            awaitClose()
        }
    fun redefinirSenha(email: String): Flow<AuthResponse> =
        callbackFlow {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResponse.Success)
                    } else {
                        trySend(AuthResponse.Error(message = task.exception?.message ?: "Erro ao enviar o email de redefinição de senha."))
                    }
                }
            awaitClose()
        }


}
interface AuthResponse{
    data object Success : AuthResponse
    data class Error(val message: String): AuthResponse
}