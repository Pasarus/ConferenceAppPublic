package uk.ac.aber.dcs.cs31620.conferenceapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.dcs.cs31620.conferenceapp.database.daos.LocationsDao;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.daos.SessionsDao;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.daos.SpeakersDao;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SessionsEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SpeakersEntity;

public class ConferenceRepository {
    private LocationsDao m_locationsDao;
    private SessionsDao m_sessionsDao;
    private SpeakersDao m_speakersDao;

    public ConferenceRepository(Application application) {
        ConferenceDatabase db = ConferenceDatabase.getDatabase(application);
        m_locationsDao = db.getLocationsDao();
        m_sessionsDao = db.getSessionsDao();
        m_speakersDao = db.getSpeakersDao();
    }


    // Sessions data retrieval
    public LiveData<List<SessionsEntity>> getAllSessions() {
        return m_sessionsDao.getAllSessions();
    }

    public String getLocationNameFromLocationdId(String locationId) {
        return m_locationsDao.getLocationNameFromLocationId(locationId);
    }

    // Speakers data retrieval
    public List<SpeakersEntity> getAllSpeakers() {
        return m_speakersDao.getAllSpeakers();
    }

    public String getSpeakerNameFromSpeakerId(String speakerId) {
        return m_speakersDao.getSpeakerNameFromId(speakerId);
    }

    public List<SessionsEntity> getSessionsBySessionIds(List<String> faveSessionIds) {
        return m_sessionsDao.fetchSessionsBySessionIds(faveSessionIds);
    }

    public float getLongitudeOfLocationId(String locationId) {
        return m_locationsDao.getLongitudeOfLocationId(locationId);
    }

    public float getLatitudeOfLocationId(String locationId) {
        return m_locationsDao.getLatitudeOfLocationId(locationId);
    }

    public String getSpeakerDetailsFromSpeakerId(String speakerId) {
        return m_speakersDao.getSpeakerDetailsFromSpeakerId(speakerId);
    }

    public String getSpeakerTwitterHandleFromSpeakerId(String speakerId) {
        return m_speakersDao.getSpeakerTwitterHandleFromSpeakerId(speakerId);
    }

    public String getSpeakerIdFromSessionId(String sessionId) {
        return m_sessionsDao.getSpeakerIdFromSessionId(sessionId);
    }
}
