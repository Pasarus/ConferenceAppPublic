package uk.ac.aber.dcs.cs31620.conferenceapp.ui.events;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SessionsEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;
import uk.ac.aber.dcs.cs31620.conferenceapp.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment implements Observer {
    private EventsEventCardRecyclerAdapter m_recyclerAdapter;
    private RecyclerView m_eventRecyclerView;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        m_eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        m_eventRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        m_eventRecyclerView.setLayoutManager(linearLayoutManager);

        setupRecyclerAdapter();

        return view;
    }

    private void setupRecyclerAdapter() {
        m_recyclerAdapter = new EventsEventCardRecyclerAdapter(this);
        m_eventRecyclerView.setAdapter(m_recyclerAdapter);
        setChangeObserver();
    }

    /**
     *
     */
    private void setChangeObserver() {
        DataService ds = DataService.getDataService();
        LiveData<List<SessionsEntity>> allSessions = ds.getAllSessions();

        allSessions.observe(this, sessionEntities -> m_recyclerAdapter
                .changeDataSet(ds.convertSessionsToEvents(sessionEntities)));

        ds.observeFavourites(this);
    }

    @Override
    public void observedAddUpdate(String sessionId) {
        m_recyclerAdapter.setEventStatusForSessionId(sessionId, true);
        m_recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void observedRemoveUpdate(String sessionId) {
        m_recyclerAdapter.setEventStatusForSessionId(sessionId, false);
        m_recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        DataService ds = DataService.getDataService();
        setChangeObserver();
    }
}
