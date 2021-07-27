package com.example.kumparan.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import okio.buffer
import okio.source

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

class DrawableLoader(private val context: Context, private val imageLoader: ImageLoader) {

    suspend fun invoke(block: ImageRequest.Builder.() -> Unit): Drawable {
        val request = ImageRequest.Builder(context)
            .apply(block)
            .build()

        return when (val result = imageLoader.execute(request)) {
            is SuccessResult -> result.drawable
            is ErrorResult -> throw result.throwable
        }
    }
}

inline fun <reified T> responseFromJson(jsonFile: String): List<T> {
    val inputStream = T::class.java.classLoader.getResourceAsStream(jsonFile).source().buffer()
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(T::class.java)
    val response = mutableListOf<T>()
    JsonReader.of(inputStream).use { reader ->
        reader.beginArray()
        while (reader.hasNext()) {
            adapter.fromJson(reader)?.let {
                response.add(it)
            }
        }
    }
    return response
}