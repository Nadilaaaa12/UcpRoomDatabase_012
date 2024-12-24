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
        TopAppBar(
            title = { Text("Edit Jadwal", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF1976D2), titleContentColor = Color.White)
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DropdownMenuComponent(
                options = dokterList.map { it.nama },
                selectedOption = namaDokter,
                onOptionSelected = { namaDokter = it },
            )

            OutlinedTextField(
                value = namaPasien,
                onValueChange = { namaPasien = it },
                label = { Text("Nama Pasien") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = noHp,
                onValueChange = { noHp = it },
                label = { Text("No HP") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = tanggalKonsultasi,
                onValueChange = { tanggalKonsultasi = it },
                label = { Text("Tanggal Konsultasi") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text("Status") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = {
                    if (namaDokter.isNotEmpty() && namaPasien.isNotEmpty() && noHp.isNotEmpty() && tanggalKonsultasi.isNotEmpty() && status.isNotEmpty()) {
                        showDialog = true
                    } else {
                        Toast.makeText(navController.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5))
            ) {
                Text("Save Changes", color = Color.White)
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Konfirmasi", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                text = { Text("Apakah Anda yakin ingin menyimpan perubahan jadwal?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.updateJadwal(
                                Jadwal(
                                    id = jadwal.id,
                                    namaDokter = namaDokter,
                                    namaPasien = namaPasien,
                                    noHp = noHp,
                                    tanggalKonsultasi = tanggalKonsultasi,
                                    status = status
                                )
                            )
                            navController.popBackStack()
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A))
                    ) {
                        Text("Yes", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350))) {
                        Text("No", color = Color.White)
                    }
                },
                containerColor = Color.White
            )
        }
    }
}
