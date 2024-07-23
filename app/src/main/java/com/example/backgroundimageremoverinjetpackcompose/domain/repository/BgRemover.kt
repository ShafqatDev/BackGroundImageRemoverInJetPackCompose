package com.example.backgroundimageremoverinjetpackcompose.domain.repository

import android.graphics.Bitmap
import com.example.backgroundimageremoverinjetpackcompose.data.utils.OnBackgroundChangeListener

interface BgRemover {
    fun bitmapForProcessing(bitmap: Bitmap, trimEmptyPart: Boolean? = false, listener: OnBackgroundChangeListener)
}