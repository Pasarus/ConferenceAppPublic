package uk.ac.aber.dcs.cs31620.conferenceapp.util;

public interface Observer {
    void observedAddUpdate(String sessionId);

    void observedRemoveUpdate(String sessionId);
}
