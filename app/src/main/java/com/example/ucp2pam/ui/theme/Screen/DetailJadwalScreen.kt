package com.example.ucp2pam.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE3F2FD))
    ) {
        TopAppBar(
            title = { Text("Daftar Jadwal") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(jadwalList) { jadwal ->
                JadwalCard(
                    jadwal = jadwal,
                    navController = navController,
                    onDeleteClick = { selectedJadwal ->
                        jadwalToDelete = selectedJadwal
                        showDialog = true
                    }
                )
            }
        }
    }

    if (showDialog && jadwalToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus jadwal ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        jadwalToDelete?.let { jadwal ->
                            viewModel.deleteJadwal(jadwal)
                            showDialog = false
                            jadwalToDelete = null
                            Toast.makeText(navController.context, "Jadwal berhasil dihapus", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        jadwalToDelete = null
                    }
                ) {
                    Text("Tidak")
                }
            }
        )
    }
}

@Composable
fun JadwalCard(jadwal: Jadwal, navController: NavController, onDeleteClick: (Jadwal) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Nama Pasien: ${jadwal.namaPasien}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text("Nama Dokter: ${jadwal.namaDokter}", style = MaterialTheme.typography.bodyMedium)
            Text("No HP: ${jadwal.noHp}", style = MaterialTheme.typography.bodySmall)
            Text("Tanggal Konsultasi: ${jadwal.tanggalKonsultasi}", style = MaterialTheme.typography.bodySmall)
            Text("Status: ${jadwal.status}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onDeleteClick(jadwal) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                ) {
                    Text("Hapus", color = Color.White)
                }

                Button(onClick = {
                    navController.navigate("editJadwal/${jadwal.id}")
                }) {
                    Text("Edit")
                }
            }
        }
    }
}
