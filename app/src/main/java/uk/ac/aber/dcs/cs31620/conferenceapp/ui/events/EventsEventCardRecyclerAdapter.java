package uk.ac.aber.dcs.cs31620.conferenceapp.ui.events;

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
/**
 * The Adapter that allows the RecycleView of the EventsTab for the MainActivity to work.
 */
class EventsEventCardRecyclerAdapter extends RecyclerView.Adapter<EventCard> {
    private final Fragment m_parentFragment;
    private List<Event> m_dataSet = new ArrayList<>();

    public EventsEventCardRecyclerAdapter(Fragment parent) {
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

    /**
     * This ensures that the correct status of favourite exists in the DataService via the event
     * object.
     *
     * @param sessionId The String related to the id in the SessionTable
     * @param favourite The status of favourite that you want
     */
    public void setEventStatusForSessionId(String sessionId, boolean favourite) {
        for (Event ii : m_dataSet) {
            if (ii.getSessionId().equals(sessionId)) {
                // Ensure a flip occurs.
                if (ii.isFavourited() && !favourite) {
                    ii.flipFavouriteNoPersistance();
                } else if (!ii.isFavourited() && favourite) {
                    ii.flipFavouriteNoPersistance();
                }
            }
        }
    }
}
