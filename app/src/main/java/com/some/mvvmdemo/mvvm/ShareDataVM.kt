package com.some.mvvmdemo.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ShareDataVM(application: Application) : AndroidViewModel(application) {

    val liveData = MutableLiveData<String>()

    fun request(){
        viewModelScope.launch{

        }
    }

}
