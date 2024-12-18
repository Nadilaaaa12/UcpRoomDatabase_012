package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Insert
    suspend fun insertJadwal(mahasiswa: Jadwal)

    //get All namaDokter
    @Query("SELECT * FROM jadwal ORDER BY namaDokter ASC")
    fun getAllJadwal(): Flow<List<Jadwal>>

    //get id
    @Query("SELECT * FROM jadwal WHERE id = :id")
    fun getJadwal(id: String): Flow<Jadwal>

    @Delete
    suspend fun deletejadwal(jadwal: Jadwal)

    @Update
    suspend fun updatejadwal(jadwal: Jadwal)
}