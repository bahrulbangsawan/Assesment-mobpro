package org.d3if4118.asesmen.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val note: String,
    val date: String,
    val status: String
) : Parcelable