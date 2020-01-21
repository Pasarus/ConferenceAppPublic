package uk.ac.aber.dcs.cs31620.conferenceapp.ui.speakers;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.Speaker;

public class SpeakerCard extends RecyclerView.ViewHolder {
    private TextView m_twitterHandle;
    private TextView m_speakerName;
    private ImageView m_speakerImage;
    private MaterialCardView m_cardView;
    private Speaker m_speaker;

    public SpeakerCard(@NonNull View itemView) {
        super(itemView);

        m_twitterHandle = itemView.findViewById(R.id.cardTwitterHandle);
        m_speakerName = itemView.findViewById(R.id.cardTitleText);
        m_speakerImage = itemView.findViewById(R.id.cardImage);
        m_cardView = itemView.findViewById(R.id.cardView);
        m_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpeakerActivity();
            }
        });
    }

    /**
     * This pushes an intent to show the speaker details. This using the speaker object that is set
     * when bindDataSet is called. This must not be called if bindDataSet has not been called as
     * m_speaker will be null.
     */
    private void showSpeakerActivity() {
        DataService ds = DataService.getDataService();
        Intent intent = new Intent(m_cardView.getContext().getApplicationContext(),
                SpeakerActivity.class);
        intent.putExtra("speakerDetails", ds.getSpeaker(m_speaker.getSpeakerId()));
        m_cardView.getContext().startActivity(intent);
    }

    public void bindDataSet(@NonNull Speaker speaker) {
        m_speakerName.setText(speaker.getSpeakerName());
        try {
            m_speakerImage.setImageDrawable(Drawable.createFromStream(
                    m_cardView.getContext().getApplicationContext().getAssets()
                            .open("images/speakers/" + speaker.getSpeakerId()),
                    null));
        } catch (IOException e) {
            // Don't change the image
        }
        m_twitterHandle.setText(speaker.getTwitterHandle());
        m_speaker = speaker;
    }
}
