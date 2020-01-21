package uk.ac.aber.dcs.cs31620.conferenceapp.ui.events;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.SupportMapFragment;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;
import uk.ac.aber.dcs.cs31620.conferenceapp.ui.map.GoogleMapContext;
import uk.ac.aber.dcs.cs31620.conferenceapp.ui.speakers.SpeakerActivity;

/**
 * This is the Dialog Fragment that pops up after a card has been clicked, it shows detailed
 * information about each event that it is given.
 */
public class EventDetailDialogFragment extends DialogFragment {
    private GoogleMapContext m_gmapContext;
    private SupportMapFragment m_mapFragment;
    private double m_longitude = 1;
    private double m_latitude = 1;
    private String m_description;
    private String m_speakerName;
    private String m_dateAndTime;
    private String m_locationName;
    private String m_speakerId;

    public EventDetailDialogFragment() {
        // Required empty public constructor
    }

    public EventDetailDialogFragment(double longitude, double latitude, String description,
                                     String speakerName, String dateAndTime, String locationName,
                                     String speakerId) {
        m_longitude = longitude;
        m_latitude = latitude;
        m_description = description;
        m_speakerName = speakerName;
        m_dateAndTime = dateAndTime;
        m_locationName = locationName;
        m_speakerId = speakerId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        m_mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.eventDetailsMap);
        m_gmapContext = new GoogleMapContext(m_longitude, m_latitude);
        m_mapFragment.getMapAsync(m_gmapContext);

        if (m_description != null) {
            final TextView eventDescription = view.findViewById(R.id.eventDetailDetails);
            eventDescription.setText(m_description);
        }

        if (m_speakerName != null) {
            final TextView speakerName = view.findViewById(R.id.eventDetailSpeakerGridSpeakerName);
            speakerName.setText(getString(R.string.eventDetailSpeaker) + " " + m_speakerName);

            speakerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSpeakerDetails();
                }
            });

        } else {
            // If is break or no speaker, remove speaker part of the view.
            view.findViewById(R.id.eventDetailSpeakerGridSpeakerName).setVisibility(View.GONE);
            view.findViewById(R.id.hideableLineForSpeakers).setVisibility(View.GONE);
        }



        if (m_dateAndTime != null) {
            final TextView dateTime = view.findViewById(R.id.eventDetailTime);
            dateTime.setText(getString(R.string.eventDetailTime) + " " + m_dateAndTime);
        }

        if (m_locationName != null) {
            final TextView location = view.findViewById(R.id.eventDetailLocation);
            location.setText(getString(R.string.eventDetailLocation) + " " + m_locationName);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (m_mapFragment != null) {
            try {
                getFragmentManager().beginTransaction().remove(m_mapFragment).commit();
            } catch (IllegalStateException e) {
                // Catch this and ignore it because during testing and some circumstances the
                // fragment is already gone.
            }
        }
    }

    private void showSpeakerDetails() {
        DataService ds = DataService.getDataService();
        Intent intent = new Intent(this.getContext().getApplicationContext(), SpeakerActivity.class);
        intent.putExtra("speakerDetails", ds.getSpeaker(m_speakerId));
        startActivity(intent);
    }
}
