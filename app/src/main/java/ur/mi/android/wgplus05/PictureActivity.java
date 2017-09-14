package ur.mi.android.wgplus05;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ur.mi.android.wgplus05.Camera;
import ur.mi.android.wgplus05.ImageAdapter;
import ur.mi.android.wgplus05.R;


public class PictureActivity extends AppCompatActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Camera camera;
    private FotoItemAdapter listAdapter;
    private static final int MY_PERMISSION_CAMERA = 1;
    private FrameLayout mainLayout;
    private ArrayList<FotoItem> posts;
    private ImageAdapter gridAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCamera();
        initPostList();
        initUI();
        mainLayout = (FrameLayout) findViewById(R.id.fotowand);

    }

    private void initCamera() {
        camera = new Camera(this);
    }

    private void initPostList() {
        posts = new ArrayList<>();
    }

    private void initUI() {
        setContentView(R.layout.activity_foto_wand);
        Point displaySize = getDisplaySize();

        GridView grid = new GridView(getApplicationContext());
        gridAdapter = new ImageAdapter(this, displaySize);
        grid.setAdapter(gridAdapter);

        listView = (ListView) findViewById(R.id.listview_foto_item);
        listAdapter = new FotoItemAdapter(this,posts);
        listView.setAdapter(listAdapter);


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
        int permissionCheckCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int permissionCheckWriteExStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheckWriteExStorage == PackageManager.PERMISSION_DENIED || permissionCheckCamera == PackageManager.PERMISSION_DENIED) {

                Log.d("photo", "permission ist nicht da ");

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

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
        Point imageSize = new Point(getDisplaySize().x, getDisplaySize().y);
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
            Log.d("foto",Integer.toString(requestCode)+" "+ Integer.toString(resultCode));
        }
            showPopupImage();
    }


    public void showPopupImage() {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.layout_popup_image, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);


        final ImageView imageView = (ImageView) popupView.findViewById(R.id.foto_image_popup);
        imageView.setImageBitmap(gridAdapter.getItem(gridAdapter.getCount()-1));

        final EditText editText = (EditText) popupView.findViewById(R.id.foto_edit_commentary_popup);

        final Button buttonAdd = (Button) popupView.findViewById(R.id.foto_button_popup);




        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FotoItem fotoItem = new FotoItem(editText.getText().toString(), gridAdapter.getItem(gridAdapter.getCount()-1));
                posts.add(0, fotoItem);
                listAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                listView.smoothScrollToPosition(0);


            }
        });
    }


}
