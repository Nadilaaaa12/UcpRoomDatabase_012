package com.example.ucp2pam.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ucp2pam.ViewModel.MainViewModel
import com.example.ucp2pam.data.Jadwal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditJadwalScreen(
    navController: NavController,
    jadwal: Jadwal?,
    viewModel: MainViewModel
) {
    if (jadwal == null) {
        return
    }

    val dokterList by viewModel.dokterList.observeAsState(emptyList())

    var namaDokter by remember { mutableStateOf(jadwal.namaDokter) }
    var namaPasien by remember { mutableStateOf(jadwal.namaPasien) }
    var noHp by remember { mutableStateOf(jadwal.noHp) }
    var tanggalKonsultasi by remember { mutableStateOf(jadwal.tanggalKonsultasi) }
    var status by remember { mutableStateOf(jadwal.status) }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
    ) {
