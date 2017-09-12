package ur.mi.android.wgplus05;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import ur.mi.android.wgplus05.Camera;
import ur.mi.android.wgplus05.ImageAdapter;
import ur.mi.android.wgplus05.R;


public class PictureActivity extends AppCompatActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Camera camera;
    private ImageAdapter gridAdapter;
    private static final int MY_PERMISSION_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCamera();
        initUI();
    }

    private void initCamera() {
        camera = new Camera(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_foto_wand);
        Point displaySize = getDisplaySize();

       GridView grid = (GridView) findViewById(R.id.photo_grid);
        gridAdapter = new ImageAdapter(this, displaySize);
        grid.setAdapter(gridAdapter);

        ImageButton cameraButton = (ImageButton) findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.photo_add_button) {
            checkPermission();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {

                Log.d("photo", "permission ist nicht da ");

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }
            else Log.d("photo", "permission ist schon da "); takePicture();
        }




    private void takePicture() {
        Log.d("photo", "*photo*");
        camera.takePicture(REQUEST_IMAGE_CAPTURE);
    }

    private void processPicture(String path) {
        Point imageSize = new Point(getDisplaySize().x / 2, getDisplaySize().y / 2);
        Bitmap image = camera.getScaledBitmap(path, imageSize);

        gridAdapter.addImage(image);
        gridAdapter.notifyDataSetChanged();
    }

    private Point getDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            processPicture(camera.getCurrentPhotoPath());
        }
    }


}
