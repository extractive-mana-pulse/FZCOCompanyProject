package com.example.worktestcomposeproject.auth.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worktestcomposeproject.auth.data.remote.repository.AuthRepository
import com.example.worktestcomposeproject.auth.domain.model.AuthUser
import com.example.worktestcomposeproject.auth.domain.model.CheckAuthCodeRequest
import com.example.worktestcomposeproject.auth.domain.model.SendAuthCodeRequest
import com.example.worktestcomposeproject.auth.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _reg = MutableStateFlow<CodeResult>(CodeResult.Loading)
    val regResult = _reg.asStateFlow()

    private val _auth = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val authResult = _auth.asStateFlow()

    private val _checkCode = MutableStateFlow<CodeResult>(CodeResult.Loading)
    val codeResult = _checkCode.asStateFlow()

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId

    suspend fun auth(phone: String) {

        val request = SendAuthCodeRequest(phone)
        authRepository.sendAuthCode(request).enqueue(object : Callback<SendAuthCodeRequest> {
            override fun onResponse(call: Call<SendAuthCodeRequest>, response: Response<SendAuthCodeRequest>) {

                if (response.isSuccessful) {
                    val signUpResponse = response.body() ?: throw Exception("Response body is null")
                    _auth.value = signUpResponse.let { AuthResult.Success(it) }
                } else {
                    _auth.value = AuthResult.Error(response.code().toString())
                }
            }

            override fun onFailure(call: Call<SendAuthCodeRequest>, t: Throwable) {
                _auth.value = AuthResult.Error("Error occur: ${t.message.toString()}")
            }
        })
    }

    suspend fun codeCheck(phone: String, code: String) {

        val request = CheckAuthCodeRequest(phone, code)
        authRepository.checkAuthCode(request).enqueue(object : Callback<AuthUser> {
            override fun onResponse(call: Call<AuthUser>, response: Response<AuthUser>) {
                if (response.isSuccessful) {
                    val signUpResponse = response.body()

                    if (signUpResponse != null) {
                        val token = signUpResponse.token
                        val userId = signUpResponse.id

                        if (token != null && userId != null) {
                            _token.value = token!!
                            _userId.value = userId!!
                            _checkCode.value = CodeResult.Success(signUpResponse)
                        } else {
                            _checkCode.value = CodeResult.Error("Token or User ID is null")
                        }
                    } else {
                        _checkCode.value = CodeResult.Error("Response body is null")
                    }
                } else {
                    _checkCode.value = CodeResult.Error("Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AuthUser>, t: Throwable) {
                _checkCode.value = CodeResult.Error("Error occur: ${t.message.toString()}")
            }
        })
    }

    suspend fun registration(phone: String, name: String, username: String) {

        val request = User(phone, name, username)
        authRepository.register(request).enqueue(object : Callback<AuthUser> {
            override fun onResponse(call: Call<AuthUser>, response: Response<AuthUser>) {
                if (response.isSuccessful) {
                    val signUpResponse = response.body()

                    if (signUpResponse != null) {
                        val token = signUpResponse.token
                        val userId = signUpResponse.id

                        if (token != null && userId != null) {
                            _token.value = token!!
                            _userId.value = userId!!
                            _reg.value = CodeResult.Success(signUpResponse)
                        } else {
                            _reg.value = CodeResult.Error("Token or User ID is null")
                        }
                    } else {
                        _reg.value = CodeResult.Error("Response body is null")
                    }
                } else {
                    _reg.value = CodeResult.Error("Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AuthUser>, t: Throwable) {
                _reg.value = CodeResult.Error("Error occur: ${t.message.toString()}")
            }
        })
    }
}

sealed class AuthResult {
    data object Loading : AuthResult()
    data class Success(val data: SendAuthCodeRequest) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

sealed class CodeResult {
    data object Loading : CodeResult()
    data class Success(val data: AuthUser) : CodeResult()
    data class Error(val message: String) : CodeResult()
}