package com.example.coroutinesimages

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.URL

class ImagesViewModel() : ViewModel() {

    val repository = ImagesRepository()
    private val image: MutableLiveData<Bitmap> = MutableLiveData()
    val error: MutableLiveData<Boolean> =  MutableLiveData()
    val url = "https://images.unsplash.com/photo-1474224017046-182ece80b263?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max"

    val listaUrl: MutableList<String> = mutableListOf("https://images.unsplash.com/photo-1474224017046-182ece80b263?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max"
        , "https://images.unsplash.com/photo-1553531889-65d9c41c2609?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max"
        , "https://images.unsplash.com/photo-1451772741724-d20990422508?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max"
        , "https://images.unsplash.com/photo-1419242902214-272b3f66ee7a?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max")

    fun loadImage(): LiveData<Bitmap>{
        viewModelScope.launch {
            for (url in listaUrl ){
                val download = repository.downloadFromNetwork(url)

                when(download){
                    is ImagesRepository.Result.Success -> {
                        image.postValue(download.data)
                    }
                    else -> {
                        Log.d("Imagen", "No pasa na")
                        error.postValue(true)
                    }
                }
            }
        }
        return image
    }
}