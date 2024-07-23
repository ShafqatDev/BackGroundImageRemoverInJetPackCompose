package com.example.backgroundimageremoverinjetpackcompose.presentation.bgm_remover

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.backgroundimageremoverinjetpackcompose.utils.LoadingResponse
import com.example.backgroundimageremoverinjetpackcompose.utils.MyButton
import com.example.backgroundimageremoverinjetpackcompose.utils.getBitmapFromUri
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackGroundImageRemoverScreen(
    modifier: Modifier = Modifier,
    viewModel: BackGroundImageRemoverViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = getBitmapFromUri(context.contentResolver, it, 1024)
            viewModel.imageChosen(bitmap)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
                title = {
                Text(text = "Background Image Remover")
            }, navigationIcon = {
                if (state.isBgRemoved) {
                    IconButton(onClick = { viewModel.resetState() }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "")
                    }
                }
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            if (state.isBgRemoved.not()) {
                FloatingActionButton(onClick = { galleryLauncher.launch("image/*") }){
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        },
        bottomBar = {
            if (state.isBgRemoved) {
                MyButton(
                    onClick = { viewModel.downloadImage(context, state.currentImage) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = "Download Image"
                )
            } else {
                MyButton(
                    onClick = { viewModel.removeBg(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = "Remove Background Image"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    LoadingResponse()
                } else {
                    Image(
                        bitmap = state.currentImage.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

}
