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
        if (nama.isNotEmpty() && spesialis.isNotEmpty() && klinik.isNotEmpty() && noHp.isNotEmpty() && jamKerja.isNotEmpty()) {
            val dokter = Dokter(
                nama = nama,
                spesialis = spesialis,
                klinik = klinik,
                noHp = noHp,
                jamKerja = jamKerja
            )
            viewModel.addDokter(dokter)

            doctorAdded = true
            nama = ""
            spesialis = ""
            klinik = ""
            noHp = ""
            jamKerja = ""
        } else {
            Toast.makeText(navController.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    val spesialisOptions = listOf("Gigi", "Anak", "Obgyn", "Umum")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Tambah Dokter", style = MaterialTheme.typography.titleLarge, color = Color.White)
                },
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
                    .background(Color(0xFFF0F8FF)),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Form Tambah Dokter",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )

                Column {
                    Text("Spesialis", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    spesialisOptions.forEach { option ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = spesialis == option,
                                onClick = { spesialis = option },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2E7D32))
                            )
                            Text(text = option, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }

                OutlinedTextField(
                    value = klinik,
                    onValueChange = { klinik = it },
                    label = { Text("Klinik") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )

