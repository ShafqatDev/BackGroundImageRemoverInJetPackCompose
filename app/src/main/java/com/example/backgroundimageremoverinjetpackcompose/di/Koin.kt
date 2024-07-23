package com.example.backgroundimageremoverinjetpackcompose.di

import com.example.backgroundimageremoverinjetpackcompose.data.repository.BgRemoverImp
import com.example.backgroundimageremoverinjetpackcompose.data.utils.PhotoSaver
import com.example.backgroundimageremoverinjetpackcompose.domain.repository.BgRemover
import com.example.backgroundimageremoverinjetpackcompose.presentation.bgm_remover.BackGroundImageRemoverViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleList = module {
    viewModel { BackGroundImageRemoverViewModel(get(),get()) }
    factory<BgRemover> {
        BgRemoverImp()
    }
    factory {
        PhotoSaver()
    }
}