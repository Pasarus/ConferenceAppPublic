package uk.ac.aber.dcs.cs31620.conferenceapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "speakers")
public class SpeakersEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "biography")
    private String biography;

    @ColumnInfo(name = "twitter")
    private String twitter;

    public SpeakersEntity() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
