package com.example.backgroundimageremoverinjetpackcompose.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.example.backgroundimageremoverinjetpackcompose.data.utils.BackgroundRemover
import com.example.backgroundimageremoverinjetpackcompose.data.utils.BackgroundRemover.buffer
import com.example.backgroundimageremoverinjetpackcompose.data.utils.BackgroundRemover.height
import com.example.backgroundimageremoverinjetpackcompose.data.utils.BackgroundRemover.removeBackgroundFromImage
import com.example.backgroundimageremoverinjetpackcompose.data.utils.BackgroundRemover.segment
import com.example.backgroundimageremoverinjetpackcompose.data.utils.BackgroundRemover.trim
import com.example.backgroundimageremoverinjetpackcompose.data.utils.BackgroundRemover.width
import com.example.backgroundimageremoverinjetpackcompose.data.utils.OnBackgroundChangeListener
import com.example.backgroundimageremoverinjetpackcompose.domain.repository.BgRemover
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class BgRemoverImp : BgRemover {
    override  fun bitmapForProcessing(
        bitmap: Bitmap,
        trimEmptyPart: Boolean?,
        listener: OnBackgroundChangeListener
    ) {
        val copyBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val input = InputImage.fromBitmap(copyBitmap, 0)
        segment.process(input)
            .addOnSuccessListener { segmentationMask ->
                buffer = segmentationMask.buffer
                width = segmentationMask.width
                height = segmentationMask.height

                CoroutineScope(Dispatchers.IO).launch {
                    val time = measureTimeMillis {
                        val resultBitmap = if (trimEmptyPart == true) {
                            val bgRemovedBitmap = removeBackgroundFromImage(copyBitmap)
                            trim(bgRemovedBitmap)
                        } else {
                            removeBackgroundFromImage(copyBitmap)
                        }
                        withContext(Dispatchers.Main) {
                            listener.onSuccess(resultBitmap)
                        }
                    }
                    Log.e("TAG", "bitmapForProcessingTime: $time")
                }

            }
            .addOnFailureListener { e ->
                println("Image processing failed: $e")
                listener.onFailed(e)

            }
    }
}