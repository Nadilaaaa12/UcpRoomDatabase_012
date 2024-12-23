package com.example.ucp2pam.ui.theme.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.example.ucp2pam.ViewModel.MainViewModel
import com.example.ucp2pam.data.Dokter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DokterDetailScreen(
    navController: NavController,
    dokterList: List<Dokter>,
    viewModel: MainViewModel // Assuming ViewModel for managing data
) {
    var showDialog by remember { mutableStateOf(false) }
    var dokterToDelete by remember { mutableStateOf<Dokter?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)  .background(Color(0xFFA6C641))) {


        TopAppBar(
            title = { Text("Daftar Dokter") },
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


        LazyColumn {
            items(dokterList) { dokter ->
                DokterCard(
                    dokter = dokter,
                    navController = navController,
                    onDelete = {
                        // Set the doctor to be deleted
                        dokterToDelete = dokter
                        showDialog = true
                    }
                )
            }
        }

        Spacer(Modifier.height(150.dp))


        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home"
            )
        }
    }


    if (showDialog && dokterToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus dokter ${dokterToDelete!!.nama}?") },
            confirmButton = {
                Button(
                    onClick = {
                        dokterToDelete?.let {
                            // Call the delete method from the ViewModel
                            viewModel.deleteDokter(it)
                            showDialog = false
                            navController.popBackStack() // Navigate back after deletion
                        }
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun DokterCard(dokter: Dokter, navController: NavController, onDelete: () -> Unit) {
    val spesialisColor = when (dokter.spesialis.lowercase()) {
        "Gigi" -> Color.Black
        "Anak" -> Color.Blue
        "Obgyn" -> Color.Magenta
        "Umum" -> Color.Red
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = dokter.nama,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
