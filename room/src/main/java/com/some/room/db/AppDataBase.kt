package com.some.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.some.room.dao.CarDao
import com.some.room.entity.Car
import com.some.room.entity.Road

/**
 * @author xiangxing
 * 要去执行数据库操作，比如插入一条数据才可以显示出来
 */
@Database(entities = [Car::class, Road::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getCarDao(): CarDao

    companion object {
        private var instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        AppDataBase::class.java,
                        "raceGame.db")
                        .allowMainThreadQueries()
                        .build()
            }
            return instance as AppDataBase
        }
    }
}