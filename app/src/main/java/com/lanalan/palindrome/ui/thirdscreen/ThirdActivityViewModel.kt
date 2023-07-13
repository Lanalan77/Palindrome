package com.lanalan.palindrome.ui.thirdscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanalan.palindrome.api.ApiConfig
import com.lanalan.palindrome.data.User
import kotlinx.coroutines.launch

class ThirdActivityViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()
    private val _users: MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>> get() = _users

    fun fetchUsers(page: Int, perPage: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getUsers(page, perPage)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        _users.postValue(it.data?.mapNotNull { userData ->
                            User(userData?.firstName.orEmpty(), userData?.email.orEmpty(), userData?.avatar.orEmpty())
                        })
                    }
                } else {
                    // Handle error response
                }
            } catch (e: Exception) {
                // Handle network or API call exception
            }
        }
    }

}

