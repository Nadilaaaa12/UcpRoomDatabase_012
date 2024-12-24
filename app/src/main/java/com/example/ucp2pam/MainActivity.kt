package com.example.ucp2pam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import com.example.ucp2pam.Navigation.AppNavigation
import com.example.ucp2pam.ViewModel.MainViewModel
import com.example.ucp2pam.ui.theme.UCP2PAMTheme

class MainActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UCP2PAMTheme(darkTheme = false) {

                val dokterList = viewModel.dokterList.observeAsState(emptyList()).value
                val jadwalList = viewModel.jadwalList.observeAsState(emptyList()).value
                AppNavigation(dokterList = dokterList, jadwalList = jadwalList, viewModel = viewModel)
            }
        }
    }
}