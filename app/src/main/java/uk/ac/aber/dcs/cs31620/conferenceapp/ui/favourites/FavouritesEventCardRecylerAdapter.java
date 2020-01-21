package uk.ac.aber.dcs.cs31620.conferenceapp.ui.favourites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.event.Event;
import uk.ac.aber.dcs.cs31620.conferenceapp.ui.events.EventCard;

/**
 * The Adapter that allows the RecycleView of the FavouritesTab for the MainActivity to work.
 */
class FavouritesEventCardRecylerAdapter extends RecyclerView.Adapter<EventCard> {
    private final Fragment m_parentFragment;
    private List<Event> m_dataSet = new ArrayList<>();

    public FavouritesEventCardRecylerAdapter(Fragment parent) {
        m_parentFragment = parent;
    }

    @Override
    public int getItemCount() {
        return m_dataSet.size();
    }

    @NonNull
    @Override
    public EventCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.event_card, parent, false);
        return new EventCard(view, m_parentFragment.getFragmentManager());
    }

    @Override
    public void onBindViewHolder(@NonNull EventCard holder, int position) {
        holder.bindDataSet(m_dataSet.get(position));
    }

    /**
     * This will replace the data set used in the adapter
     *
     * @param dataSet A List of Speaker objects for display in the list
     */
    public void changeDataSet(List<Event> dataSet) {
        this.m_dataSet = dataSet;
        notifyDataSetChanged();
    }
}
