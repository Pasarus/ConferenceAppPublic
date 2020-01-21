package uk.ac.aber.dcs.cs31620.conferenceapp.ui.favourites;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.database.entities.SessionsEntity;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;
import uk.ac.aber.dcs.cs31620.conferenceapp.util.Observer;

/**
 * Very similar to the EventsFragment, however this fragment is dependant on observing the
 * favourites list inside of the data service. If that list updates this fragment should update to.
 */
public class FavouritesFragment extends Fragment implements Observer {
    private FavouritesEventCardRecylerAdapter m_recyclerAdapter;
    private RecyclerView m_eventRecyclerView;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        m_eventRecyclerView = view.findViewById(R.id.faveRecyclerView);
        m_eventRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        m_eventRecyclerView.setLayoutManager(linearLayoutManager);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        m_recyclerAdapter = new FavouritesEventCardRecylerAdapter(this);
        m_eventRecyclerView.setAdapter(m_recyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        DataService ds = DataService.getDataService();
        causeUpdateToFaveSessions(ds);
        setChangeObserver();
    }

    private void setChangeObserver() {
        DataService ds = DataService.getDataService();
        causeUpdateToFaveSessions(ds);

        ds.observeFavourites(this);
    }

    private void causeUpdateToFaveSessions(DataService ds) {
        m_recyclerAdapter.changeDataSet(ds.getFaveSessions());
    }

    private void updateOccurs() {
        DataService ds = DataService.getDataService();
        causeUpdateToFaveSessions(ds);
    }

    @Override
    public void observedAddUpdate(String sessionId) {
        updateOccurs();
    }

    @Override
    public void observedRemoveUpdate(String sessionId) {
        updateOccurs();
    }
}
