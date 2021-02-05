package com.some.room.dao

import androidx.room.*
import com.some.room.entity.Car

/**
 * @author xiangxing
 */
@Dao
interface CarDao {
    @Query("SELECT * FROM Car")
    fun getAll(): List<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(car: Car)

    @Delete
    fun delete(car: Car)

//    @Query()
//    fun update()
}