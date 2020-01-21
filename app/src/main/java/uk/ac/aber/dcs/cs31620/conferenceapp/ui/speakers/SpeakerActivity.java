package uk.ac.aber.dcs.cs31620.conferenceapp.ui.speakers;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

import uk.ac.aber.dcs.cs31620.conferenceapp.R;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.Speaker;

/**
 * This Activity, takes a Speaker object. The speaker object contains all the data necessary
 * for this to show, and display the details properly.
 */

public class SpeakerActivity extends AppCompatActivity {
    private Speaker m_speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the up button to the action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        m_speaker = (Speaker) getIntent().getSerializableExtra("speakerDetails");

        TextView speakerNameView = findViewById(R.id.speakerActivitySpeakerName);
        speakerNameView.setText(m_speaker.getSpeakerName());

        TextView speakerTwitterHandle = findViewById(R.id.speakerActivityTwitterHandle);
        speakerTwitterHandle.setText(m_speaker.getTwitterHandle());

        ImageView speakerImageView = findViewById(R.id.speakerActivitySpeakerImage);
        String imagePath = "images/speakers/" + m_speaker.getSpeakerId();
        try {
            speakerImageView.setImageDrawable(Drawable.createFromStream(getAssets()
                    .open(imagePath), null));
        } catch (IOException e) {
            // Do nothing. Leave as the default avatar.
        }

        TextView speakerDetailsView = findViewById(R.id.speakerActivitySpeakerDetails);
        speakerDetailsView.setText(m_speaker.getSpeakerDetails());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
