package com.example.movieappcompose.authorization.repository

import com.example.movieappcompose.shared.data.models.User
import com.google.firebase.auth.AuthResult
import com.example.movieappcompose.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val ref: FirebaseDatabase
):AuthRepository {
    override suspend fun signUp(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()

                val uid = auth.uid!!
                val timestamp = System.currentTimeMillis()
                val user = User(name = name, surname = surname, uid = uid, email = email, timestamp = timestamp)

                ref.getReference("Users").child(uid).setValue(user).await()
                Result.Success(result)
            } catch (e: Exception) {
                Result.Failure(e.message ?: "Uknown error occured")
            }
        }
    }

    override suspend fun login(email: String, password: String): Result<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Result.Success(result)
            }
            catch (e: java.lang.Exception) {
                Result.Failure(e.message ?: "Uknown error occured")
            }
        }
    }

    override fun logout() {
        auth.signOut()
    }
}