package com.some.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author xiangxing
 */
@Entity
data class Road(
        @PrimaryKey val roadId: Int,
        @ColumnInfo val roadName: String,
        @ColumnInfo val roadPicUrl: String,
        @ColumnInfo val roadThumbnailUrl: String,
        @ColumnInfo val roadWinUrl: String,
        @ColumnInfo val roadLoseUrl: String
)
