package co.in.dreamguys.cream;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import co.in.dreamguys.cream.utils.CircularImageView;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.isValidEmail;


/**
 * Created by User on 2/14/2017.
 */
public class AddUsers extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Uri mCapturedImageURI;
    String picturePath;
    int CAMERA_CAPTURE = 1;
    int PICK_IMAGE_FROM_GALLERY = 2;
    CircularImageView mAccountImage;
    ImageView mChooseCamera;
    EditText mFirstName, mLastname, mEmail, mPhoneNo, mStreetNo, mCity, mState, mPostalCode;
    TextView mUserType, mBranch, mCountry;
    Button mSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        initWidgets();
        MarshallMallowPermission();
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_add_user_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mAccountImage = (CircularImageView) findViewById(R.id.AAU_CIV_profile_image);
        mChooseCamera = (ImageView) findViewById(R.id.AAU_IV_camera);
        mChooseCamera.setOnClickListener(this);
        mFirstName = (EditText) findViewById(R.id.AAU_ET_firstname);
        mLastname = (EditText) findViewById(R.id.AAU_ET_lastname);
        mEmail = (EditText) findViewById(R.id.AAU_ET_email);
        mPhoneNo = (EditText) findViewById(R.id.AAU_ET_phoneno);
        mStreetNo = (EditText) findViewById(R.id.AAU_ET_streetno);
        mCity = (EditText) findViewById(R.id.AAU_ET_city);
        mState = (EditText) findViewById(R.id.AAU_ET_state);
        mCountry = (TextView) findViewById(R.id.AAU_ET_country);
        mCountry.setOnClickListener(this);
        mPostalCode = (EditText) findViewById(R.id.AAU_ET_postal_code);
        mUserType = (TextView) findViewById(R.id.AAU_ET_usertype);
        mBranch = (TextView) findViewById(R.id.AAU_ET_branch_location);
        mBranch.setOnClickListener(this);
        mUserType.setOnClickListener(this);

        mSave = (Button) findViewById(R.id.AAU_BT_save);
        mSave.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.AAU_IV_camera) {
            sendImage();
        } else if (v.getId() == R.id.AAU_ET_country) {

        } else if (v.getId() == R.id.AAU_ET_usertype) {

        } else if (v.getId() == R.id.AAU_ET_branch_location) {

        } else if (v.getId() == R.id.AAU_BT_save) {
            if (validation()) {

            }
        }
    }

    private boolean validation() {

        if (mFirstName.getText().toString().isEmpty()) {
            mFirstName.setError(getString(R.string.err_first_name));
            mFirstName.requestFocus();
        } else if (mLastname.getText().toString().isEmpty()) {
            mLastname.setError(getString(R.string.err_last_name));
            mLastname.requestFocus();
        } else if (mEmail.getText().toString().isEmpty()) {
            mEmail.setError(getString(R.string.err_email));
            mEmail.requestFocus();
        } else if (!isValidEmail(mEmail.getText().toString())) {
            mEmail.setError(getString(R.string.err_emailInvalid));
            mEmail.requestFocus();
        } else if (mPhoneNo.getText().toString().isEmpty()) {
            mPhoneNo.setError(getString(R.string.err_phone));
            mPhoneNo.requestFocus();
        } else if (mStreetNo.getText().toString().isEmpty()) {
            mStreetNo.setError(getString(R.string.err_street_no));
            mStreetNo.requestFocus();
        } else if (mCity.getText().toString().isEmpty()) {
            mCity.setError(getString(R.string.err_city));
            mCity.requestFocus();
        } else if (mState.getText().toString().isEmpty()) {
            mState.setError(getString(R.string.err_state));
            mState.requestFocus();
        } else if (mCountry.getText().toString().isEmpty()) {
            mCountry.setError(getString(R.string.err_country));
            mCountry.requestFocus();
        } else if (mPostalCode.getText().toString().isEmpty()) {
            mPostalCode.setError(getString(R.string.err_psotal_code));
            mPostalCode.requestFocus();
        } else if (mUserType.getText().toString().isEmpty()) {
            mUserType.setError(getString(R.string.err_user_type));
            mUserType.requestFocus();
        } else if (mBranch.getText().toString().isEmpty()) {
            mBranch.setError(getString(R.string.err_branch));
            mBranch.requestFocus();
        } else if (!isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void sendImage() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
        android.app.AlertDialog.Builder mAlertBuilder = new android.app.AlertDialog.Builder(AddUsers.this);
        mAlertBuilder.setTitle("Choose the Options");
        mAlertBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Take Photo")) {
                    String fileName = "temp.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                    startActivityForResult(intent, CAMERA_CAPTURE);
                } else if (options[which].equals("Choose From Gallery")) {
                    Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    in.setType("image/*");
                    startActivityForResult(Intent.createChooser(in, "Select Picture"), PICK_IMAGE_FROM_GALLERY);
                } else if (options[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        mAlertBuilder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                camera();
            } else if (requestCode == PICK_IMAGE_FROM_GALLERY) {
                gallery(data);
            }
        }
    }


    private void gallery(Intent data) {
        if (data.getData() != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            System.out.println("Res picturePath :" + picturePath);
            Picasso.with(AddUsers.this).load(new File(picturePath)).into(mAccountImage);
            cursor.close();
        }
    }

    private void camera() {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(mCapturedImageURI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        picturePath = cursor.getString(column_index_data);
        System.out.println("Res mCapturedImageURI :" + mCapturedImageURI);
        System.out.println("Res picturePath :" + picturePath);
        Picasso.with(AddUsers.this).load(new File(picturePath)).into(mAccountImage);
        cursor.close();
    }

    private void MarshallMallowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            if (!hasPermissions(AddUsers.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(AddUsers.this, PERMISSIONS, PERMISSION_REQUEST_CODE);
            }
        }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                break;
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

        }
        return true;
    }
}
