package com.example.backgroundimageremoverinjetpackcompose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.backgroundimageremoverinjetpackcompose.R

@Composable
fun MyButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}, text: String = "") {
    Button(
        onClick = onClick,
        modifier = modifier.clip(RoundedCornerShape(0.dp)), shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = colorResource(id = R.color.white)
        )
    ) {
        Text(
            text = text,
            color = colorResource(id = R.color.white),
        )
    }
}

@Composable
fun LoadingResponse(modifier: Modifier = Modifier) {
    Dialog(
        onDismissRequest = {
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = modifier
                .size(60.dp)
                .background(
                    color = colorResource(id = R.color.white),
                    RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
