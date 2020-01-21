package uk.ac.aber.dcs.cs31620.conferenceapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import uk.ac.aber.dcs.cs31620.conferenceapp.database.daos.LocationsDao;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.daos.SessionsDao;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.daos.SpeakersDao;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.LocationsEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SessionsEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SpeakersEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.typeconverters.EventTypeConverter;


@Database(entities = {LocationsEntity.class,
        SessionsEntity.class,
        SpeakersEntity.class}, version = 1)
@TypeConverters({EventTypeConverter.class})
public abstract class ConferenceDatabase extends RoomDatabase {
    private static ConferenceDatabase INSTANCE;

    public static ConferenceDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (ConferenceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = createDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static ConferenceDatabase createDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), ConferenceDatabase.class,
                "conf.db")
                .createFromAsset("databases/conf.db")
                .allowMainThreadQueries()
                .build();
    }

    public abstract LocationsDao getLocationsDao();

    public abstract SessionsDao getSessionsDao();

    public abstract SpeakersDao getSpeakersDao();
}
