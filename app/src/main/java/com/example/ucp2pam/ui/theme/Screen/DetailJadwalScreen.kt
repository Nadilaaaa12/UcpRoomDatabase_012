package com.example.ucp2pam.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ucp2pam.ViewModel.MainViewModel
import com.example.ucp2pam.data.Jadwal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JadwalDetailScreen(navController: NavController, jadwalList: List<Jadwal>, viewModel: MainViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    var jadwalToDelete by remember { mutableStateOf<Jadwal?>(null) }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // TopAppBar
        TopAppBar(
            title = { Text("Daftar Jadwal") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )


        LazyColumn {
            items(jadwalList) { jadwal ->
                // For each item, display a card
                JadwalCard(jadwal = jadwal, navController = navController, viewModel = viewModel(), onDeleteClick = { selectedJadwal ->
                    jadwalToDelete = selectedJadwal
                    showDialog = true
                })
            }
        }

