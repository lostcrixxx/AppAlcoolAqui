package br.com.envolvedesenvolve.alcoolaqui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import br.com.envolvedesenvolve.alcoolaqui.controller.Sync;
import br.com.envolvedesenvolve.alcoolaqui.db.HelperDB;
import br.com.envolvedesenvolve.alcoolaqui.db.MarksTable;
import br.com.envolvedesenvolve.alcoolaqui.db.UserTable;
import br.com.envolvedesenvolve.alcoolaqui.model.Marks;
import br.com.envolvedesenvolve.alcoolaqui.model.User;
import br.com.envolvedesenvolve.alcoolaqui.view.AdicionarFragment;

/**
 * Created by Cristiano M. on 21/03/2020
 * Modified by Cristiano M. on 21/03/2020
 */

public class MapsNewActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

    private static final String TAG = "MapsNewActivity";
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private static final int MULTIPLE_PERMISSIONS = 100;
    private static MapsNewActivity sInstance = null; // Singleton instance

    private double lat, lon;
    private boolean focuStarting = true;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Menu menu;

    private GoogleMap mMap;
    private Marker marker;
    private LatLng point = new LatLng(0.0, 0.0);
    private ImageView mylocationButton;

    private RecyclerView recyclerView;
    //    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SupportMapFragment mapFragment;
    private MarkerOptions mOpt = new MarkerOptions();
    private UserTable userTable = new UserTable(this);
    private MarksTable marksTable = new MarksTable(this);

    public static MapsNewActivity getInstance() {
        return sInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);
        sInstance = this; // Setup singleton instance

        checkPermission();

        HelperDB db = HelperDB.getInstance(this);

        Sync sync = new Sync();
        sync.getSyncAll(this);

        try{
        // toolbar custom
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(""); // null or "" remove title
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showDialog(new AdicionarFragment());


//                    // Begin the transaction
//                    FragmentTransaction ft = myContext.getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.nav_host_fragment, new CreateMarkFragment());
//                    ft.commit();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                }
            });


//            try {
//                messageReadListener = (IMapsBranchReadListener) this;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            mylocationButton = findViewById(R.id.my_location);
//            mylocationButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e(TAG, "mylocationButton.setOnClickListener");
//                    centralizeCameraInUserPosition();
//                }
//            });

            settingsMenu();
            setUpMapIfNeeded();

        } catch (Exception e) {
            Log.e(TAG, "ERRO " + e.toString());
            e.printStackTrace();
        }
    }

    long idBranchSpinner;

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            mapFragment.getMapAsync(this);
        }
    }

//    public void addButtonsFavorites() {
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//
//        UserFavorites userTable = new UserFavorites();
//        nameFavoriteList = new ArrayList<>();
//        favoritesList = new ArrayList<>();
//
//        try {
//            nameFavoriteList = userTable.getSelectFavorites();
//
//            for (UserFavorites nameFavorite : nameFavoriteList) {
////                Log.e(TAG, "nameFavorites " + nameFavorite.getmCode());
//                EventType eventType = getmResolver().getEventTypeSingle(nameFavorite.getmCode());
//                favoritesList.add(eventType.getmName());
//            }
//
//            recyclerView.setHasFixedSize(true);
//            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerAdapter = new RecyclerAdapter(favoritesList);
//            recyclerView.setAdapter(recyclerAdapter);
//        } catch (Exception e) {
//            Log.e(TAG, "ERRO nos favoritos " + e.toString());
//            e.printStackTrace();
//        }
//    }

    private void settingsMenu() {
        navigationView = (NavigationView) findViewById(R.id.navView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_tools: {
                        Toast.makeText(getBaseContext(), "Trocar de usuario", Toast.LENGTH_SHORT).show();
//                        logoutFromUser();
                        break;
                    }
                    case R.id.nav_share: {
                        Toast.makeText(getBaseContext(), "Sobre", Toast.LENGTH_SHORT).show();
//                        showDialog(new AboutFragment());
                        break;
                    }
                    case R.id.nav_send: {
                        Toast.makeText(getBaseContext(), "Sair", Toast.LENGTH_SHORT).show();
//                        logout();
                        break;
                    }
                    default: {
                        Toast.makeText(getBaseContext(), "Erro botão não encontrado !", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        Log.e("MapsActivity", "onMapReady !!!");
        mMap = googleMap;

        if (mMap != null) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
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
//                        Log.d(TAG, "localização lat:" + lat + " lon:" + lon);

                        if (focuStarting) {
                            focuStarting = false;
                            centralizeCameraInUserPosition();
                        }

                    } catch (Exception e) {
                        Log.e("", "ERRO myLocation.getLatitude(), myLocation.getLongitude()");
                    }
                }
            });

            } else {
                // Request permission.
                ActivityCompat.requestPermissions(MapsNewActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_LOCATION_REQUEST_CODE);
            }
            centralizeCameraInUserPosition();
        }

        ArrayList<Marks> list = new ArrayList<>();
        list = marksTable.getAllMarks();

        for (Marks line : list) {
            Log.e("main", "linha: " + line.getId());

            LatLng local = (new LatLng(line.getLat(), line.getLon()));
            addMarkerFull(line.getId(), local, line.getFk_product()+"");
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    private void addMarkerFull(int id, LatLng local, String title) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(local);
        markerOptions.title("Localização de Teste");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);
    }

    private void centralizeCameraInUserPosition() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setOnMyLocationClickListener(this);

            centralizeCamera(point);
            updateZoomCamera(17.0f);

        } else {
            // Request permission.
            ActivityCompat.requestPermissions(MapsNewActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        point = new LatLng(location.getLatitude(), location.getLongitude());
        centralizeCamera(point);
        updateZoomCamera(17.0f);
//        Toast.makeText(this, "Current location:\n" + point, Toast.LENGTH_LONG).show();
//        Log.e(TAG, "coordenadas " + location);
    }

    public void addMarker() {
        mMap.addMarker(new MarkerOptions().position(point).title("localização"));
        centralizeCamera(point);
        updateZoomCamera(17.0f);
    }

    private void centralizeCamera(LatLng point) {
//        Log.e(TAG, "centralizeCamera" + point);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }

    private void updateZoomCamera(float zoom) {
        try {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
        } catch (Exception e) {
            Log.e("Maps", "ERRO updateZoomCamera 4");
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
//            case R.id.action_settings: {
//                Toast.makeText(getBaseContext(), "teste", Toast.LENGTH_SHORT).show();
////                spinnerAllDialog.showSpinerDialog();
//                break;
//            }
            default: {
                Toast.makeText(getBaseContext(), "Erro botão não encontrado !", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return true;
    }

//    public void logoutFromUser() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getResources().getString(R.string.logout_dialog_title));
//        builder.setMessage(getResources().getString(R.string.logout_dialog_message));
//        builder.setNegativeButton(getResources().getString(R.string.cancel_value), null);
//        builder.setPositiveButton(getResources().getString(R.string.logout_dialog_button_title), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                getBaseContext().getSharedPreferences(SyncConstants.LOGIN_PREFERENCES_FILE, Context.MODE_PRIVATE).edit().clear().apply(); // remover todos os seus prefs.apply();
//                onDetachedFromWindow();
//                finish();
//                Intent in = new Intent(getBaseContext(), LoginActivity.class);
//                startActivity(in);
//            }
//        });
//        builder.setCancelable(true);
//        builder.show();
//    }

//    public void logout() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(getResources().getString(R.string.quit_dialog_message));
//        builder.setTitle(getResources().getString(R.string.quit_dialog_title));
//        builder.setNegativeButton(getResources().getString(R.string.cancel_value), null);
//        builder.setPositiveButton(getResources().getString(R.string.quit_dialog_button_title), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                onDestroy();
//                finish();
//            }
//        });
//        builder.setCancelable(true);
//        builder.show();
//    }

    public void showDialog(DialogFragment dialogFragment) {
        //mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        dialogFragment.show(ft, "dialog");
    }

    public void showDialog(DialogFragment dialogFragment, Bundle bundle) {
        //mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        dialogFragment.setArguments(bundle);
        // Create and show the dialog.
        dialogFragment.show(ft, "dialog");
    }

//    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
//
//        private ArrayList<String> data;
//
//        public RecyclerAdapter(ArrayList<String> data) {
//            this.data = data;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            // create a new view
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.recycler_row, parent, false);
//
//            ViewHolder vh = new ViewHolder(v);
//
//            return vh;
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.name.setText(data.get(position));
//
//            holder.name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    statusInspection = prefsSpinner.getBoolean(SyncConstants.USER_SETTINGS_STATUS, false);
//
//                    if (statusInspection) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("SELECTED", data.get(position));
//                        bundle.putDouble("latStarting", lat);
//                        bundle.putDouble("lonStarting", lon);
////                        Log.e(TAG, "id do evento " + data.get(position));
//                        showDialog(new MapsNewCreateFragment(), bundle);
////                        Toast.makeText(getBaseContext(), data.get(position), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getBaseContext(), "Nenhuma ", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            public TextView name;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//
//                name = (TextView) itemView.findViewById(R.id.btns_favorites);
//            }
//        }
//    }

    @TargetApi(23)
    public void checkPermission() {
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


