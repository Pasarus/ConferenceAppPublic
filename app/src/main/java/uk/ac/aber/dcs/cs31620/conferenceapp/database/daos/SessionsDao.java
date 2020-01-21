package uk.ac.aber.dcs.cs31620.conferenceapp.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.List;

import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SessionsEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.typeconverters.EventTypeConverter;

@Dao
@TypeConverters({EventTypeConverter.class})
public interface SessionsDao {
    @Query("SELECT * FROM sessions")
    LiveData<List<SessionsEntity>> getAllSessions();

    @Query("SELECT * FROM sessions WHERE id IN (:faveSessionIds)")
    List<SessionsEntity> fetchSessionsBySessionIds(List<String> faveSessionIds);

    @Query("SELECT speakerId FROM sessions WHERE id = :sessionId")
    String getSpeakerIdFromSessionId(String sessionId);
}
