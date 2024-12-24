package com.example.ucp2pam.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.AppDatabase
import com.example.ucp2pam.data.Dokter
import com.example.ucp2pam.data.Jadwal
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val dokterDao = database.dokterDao()
    private val jadwalDao = database.jadwalDao()


    val dokterList: LiveData<List<Dokter>> = dokterDao.getAllDokter().asLiveData()
    val jadwalList: LiveData<List<Jadwal>> = jadwalDao.getAllJadwal().asLiveData()


    fun addDokter(dokter: Dokter) {
        viewModelScope.launch {
            dokterDao.insertDokter(dokter)
        }
    }
    fun deleteDokter(dokter: Dokter) {
        viewModelScope.launch {
            dokterDao.deleteDokter(dokter)
        }
    }

