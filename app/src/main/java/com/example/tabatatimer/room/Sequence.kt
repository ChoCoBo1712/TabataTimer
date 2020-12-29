package com.example.tabatatimer.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sequence")
class Sequence(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sequence_id")
    var id: Int = 0,

    var title: String,

    var preparation: Int,

    var workout: Int,

    var rest: Int,

    var cycles: Int
) {
    override fun toString(): String {
        return this.title
    }
}
