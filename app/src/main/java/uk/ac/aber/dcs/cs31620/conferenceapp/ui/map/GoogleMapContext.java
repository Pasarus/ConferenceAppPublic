package uk.ac.aber.dcs.cs31620.conferenceapp.ui.map;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This is needed to show a google map. It is the context for it.
 */
public class GoogleMapContext extends Fragment implements OnMapReadyCallback {
    private double m_locationLongitude;
    private double m_locationLatitude;
    private static final float ZOOM_LEVEL = 20;

    public GoogleMapContext() {
    }

    public GoogleMapContext(double longitude, double latitude) {
        m_locationLatitude = latitude;
        m_locationLongitude = longitude;
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        LatLng pos = new LatLng(m_locationLatitude, m_locationLongitude);
        MarkerOptions markerPos = new MarkerOptions().position(pos);
        googleMap.addMarker(markerPos);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, ZOOM_LEVEL));
    }
}
