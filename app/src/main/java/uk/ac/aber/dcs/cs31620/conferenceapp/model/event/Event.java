package uk.ac.aber.dcs.cs31620.conferenceapp.model.event;

import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;

public class Event {
    private String m_title;
    private String m_speaker;
    private String m_imageId;
    private String m_location;
    private String m_startAndEndTime;
    private String m_startDate;
    private String m_sessionId;
    private boolean m_favourite;
    private EventType m_eventType;
    private float m_longitude;
    private float m_latitude;
    private String m_description;

    public Event(String title, String speaker, String imageId, String location,
                 String startTime, String endTime, String startDate, String sessionId,
                 EventType eventType, boolean favourite, float longitude, float latitude,
                 String description) {
        m_title = title;
        m_speaker = speaker;
        m_imageId = imageId;
        m_location = location;
        m_startAndEndTime = formulateStartEndTime(startTime, endTime);
        m_startDate = startDate;
        m_sessionId = sessionId;
        m_eventType = eventType;
        m_favourite = favourite;
        m_longitude = longitude;
        m_latitude = latitude;
        m_description = description;
    }

    private String formulateStartEndTime(String start, String end) {
        return start + " - " + end;
    }

    public boolean isBreak() {
        return m_eventType == EventType.COFFEE ||
                m_eventType == EventType.DINNER ||
                m_eventType == EventType.LUNCH;
    }

    public String getImageId() {
        return m_imageId;
    }

    public String getTitle() {
        return m_title;
    }

    public String getTime() {
        return m_startAndEndTime;
    }

    public String getLocation() {
        return m_location;
    }

    public String getDate() {
        return m_startDate;
    }

    public String getSpeakerName() {
        return m_speaker;
    }

    public boolean isFavourited() {
        return m_favourite;
    }

    public EventType getEventType() {
        return m_eventType;
    }

    public void flipFavourite() {
        m_favourite = !m_favourite;
        ensurePersistanceUpdated();
    }

    public void flipFavouriteNoPersistance() {
        m_favourite = !m_favourite;
    }

    private void ensurePersistanceUpdated() {
        DataService.getDataService().setSessionFavouriteStatus(m_sessionId, m_favourite);
    }

    public String getSessionId() {
        return m_sessionId;
    }

    public float getLongitude() {
        return m_longitude;
    }

    public float getLatitude() {
        return m_latitude;
    }

    public String getDescription() {
        return m_description;
    }
}
