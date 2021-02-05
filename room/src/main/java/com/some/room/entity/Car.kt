package com.some.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author xiangxing
 */
@Entity
data class Car(
        @PrimaryKey val carId: Int,
        @ColumnInfo val carName: String,
        @ColumnInfo val carPicUrl: String,
        @ColumnInfo val carAniUrl: String,
        @ColumnInfo val carTopViewUrl: String,
        @ColumnInfo val carBgColor: String

)
