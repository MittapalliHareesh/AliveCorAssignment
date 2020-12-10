package com.example.alivecortask.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alivecortask.model.UserModel

class UserViewModel : ViewModel() {

    private val liveDataObj = MutableLiveData<UserModel>()

    fun setUserData(user: UserModel) {
        liveDataObj.value = user
    }

    fun getUserData(): MutableLiveData<UserModel> {
        return liveDataObj
    }
}