package com.example.ucp2pam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dokter")
data class Dokter(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val spesialis: String,
    val klinik: String,
    val noHp: String,
    val jamKerja: String
)

@Entity(tableName = "jadwal")
data class Jadwal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val namaDokter: String,
    val namaPasien: String,
    val noHp: String,
    val tanggalKonsultasi: String,
    val status: String
)
