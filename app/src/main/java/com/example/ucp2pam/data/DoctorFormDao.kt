package com.example.ucp2pam.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Update
    suspend fun updateDokter(dokter: Dokter)

    @Delete
    suspend fun deleteDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter")
    fun getAllDokter(): Flow<List<Dokter>>

    // New method to get doctor count
    @Query("SELECT COUNT(*) FROM dokter")
    fun getDoctorCount(): Flow<Int>  // Returns the number of doctors
}


@Dao
interface JadwalDao {
    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)

    @Update
    suspend fun updateJadwal(jadwal: Jadwal)

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal")
    fun getAllJadwal(): Flow<List<Jadwal>>

    // New method to get patient count
    @Query("SELECT COUNT(*) FROM jadwal")
    fun getPatientCount(): Flow<Int>  // Returns the number of patients
}
