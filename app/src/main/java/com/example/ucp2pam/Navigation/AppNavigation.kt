package com.example.ucp2pam.Navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucp2pam.ViewModel.MainViewModel
import com.example.ucp2pam.data.Dokter
import com.example.ucp2pam.data.Jadwal
import com.example.ucp2pam.ui.theme.Screen.DokterDetailScreen
import com.example.ucp2pam.ui.theme.Screen.DokterScreen
import com.example.ucp2pam.ui.theme.Screen.EditJadwalScreen
import com.example.ucp2pam.ui.theme.Screen.HomeScreen
import com.example.ucp2pam.ui.theme.Screen.JadwalDetailScreen
import com.example.ucp2pam.ui.theme.Screen.JadwalScreen

@Composable
fun AppNavigation(dokterList: List<Dokter>,jadwalList : List<Jadwal>, viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            // HomeScreen menerima navController dan dokterList
            HomeScreen(navController = navController)

        }
        composable("dokter") {
            // DokterScreen menerima navController dan viewModel
            DokterScreen(navController = navController, viewModel = viewModel)
        }
        composable("jadwal") {
            // JadwalScreen menerima navController dan viewModel
            JadwalScreen(navController = navController, viewModel = viewModel)
        }
        composable("Detail Dokter"){
            // Pastikan untuk menggunakan nama parameter yang benar: navController
            DokterDetailScreen(navController = navController, dokterList, viewModel = viewModel)
        }
        composable("Detail Jadwal") {
            // JadwalScreen menerima navController dan viewModel
            JadwalDetailScreen(navController = navController, jadwalList, viewModel = viewModel)
        }
        composable("editJadwal/{jadwalId}") { backStackEntry ->
            val jadwalId = backStackEntry.arguments?.getString("jadwalId")?.toInt()
            val jadwal = jadwalList.find { it.id == jadwalId }
            EditJadwalScreen(navController = navController, jadwal = jadwal, viewModel = viewModel)
        }
    }

}
