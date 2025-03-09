package com.example.tp3;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Ajoutez ici les modifications nécessaires pour migrer de la version 1 à la version 2
            database.execSQL("ALTER TABLE users ADD COLUMN genre TEXT");
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "user_database")
                            .addMigrations(MIGRATION_1_2) // Ajoutez la migration ici
                            .fallbackToDestructiveMigration() // Optionnel : en développement
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}