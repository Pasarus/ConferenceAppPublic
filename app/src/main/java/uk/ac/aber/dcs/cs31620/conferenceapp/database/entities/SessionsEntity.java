package uk.ac.aber.dcs.cs31620.conferenceapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import uk.ac.aber.dcs.cs31620.conferenceapp.database.typeconverters.EventTypeConverter;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.event.EventType;

@Entity(tableName = "sessions")
@TypeConverters({EventTypeConverter.class})
public class SessionsEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "locationId")
    private String locationId;

    @ColumnInfo(name = "sessionDate")
    private String sessionDate;

    @NonNull
    @ColumnInfo(name = "sessionOrder")
    private int sessionOrder;

    @ColumnInfo(name = "timeStart")
    private String timeStart;

    @ColumnInfo(name = "timeEnd")
    private String timeEnd;

    @ColumnInfo(name = "sessionType")
    private EventType sessionType;

    @ColumnInfo(name = "speakerId")
    private String speakerId;

    public SessionsEntity() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public int getSessionOrder() {
        return sessionOrder;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public EventType getSessionType() {
        return sessionType;
    }

    public String getSpeakerId() {
        return speakerId;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public void setSessionOrder(int sessionOrder) {
        this.sessionOrder = sessionOrder;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setSessionType(EventType sessionType) {
        this.sessionType = sessionType;
    }

    public void setSpeakerId(String speakerId) {
        this.speakerId = speakerId;
    }
}
