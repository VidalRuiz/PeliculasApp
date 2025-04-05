package com.vidalruiz.peliculasapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vidalruiz.peliculasapp.data.model.FavoriteMovieEntity

/**
 * Author: Vidal Ruiz
 * Created on: April 4, 2025
 * Description: Room database for the application, holds favorite movies.
 */

@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "peliculas_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}