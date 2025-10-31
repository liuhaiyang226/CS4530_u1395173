package com.example.assignment4

import android.app.Application
import com.example.assignment4.data.AppDatabase
import com.example.assignment4.data.FactsRepository
import com.example.assignment4.network.UselessFactsApi

class FactsApp : Application() {
    // these are simple enough to keep here
    lateinit var repository: FactsRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val db = AppDatabase.get(this)
        repository = FactsRepository(
            dao = db.funFactDao(),
            api = UselessFactsApi()
        )
    }
}