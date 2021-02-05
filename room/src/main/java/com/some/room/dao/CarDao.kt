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

    @Query("SELECT * FROM Car where carId=:carId")
    fun getCar(carId: Int): Car?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(car: Car)

    @Delete
    fun delete(car: Car)

    @Query("update car set carName =:carName where carId = :carId")
    fun update(carName: String, carId: Int)
}