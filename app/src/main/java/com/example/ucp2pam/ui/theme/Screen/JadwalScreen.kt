package com.example.ucp2pam.ui.theme.Screen

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ucp2pam.ViewModel.MainViewModel
import com.example.ucp2pam.data.Jadwal
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JadwalScreen(viewModel: MainViewModel, navController: NavController) {
    val dokterList by viewModel.dokterList.observeAsState(emptyList())
    val jadwalList by viewModel.jadwalList.observeAsState(emptyList())
    var namaDokter by remember { mutableStateOf("") }
    var namaPasien by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var tanggalKonsultasi by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    // State for AlertDialog
    var showDialog by remember { mutableStateOf(false) }

    // DatePickerDialog state
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        navController.context,
        { _, year, monthOfYear, dayOfMonth ->
            val month = monthOfYear + 1  // Month starts from 0
            tanggalKonsultasi = "$dayOfMonth/$month/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Jadwal", style = MaterialTheme.typography.titleLarge, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF4CAF50))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(Color(0xFFE3F2FD))
            ) {
                Text(
                    "Form Tambah Jadwal",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                DropdownMenuComponent(
                    options = dokterList.map { it.nama },
                    selectedOption = namaDokter,
                    onOptionSelected = { namaDokter = it }
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = namaPasien,
                    onValueChange = { namaPasien = it },
                    label = { Text("Nama Pasien") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = noHp,
                    onValueChange = { noHp = it },
                    label = { Text("No HP") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = MaterialTheme.shapes.medium
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = tanggalKonsultasi,
                    onValueChange = { },
                    label = { Text("Tanggal Konsultasi") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Select Date")
                        }
                    },
                    shape = MaterialTheme.shapes.medium
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = status,
                    onValueChange = { status = it },
                    label = { Text("Status") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (namaDokter.isNotEmpty() && namaPasien.isNotEmpty() && noHp.isNotEmpty() && tanggalKonsultasi.isNotEmpty() && status.isNotEmpty()) {
                            // Show the confirmation dialog before adding the schedule
                            showDialog = true
                        } else {
                            Toast.makeText(navController.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Tambah Jadwal", color = Color.White)
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    )

    // AlertDialog for confirmation
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi", color = Color(0xFF1E88E5)) },
            text = { Text("Apakah Anda yakin ingin menambahkan jadwal ini?", style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.addJadwal(
                            Jadwal(
                                namaDokter = namaDokter,
                                namaPasien = namaPasien,
                                noHp = noHp,
                                tanggalKonsultasi = tanggalKonsultasi,
                                status = status
                            )
                        )
                        // Clear fields after adding the schedule
                        namaDokter = ""
                        namaPasien = ""
                        noHp = ""
                        tanggalKonsultasi = ""
                        status = ""
                        showDialog = false
                    }
                ) {
                    Text("Ya", color = Color(0xFF4CAF50))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Tidak", color = Color(0xFFF44336))
                }
            },
            containerColor = Color(0xFFE3F2FD)
        )
    }
}

@Composable
fun DropdownMenuComponent(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            label = { Text("Pilih Dokter") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            shape = MaterialTheme.shapes.medium
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = Color.Black) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
