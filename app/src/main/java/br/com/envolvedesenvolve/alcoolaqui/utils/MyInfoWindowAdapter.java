package br.com.envolvedesenvolve.alcoolaqui.utils;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import br.com.envolvedesenvolve.alcoolaqui.R;

public class MyInfoWindowAdapter extends AppCompatActivity implements GoogleMap.InfoWindowAdapter {

    private final View row;

    public MyInfoWindowAdapter(){
        row = getLayoutInflater().inflate(R.layout.custom_address, null);
    }

    @Override
    public View getInfoContents(Marker marker) {

//        TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
//        tvTitle.setText(marker.getTitle());
//        TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
//        tvSnippet.setText(marker.getSnippet());

        TextView t1_locality = (TextView) row.findViewById(R.id.locality);
        TextView t2_latitude = (TextView) row.findViewById(R.id.latTxt);
        TextView t3_longitude = (TextView) row.findViewById(R.id.lngTxt);
        TextView t4_snippet = (TextView) row.findViewById(R.id.snippet);

        LatLng ll = marker.getPosition();
        t1_locality.setText(marker.getTitle());
        t2_latitude.setText(String.valueOf(ll.latitude));
        t3_longitude.setText(String.valueOf(ll.longitude));
        t4_snippet.setText(marker.getSnippet());

        return row;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }

}
