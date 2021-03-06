package ur.mi.android.wgplus05;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Camera {
    PictureActivity activityContext;
    private String currentPhotoPath;

    public Camera(PictureActivity activityContext) {
        this.activityContext = activityContext;
    }

    public void takePicture(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activityContext.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created

            if (photoFile != null) {
                if(Build.VERSION.SDK_INT < 24){
                Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activityContext.startActivityForResult(takePictureIntent, requestCode);
            }
            else {
                Uri photoURI = FileProvider.getUriForFile(activityContext,
                        BuildConfig.APPLICATION_ID + ".genericfileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activityContext.startActivityForResult(takePictureIntent, requestCode);
            }
            }
        }
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public Bitmap getScaledBitmap(String path, Point targetSize) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


        int scaleFactor = Math.min(photoW / targetSize.x, photoH / targetSize.y);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

        return bitmap;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();

        return image;

    }
}
