package com.example.movieappcompose.authorization.repostiroy

import com.example.movieappcompose.models.User
import com.example.movieappcompose.utils.Constants
import com.google.firebase.auth.AuthResult
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl:AuthRepository {
    override suspend fun signUp(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val result = Constants.auth.createUserWithEmailAndPassword(email, password).await()

                val uid = Constants.auth.uid!!
                val timestamp = System.currentTimeMillis()
                val user = User(name = name, surname = surname, uid = uid, email = email, timestamp = timestamp)

                Constants.ref.getReference("Users").child(uid).setValue(user).await()
                Result.Success(result)
            } catch (e: Exception) {
                Result.Failure(e.message ?: "Uknown error occured")
            }
        }
    }

    override suspend fun login(email: String, password: String): Result<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val result = Constants.auth.signInWithEmailAndPassword(email, password).await()
                Result.Success(result)
            }
            catch (e: java.lang.Exception) {
                Result.Failure(e.message ?: "Uknown error occured")
            }
        }
    }

    override fun logout() {
        Constants.auth.signOut()
    }
}