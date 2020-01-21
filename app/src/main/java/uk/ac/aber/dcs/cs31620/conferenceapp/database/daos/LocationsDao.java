package uk.ac.aber.dcs.cs31620.conferenceapp.database.daos;

import androidx.room.Dao;
import androidx.room.Query;


@Dao
public interface LocationsDao {
    @Query("SELECT name FROM locations WHERE id = :locationId")
    String getLocationNameFromLocationId(String locationId);

    @Query("SELECT longitude FROM locations WHERE id=:locationId")
    float getLongitudeOfLocationId(String locationId);

    @Query("SELECT latitude FROM locations WHERE id=:locationId")
    float getLatitudeOfLocationId(String locationId);
}
