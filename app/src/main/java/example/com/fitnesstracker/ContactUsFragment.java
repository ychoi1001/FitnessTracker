package example.com.fitnesstracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactUsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    Button phoneBtn, emailBtn, webBtn;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    public static final String TAG = ContactUsFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");

        phoneBtn = view.findViewById(R.id.phone_btn);
        phoneBtn.setOnClickListener(this);
        emailBtn = view.findViewById(R.id.email_btn);
        emailBtn.setOnClickListener(this);
        webBtn = view.findViewById(R.id.web_btn);
        webBtn.setOnClickListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //43.6567° N, 79.3862° W
        LatLng location = new LatLng(43.6567, -79.3862);
        mMap.addMarker(new MarkerOptions().position(location).title("Here we are!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.phone_btn:
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    callPhone();
                }
                break;
            case R.id.email_btn:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "fitness@somewhere.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Fitness Tracker");
                //intent.putExtra(Intent.EXTRA_TEXT, "=============\t");
                startActivity(Intent.createChooser(intent, ""));
                break;
            case R.id.web_btn:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.senecacollege.ca")));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                }
            }
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:4165555555"));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
    }
}
