package br.com.envolvedesenvolve.alcoolaqui.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.envolvedesenvolve.alcoolaqui.MapsActivity;
import br.com.envolvedesenvolve.alcoolaqui.R;
import br.com.envolvedesenvolve.alcoolaqui.controller.Sync;
import br.com.envolvedesenvolve.alcoolaqui.db.HelperDB;

public class Splash extends AppCompatActivity {

    private static final int MULTIPLE_PERMISSIONS = 100;

    Animation anim;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkPermission();

        HelperDB db = HelperDB.getInstance(this);

        Sync sync = new Sync();
        sync.getSyncAll(this);

        imageView = (ImageView)findViewById(R.id.imageView2); // Declare an imageView to show the animation.

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplication(), MapsActivity.class));
                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);
    }

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