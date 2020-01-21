package uk.ac.aber.dcs.cs31620.conferenceapp.model;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import uk.ac.aber.dcs.cs31620.conferenceapp.database.ConferenceRepository;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SessionsEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SpeakersEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.event.Event;
import uk.ac.aber.dcs.cs31620.conferenceapp.util.Observer;
import uk.ac.aber.dcs.cs31620.conferenceapp.util.SimpleObservableStringSet;


// This is the universal data service. If any data is needed it should be accessible via this class.
// Use of this class should be prioritised over the sub helper classes that it uses.
public class DataService {
    // Utilizing Singleton as we only need to do setup once during runtime.
    private static DataService INSTANCE;
    private static final String FAVE_KEY = "faveouriteSessionIds";
    private ConferenceRepository m_conferenceDbRepository;
    private SimpleObservableStringSet m_faveEvents;
    private Activity m_parentActivity;

    public static void initializeDataService(Activity activity) {
        INSTANCE = new DataService(activity);
        INSTANCE.loadPreferences();
    }

    public void loadPreferences() {
        SharedPreferences preferences = m_parentActivity.getPreferences(Context.MODE_PRIVATE);
        m_faveEvents = new SimpleObservableStringSet(preferences.getStringSet(FAVE_KEY,
                new HashSet<>()));
    }

    public void savePreferences() {
        SharedPreferences preferences = m_parentActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(FAVE_KEY, m_faveEvents);
        editor.apply();
    }

    public static DataService getDataService() throws NullPointerException {
        if (INSTANCE == null) {
            throw new NullPointerException("Initialize Data Service before use.");
        }
        return INSTANCE;
    }

    private DataService(Activity activity) {
        // Ensure database has been made. Using a hacky method of calling something to ensure it
        // has started.
        m_conferenceDbRepository = new ConferenceRepository(activity.getApplication());
        m_conferenceDbRepository.getAllSessions();

        m_parentActivity = activity;
    }

    private boolean isSessionIdFavourited(String sessionId) {
        return m_faveEvents.contains(sessionId);
    }

    public List<Event> convertSessionsToEvents(List<SessionsEntity> sessionEntities) {
        List<Event> events = new ArrayList<>();
        for (SessionsEntity sessionEntity : sessionEntities) {
            events.add(generateEventFromSession(sessionEntity));
        }
        return events;
    }

    private Event generateEventFromSession(SessionsEntity session) {
        String speakerId = session.getSpeakerId();
        String locationId = session.getLocationId();
        String sessionId = session.getId();

        String speakerName =
                m_conferenceDbRepository.getSpeakerNameFromSpeakerId(speakerId);
        String locationName = m_conferenceDbRepository.getLocationNameFromLocationdId(locationId);
        float longitude = m_conferenceDbRepository.getLongitudeOfLocationId(locationId);
        float latitude = m_conferenceDbRepository.getLatitudeOfLocationId(locationId);

        return new Event(session.getTitle(), speakerName, speakerId, locationName,
                session.getTimeStart(), session.getTimeEnd(), session.getSessionDate(), sessionId,
                session.getSessionType(), isSessionIdFavourited(sessionId), longitude, latitude,
                session.getContent());
    }

    public LiveData<List<SessionsEntity>> getAllSessions() {
        return m_conferenceDbRepository.getAllSessions();
    }

    public void setSessionFavouriteStatus(String sessionId, boolean favourite) {
        if (m_faveEvents.contains(sessionId) && !favourite) {
            m_faveEvents.remove(sessionId);
        } else if (!m_faveEvents.contains(sessionId) && favourite) {
            m_faveEvents.add(sessionId);
        }
    }

    private List<String> getFaveSessionIds() {
        return new ArrayList<>(m_faveEvents);
    }

    public void observeFavourites(Observer o) {
        m_faveEvents.addObserver(o);
    }

    public List<SessionsEntity> getFaveSessionsEntities() {
        List<String> faveSessionIds = getFaveSessionIds();
        return m_conferenceDbRepository.getSessionsBySessionIds(faveSessionIds);
    }

    public Speaker getSpeaker(String speakerId) {
        String speakerName = m_conferenceDbRepository.getSpeakerNameFromSpeakerId(speakerId);
        String speakerDetails = m_conferenceDbRepository.getSpeakerDetailsFromSpeakerId(speakerId);
        String speakerTwitterHandle = m_conferenceDbRepository
                .getSpeakerTwitterHandleFromSpeakerId(speakerId);

        return new Speaker(speakerName, speakerId, speakerTwitterHandle, speakerDetails);
    }

    public String getSpeakerIdFromSessionId(String sessionId) {
        return m_conferenceDbRepository.getSpeakerIdFromSessionId(sessionId);
    }

    public List<Speaker> getAllSpeakers() {
        List<SpeakersEntity> dbSpeakers = m_conferenceDbRepository.getAllSpeakers();
        List<Speaker> speakers = new ArrayList<>();
        for (SpeakersEntity speakerEntity : dbSpeakers) {
            speakers.add(new Speaker(speakerEntity.getName(),
                    speakerEntity.getId(), speakerEntity.getTwitter(),
                    speakerEntity.getBiography()));
        }
        return speakers;
    }

    public List<Event> getFaveSessions() {
        List<String> faveSessionIds = getFaveSessionIds();
        return convertSessionsToEvents(m_conferenceDbRepository.getSessionsBySessionIds(faveSessionIds));
    }
}
