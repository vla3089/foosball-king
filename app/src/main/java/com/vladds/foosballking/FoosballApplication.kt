package com.vladds.foosballking

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.vladds.foosballking.data.FoosballKingDatabase
import com.vladds.foosballking.di.DaggerFoosballKingComponent
import com.vladds.foosballking.di.FoosballKingComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class FoosballApplication : Application() {

    lateinit var component: FoosballKingComponent

    override fun onCreate() {
        super.onCreate()
        val db = Room.inMemoryDatabaseBuilder(
            applicationContext,
            FoosballKingDatabase::class.java
        )
            .build()

        runBlocking(context = Dispatchers.IO) {
            val playerDao = db.playerDao()
            players.forEach { player ->
                playerDao.insertOrUpdate(player)
            }

            val gameRecordHistoryDao = db.gameRecordHistoryDao()
            demoSource.forEach {
                gameRecordHistoryDao.insertOrUpdate(it)
            }
        }

        component = DaggerFoosballKingComponent.builder()
            .foosballKingDatabase(db)
            .build()
    }
}

fun Context.foosballKingComponent(): FoosballKingComponent {
    return (this.applicationContext as FoosballApplication).component
}
