package uk.ac.aber.dcs.cs31620.conferenceapp.ui.speakers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.Speaker;

/**
 * The Adapter that allows the RecycleView of the SpeakersTab for the MainActivity to work.
 */
public class SpeakersSpeakerCardRecyclerAdapter extends RecyclerView.Adapter<SpeakerCard> {
    private List<Speaker> m_dataSet = new ArrayList<>();

    @NonNull
    @Override
    public SpeakerCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.speaker_card, parent, false);
        return new SpeakerCard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeakerCard holder, int position) {
        holder.bindDataSet(m_dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return m_dataSet.size();
    }

    /**
     * This will replace the data set used in the adapter
     *
     * @param dataSet A List of Speaker objects for display in the list
     */
    public void changeDataSet(List<Speaker> dataSet) {
        this.m_dataSet = dataSet;
        notifyDataSetChanged();
    }
}
