package uk.ac.aber.dcs.cs31620.conferenceapp.ui.speakers;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;

/**
 * This Fragment displays the list of speakers that are attending the conference using a recycler
 * view, and the data is gained from the DataService.
 */
public class SpeakersFragment extends Fragment {
    private RecyclerView m_speakerRecyclerView;
    private SpeakersSpeakerCardRecyclerAdapter m_recyclerAdapter;

    public SpeakersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_speakers, container, false);

        m_speakerRecyclerView = view.findViewById(R.id.speakerRecyclerView);
        m_speakerRecyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        m_speakerRecyclerView.setLayoutManager(layoutManager);

        setupRecyclerAdapter();

        return view;
    }

    private void setupRecyclerAdapter() {
        m_recyclerAdapter = new SpeakersSpeakerCardRecyclerAdapter();
        m_speakerRecyclerView.setAdapter(m_recyclerAdapter);
        m_recyclerAdapter.changeDataSet(DataService.getDataService().getAllSpeakers());
    }

}
