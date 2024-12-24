package com.example.ucp2pam

import android.app.Application
import androidx.room.Room
import com.example.ucp2pam.data.AppDatabase

class MyApplication : Application() {
    val db by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "doctor_form")
            .build()
    }
}
