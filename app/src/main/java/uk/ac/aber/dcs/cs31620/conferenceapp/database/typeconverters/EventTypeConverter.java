package uk.ac.aber.dcs.cs31620.conferenceapp.database.typeconverters;

import androidx.room.TypeConverter;

import uk.ac.aber.dcs.cs31620.conferenceapp.model.event.EventType;

public class EventTypeConverter {
    private static final String TALK_STRING = "talk";
    private static final String WORKSHOP_STRING = "workshop";
    private static final String REGISTRATION_STRING = "registration";
    private static final String COFFEE_STRING = "coffee";
    private static final String DINNER_STRING = "dinner";
    private static final String LUNCH_STRING = "lunch";

    @TypeConverter
    public EventType toEventType(String string) {
        switch (string) {
            case TALK_STRING:
                return EventType.TALK;
            case WORKSHOP_STRING:
                return EventType.WORKSHOP;
            case REGISTRATION_STRING:
                return EventType.REGISTRATION;
            case COFFEE_STRING:
                return EventType.COFFEE;
            case DINNER_STRING:
                return EventType.DINNER;
            case LUNCH_STRING:
                return EventType.LUNCH;
            default:
                return EventType.TALK;
        }
    }

    @TypeConverter
    public String toString(EventType eventType) {
        switch (eventType) {
            case TALK:
                return TALK_STRING;
            case WORKSHOP:
                return WORKSHOP_STRING;
            case REGISTRATION:
                return REGISTRATION_STRING;
            case COFFEE:
                return COFFEE_STRING;
            case DINNER:
                return DINNER_STRING;
            case LUNCH:
                return LUNCH_STRING;
            default:
                return TALK_STRING;
        }
    }
}
