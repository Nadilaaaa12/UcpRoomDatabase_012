package com.example.ucp2pam.ui.theme.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ucp2pam.ViewModel.MainViewModel
import com.example.ucp2pam.data.Dokter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DokterDetailScreen(
    navController: NavController,
    dokterList: List<Dokter>,
    viewModel: MainViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var dokterToDelete by remember { mutableStateOf<Dokter?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
    ) {
        TopAppBar(
            title = { Text("Daftar Dokter", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(Icons.Filled.Home, contentDescription = "Home")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            items(dokterList) { dokter ->
                DokterCard(
                    dokter = dokter,
                    onDelete = {
                        dokterToDelete = dokter
                        showDialog = true
                    }
                )
            }
        }
    }

    if (showDialog && dokterToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus", fontWeight = FontWeight.Bold) },
            text = { Text("Apakah Anda yakin ingin menghapus dokter ${dokterToDelete!!.nama}?") },
            confirmButton = {
                Button(
                    onClick = {
                        dokterToDelete?.let {
                            viewModel.deleteDokter(it)
                            showDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Hapus", color = Color.White)
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun DokterCard(dokter: Dokter, onDelete: () -> Unit) {
    val spesialisColor = when (dokter.spesialis.lowercase()) {
        "gigi" -> Color(0xFFD32F2F)
        "anak" -> Color(0xFF1976D2)
        "obgyn" -> Color(0xFF388E3C)
        "umum" -> Color(0xFFFBC02D)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = dokter.nama,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text("Spesialis: ${dokter.spesialis}", color = spesialisColor, style = MaterialTheme.typography.bodySmall)
            Text("Klinik: ${dokter.klinik}", style = MaterialTheme.typography.bodySmall)
            Text("No HP: ${dokter.noHp}", style = MaterialTheme.typography.bodySmall)
            Text("Jam Kerja: ${dokter.jamKerja}", style = MaterialTheme.typography.bodySmall)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDelete() }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
