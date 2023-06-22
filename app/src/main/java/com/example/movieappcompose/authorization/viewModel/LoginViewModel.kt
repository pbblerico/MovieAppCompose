package com.example.movieappcompose.authorization.viewModel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.authorization.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import com.example.movieappcompose.utils.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {
    private val _loginStatus = MutableLiveData<Result<AuthResult>>()
    val loginStatus: LiveData<Result<AuthResult>> = _loginStatus

    fun login(email: String, password: String) {
        val error =
            if(email.isEmpty() || password.isEmpty()) "Blank fields"
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Not a valid Email"
            else null

        error?.let {
            _loginStatus.postValue(Result.Failure(it))
            return
        }
        _loginStatus.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.Main){
            val loginResult = authRepository.login(email, password)
            _loginStatus.postValue(loginResult)
        }
    }
}