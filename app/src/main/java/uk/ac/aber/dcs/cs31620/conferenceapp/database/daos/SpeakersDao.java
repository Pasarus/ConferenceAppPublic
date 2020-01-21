package uk.ac.aber.dcs.cs31620.conferenceapp.database.daos;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SpeakersEntity;


@Dao
public interface SpeakersDao {
    @Query("SELECT * FROM speakers")
    List<SpeakersEntity> getAllSpeakers();

    @Query("SELECT name FROM speakers WHERE id = :speakerId")
    String getSpeakerNameFromId(String speakerId);

    @Query("SELECT twitter FROM speakers WHERE id = :speakerId")
    String getSpeakerTwitterHandleFromSpeakerId(String speakerId);

    @Query("SELECT biography FROM speakers WHERE id= :speakerId")
    String getSpeakerDetailsFromSpeakerId(String speakerId);
}
