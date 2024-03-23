package com.example.user.reservation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by User on 15-Dec-17.
 */

public class booking_details extends AppCompatActivity implements View.OnClickListener {

    RequestQueue rq;
    TextView TextviewName,TextviewStatus,TextviewPrice,TextviewCheckin,TextviewCheckout,TextviewAddress;
    ImageView imageChoose;
    private Button buttonUpload;

    String reservation_id;
    String UPLOAD_URL = "http://istay.000webhostapp.com/homestay/upload_receipt.php";

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    //Uri to store the image uri
    private Uri filePath;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookingdetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Requesting storage permission
        requestStoragePermission();

        rq = Volley.newRequestQueue(this);
        TextviewName = (TextView) findViewById(R.id.name);
        TextviewStatus = (TextView) findViewById(R.id.status);
        TextviewPrice = (TextView) findViewById(R.id.price);
        TextviewCheckin = (TextView) findViewById(R.id.checkin);
        TextviewCheckout = (TextView) findViewById(R.id.checkout);
        TextviewAddress = (TextView) findViewById(R.id.address);
        imageChoose = (ImageView) findViewById(R.id.image);
        buttonUpload = (Button) findViewById(R.id.Upload);

        final Intent intent = getIntent();
        reservation_id = intent.getStringExtra("reservation_id");
        final String homestay_name = intent.getStringExtra("homestay_name");
        status = intent.getStringExtra("status");
        final String price = intent.getStringExtra("price");



        String checkin = intent.getStringExtra("checkin");
        String checkout = intent.getStringExtra("checkout");
        String address = intent.getStringExtra("address");
        String receipt = intent.getStringExtra("receipt");


       // Toast.makeText(this, reservation_id, Toast.LENGTH_SHORT).show();

        TextviewName.setText(homestay_name);
        TextviewStatus.setText(status);
        TextviewPrice.setText("RM "+price);

        //change the date format//
        StringBuilder str_checkin = new StringBuilder(checkin);
        str_checkin.insert(4, '/');
        str_checkin.insert(7, '/');
        //change the date format//

        //change the date format//
        StringBuilder str_checkout = new StringBuilder(checkout);
        str_checkout.insert(4, '/');
        str_checkout.insert(7, '/');
        //change the date format//

        TextviewCheckin.setText(str_checkin);
        TextviewCheckout.setText(str_checkout);
        TextviewAddress.setText(address);

        if(status.equals("Confirmed") || status.equals("Pending")) {
            buttonUpload.setVisibility(View.INVISIBLE);
        }
        //set  image
        if (!receipt.equalsIgnoreCase(""))
            Picasso.with(getApplicationContext())
                    .load(receipt)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.drawable.loading_icon)// Place holder image from drawable folder
                    .error(R.drawable.profile)
                    .into(imageChoose);
        //set image end

        //Setting clicklistener
        imageChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    public void uploadMultipart() {
        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId,UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", reservation_id) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageChoose.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == imageChoose && !(status.equals("Confirmed")) && !(status.equals("Pending"))) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            if (bitmap != null)
            {    uploadMultipart();
            }
            else
            {
                Toast.makeText(this, "Please select image!!", Toast.LENGTH_LONG).show();
            }

        }
    }

    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logoutz
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(booking_details.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
    //logout end

    //back button in title
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }




}
