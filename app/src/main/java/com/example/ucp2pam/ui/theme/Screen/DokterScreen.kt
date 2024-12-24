package com.example.ucp2pam.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ucp2pam.ViewModel.MainViewModel
import com.example.ucp2pam.data.Dokter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DokterScreen(viewModel: MainViewModel, navController: NavController) {
    val dokterList by viewModel.dokterList.observeAsState(emptyList())
    var nama by remember { mutableStateOf("") }
    var spesialis by remember { mutableStateOf("") }
    var klinik by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var jamKerja by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var doctorAdded by remember { mutableStateOf(false) }

    fun addDokter() {
