package br.com.envolvedesenvolve.alcoolaqui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import br.com.envolvedesenvolve.alcoolaqui.db.HelperDB;
import br.com.envolvedesenvolve.alcoolaqui.db.UserTable;
import br.com.envolvedesenvolve.alcoolaqui.model.User;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

    private static final String TAG = "MainActivity";
    private static final int MULTIPLE_PERMISSIONS = 100;

    private double lat, lon;
    private boolean focuStarting = true;

    private LatLng point;

    private static MainActivity sInstance = null; // Singleton instance
    private UserTable user = new UserTable(this);

    private AppBarConfiguration mAppBarConfiguration;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private HelperDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sInstance = this; // Setup singleton instance

        checkPermission();

        db = new HelperDB(this);

        ContentValues cv = new ContentValues();
        cv.put("nome", "Elaine");
        cv.put("email", "cris@cris.com.br");
        cv.put("senha", "123");


        user.setValuesDatabase(cv);
        Log.e("main", "insert: ");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        setUpMapIfNeeded();

        ArrayList<User> list = new ArrayList<>();
        list = user.getAllUsers();

        for (User line : list){
            Log.e("main", "linha: " + line.getNome());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        Log.e("MapsActivity", "onMapReady !!!");
        mMap = googleMap;

        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location arg0) {
//                    Log.e("NomadUtilities", "Localização Lat " + arg0.getLatitude() + " Lng " + arg0.getLongitude());
                    try {
                        lat = arg0.getLatitude();
                        lon = arg0.getLongitude();
                        point = new LatLng(lat, lon);

                        if (focuStarting) {
                            focuStarting = false;
//                            getBranchAutomatic(new LatLng());

                            centralizeCameraInUserPosition();
                        }

                    } catch (Exception e) {
                        Log.e("", "ERRO myLocation.getLatitude(), myLocation.getLongitude()");
                    }
                }
            });

//            centralizeCamera(point);
//            updateZoomCamera();
        }

//        centralizeCameraInUserPosition();

//        LatLng latLng = new LatLng();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mMap.addMarker(markerOptions);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {}

    /**
     * Centralize camera on position
     */
    private void centralizeCameraInUserPosition() {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//            mMap.setOnMyLocationClickListener(this);
//
////            centralizeCamera(point);
////            updateZoomCamera(17.0f);
//        } else {
//            // Request permission.
//            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_LOCATION_REQUEST_CODE);
//        }
    }

    @TargetApi(23)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) +
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) +
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, MULTIPLE_PERMISSIONS);
                }

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, MULTIPLE_PERMISSIONS);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {

        Log.d("Display", "Permissões" + requestCode + grantResults);

        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0) {
//                    boolean writeExternalFile = grantResults[2] == PackageManager.PERMISSION_GRANTED;
//                    boolean coarseLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean fineLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    if (!fineLocation || !coarseLocation || !writeExternalFile) {
                    if (!fineLocation) {
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setTitle(this.getResources().getString(R.string.limited_functionality_title));
//                        builder.setMessage(this.getResources().getString(R.string.limited_functionality_message));
//                        builder.setPositiveButton(android.R.string.ok, null);
//                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
                                checkPermission();
//                            }
//                        });
//                        builder.show();
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, MULTIPLE_PERMISSIONS);
                    }
                }
                break;
        }
    }
}
