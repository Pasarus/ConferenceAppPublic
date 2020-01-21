package uk.ac.aber.dcs.cs31620.conferenceapp.ui.events;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.io.InputStream;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.event.Event;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.event.EventType;

/**
 * This is the material design card that is the main method for display of information, this is used
 * in the EventsTab and FavouritesTab via their respective RecyclerAdapters
 */
public class EventCard extends RecyclerView.ViewHolder {
    private ImageView m_cardImage;
    private TextView m_cardTitleText;
    private TextView m_cardSpeakerName;
    private TextView m_cardTime;
    private TextView m_cardLocation;
    private TextView m_cardDate;
    private ImageView m_cardFavouriteButton;
    private ImageView m_cardDetailsButton;
    private Event m_currentEvent;

    public EventCard(View itemView, FragmentManager fragmentManager) {
        super(itemView);

        m_cardImage = itemView.findViewById(R.id.cardImage);
        m_cardTitleText = itemView.findViewById(R.id.cardTitleText);
        m_cardSpeakerName = itemView.findViewById(R.id.cardSpeakerName);
        m_cardTime = itemView.findViewById(R.id.cardTime);
        m_cardLocation = itemView.findViewById(R.id.cardLocation);
        m_cardDate = itemView.findViewById(R.id.cardDate);
        m_cardFavouriteButton = itemView.findViewById(R.id.cardFavouriteButton);
        m_cardDetailsButton = itemView.findViewById(R.id.cardDetailsButton);

        m_cardFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (m_currentEvent != null) {
                    m_currentEvent.flipFavourite();
                    // Ensure card refresh
                    bindDataSet(m_currentEvent);
                }
            }
        });

        m_cardDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailedFragment(fragmentManager);
            }
        });

        MaterialCardView card = itemView.findViewById(R.id.cardView);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailedFragment(fragmentManager);
            }
        });
    }

    private void showDetailedFragment(FragmentManager fragmentManager) {
        EventDetailDialogFragment dialog = new EventDetailDialogFragment(
                m_currentEvent.getLongitude(), m_currentEvent.getLatitude(),
                m_currentEvent.getDescription(), m_currentEvent.getSpeakerName(),
                m_currentEvent.getDate() + ", " + m_currentEvent.getTime(),
                m_currentEvent.getLocation(), DataService.getDataService()
                .getSpeakerIdFromSessionId(m_currentEvent.getSessionId()));
        dialog.show(fragmentManager, "Details");
    }

    private void setNoneSpeakerEventData(Drawable newImage, Event event, boolean allowFave) {
        if (newImage != null) {
            m_cardImage.setImageDrawable(newImage);
        }
        m_cardTitleText.setText(event.getTitle());
        m_cardSpeakerName.setText("");
        if (!allowFave) {
            m_cardFavouriteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void bindDataSet(@NonNull Event event) {
        m_currentEvent = event;
        Context context = this.itemView.getContext();
        String imagePath = "images/speakers/" + event.getImageId();
        if (event.isBreak()) {
            setNoneSpeakerEventData(context.getDrawable(R.drawable.ic_free_breakfast_black_24dp),
                    event, false);
        } else if (event.getEventType() == EventType.REGISTRATION) {
            setNoneSpeakerEventData(context.getDrawable(R.drawable.ic_import_contacts_black_24dp),
                    event, false);
        } else {
            try {
                InputStream stream = itemView.getContext().getApplicationContext().getAssets()
                        .open(imagePath);
                Drawable speakerImage = Drawable.createFromStream(stream, null);
                m_cardImage.setImageDrawable(speakerImage);
            } catch (IOException e) {
                // No speaker picture found so replace depending on type.
                Drawable replacementImage;
                if (event.getEventType() == EventType.WORKSHOP) {
                    replacementImage = this.itemView.getResources()
                            .getDrawable(R.drawable.ic_build_black_24dp);
                } else {
                    replacementImage = this.itemView.getResources()
                            .getDrawable(R.drawable.ic_person_black_24dp);
                }
                m_cardImage.setImageDrawable(replacementImage);
            }

            String title;
            if (event.getEventType() == EventType.WORKSHOP) {
                title = context.getString(R.string.cardWorkshopTitle) + ": " + event.getTitle();
            } else {
                title = event.getTitle();
            }

            m_cardTitleText.setText(title);
            m_cardSpeakerName.setText(event.getSpeakerName());

            //Ensure visible button and set to correct data
            m_cardFavouriteButton.setVisibility(View.VISIBLE);
            int favouriteDrawableId = 0;
            if (event.isFavourited()) {
                favouriteDrawableId = R.drawable.ic_favorite_black_24dp;
            } else {
                favouriteDrawableId = R.drawable.ic_favorite_border_black_24dp;
            }
            m_cardFavouriteButton.setImageDrawable(this.itemView.getResources()
                    .getDrawable(favouriteDrawableId));
        }

        m_cardTime.setText(event.getTime());
        m_cardLocation.setText(event.getLocation());
        m_cardDate.setText(event.getDate());
    }

}
