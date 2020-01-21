package uk.ac.aber.dcs.cs31620.conferenceapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations")
public class LocationsEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "latitude")
    private double latitude;

    @NonNull
    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "description")
    private String description;

    public LocationsEntity() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @NonNull
    public double getLatitude() {
        return latitude;
    }

    @NonNull
    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(@NonNull double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(@NonNull double longitude) {
        this.longitude = longitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
