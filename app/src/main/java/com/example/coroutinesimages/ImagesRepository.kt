package com.example.coroutinesimages

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class ImagesRepository {
    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }

    suspend fun downloadFromNetwork(url: String): Result<Bitmap> = withContext(Dispatchers.IO){
        try {
            var bmp: Bitmap?
            val inputStream = URL(url).openStream()
            bmp = BitmapFactory.decodeStream(inputStream)
            Result.Success(bmp)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

}