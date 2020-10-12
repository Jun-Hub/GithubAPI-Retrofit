package io.jun.thingsflow.model

import android.content.Context
import androidx.room.*
import io.jun.thingsflow.model.dto.GithubIssue

@Database(entities = [(GithubIssue::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    //Instance 싱글톤 사용
    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java,
                    "issue_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}