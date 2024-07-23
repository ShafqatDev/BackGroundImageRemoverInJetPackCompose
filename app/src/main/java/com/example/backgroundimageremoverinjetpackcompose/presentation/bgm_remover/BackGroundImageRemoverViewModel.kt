package com.example.backgroundimageremoverinjetpackcompose.presentation.bgm_remover

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.backgroundimageremoverinjetpackcompose.data.utils.OnBackgroundChangeListener
import com.example.backgroundimageremoverinjetpackcompose.data.utils.PhotoSaver
import com.example.backgroundimageremoverinjetpackcompose.domain.repository.BgRemover
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

data class BackGroundImageRemoverState(
    val currentImage: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val isLoading: Boolean = false,
    val isBgRemoved: Boolean = false
)

class BackGroundImageRemoverViewModel(
    private val bgRemover: BgRemover,
    private val photoSaver: PhotoSaver
) : ViewModel() {
    private var _state = MutableStateFlow(BackGroundImageRemoverState())
    val state = _state.asStateFlow()

    fun imageChosen(bmp: Bitmap) {
        _state.update {
            it.copy(
                currentImage = bmp,
            )
        }
    }

    fun removeBg(context: Context) {
        _state.update { it.copy(isLoading = true) }
        bgRemover.bitmapForProcessing(
            state.value.currentImage,
            false,
            object : OnBackgroundChangeListener {
                override fun onSuccess(bitmap: Bitmap) {
                    _state.update {
                        it.copy(
                            currentImage = bitmap,
                            isLoading = false,
                            isBgRemoved = true
                        )
                    }
                }

                override fun onFailed(exception: Exception) {
                    _state.update { it.copy(isLoading = false) }
                    Toast.makeText(context, "Error Occur", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    fun downloadImage(context: Context, bitmap: Bitmap) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = photoSaver.savePhotoToExternalStorage(
                context.contentResolver,
                UUID.randomUUID().toString() + ".png",
                bitmap
            )
            withContext(Dispatchers.Main) {
                if (success) {
                    Toast.makeText(context, "Image saved successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to save image!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun resetState() {
        _state.update {
            BackGroundImageRemoverState()
        }
    }
}
