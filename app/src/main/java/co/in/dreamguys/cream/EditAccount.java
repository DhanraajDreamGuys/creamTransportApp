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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.EditAccountAPI;
import co.in.dreamguys.cream.utils.CircularImageView;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.SessionHandler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.isValidEmail;

/**
 * Created by User on 2/13/2017.
 */
public class EditAccount extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    public static String TAG = EditAccount.class.getName();
    EditText mFirstname, mLastname, mEmailAddress, mUserType, mPassword, mChangePassword;
    ImageView mCamera;
    CustomProgressDialog mCustomProgressDialog;
    Button mSaveAccount;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Uri mCapturedImageURI;
    String picturePath;
    int CAMERA_CAPTURE = 1;
    int PICK_IMAGE_FROM_GALLERY = 2;
    CircularImageView mAccountImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initviews();

        mFirstname.setText(getIntent().getStringExtra(Constants.FIRSTNAME));
        mLastname.setText(getIntent().getStringExtra(Constants.LASTNAME));
        mEmailAddress.setText(getIntent().getStringExtra(Constants.EMAIL));
        mUserType.setText(getIntent().getStringExtra(Constants.USERTYPE));

    }

    public void initviews() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_account_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mFirstname = (EditText) findViewById(R.id.AEA_ET_firstname);
        mLastname = (EditText) findViewById(R.id.AEA_ET_lasttname);
        mEmailAddress = (EditText) findViewById(R.id.AEA_ET_emailid);
        mUserType = (EditText) findViewById(R.id.AEA_ET_usertype);
        mPassword = (EditText) findViewById(R.id.AEA_ET_password);
        mChangePassword = (EditText) findViewById(R.id.AEA_ET_confirm_password);
        mSaveAccount = (Button) findViewById(R.id.AEA_ET_save);
        mSaveAccount.setOnClickListener(this);
        mCamera = (ImageView) findViewById(R.id.AEA_IV_camera);
        mCamera.setOnClickListener(this);
        mAccountImage = (CircularImageView) findViewById(R.id.AEA_CIV_profile_image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.AEA_ET_save) {
            if (mEmailAddress.getText().toString().isEmpty()) {
                mEmailAddress.setError(getString(R.string.err_email));
                mEmailAddress.requestFocus();
            } else if (!isValidEmail(mEmailAddress.getText().toString())) {
                mEmailAddress.setError(getString(R.string.err_emailInvalid));
                mEmailAddress.requestFocus();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                MultipartBody.Part body = null;
                if (picturePath != null) {
                    File file = new File(picturePath);
                    if (file != null) {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    }
                }

                Call<EditAccountAPI.EditAccountResponse> loginCall = apiService.getEditAccount(SessionHandler.getStringPref(Constants.USER_ID),
                        mFirstname.getText().toString(), mLastname.getText().toString(), mEmailAddress.getText().toString(),
                        mUserType.getText().toString(), body, mPassword.getText().toString(), mChangePassword.getText().toString());
                loginCall.enqueue(new Callback<EditAccountAPI.EditAccountResponse>() {
                    @Override
                    public void onResponse(Call<EditAccountAPI.EditAccountResponse> call, Response<EditAccountAPI.EditAccountResponse> response) {
                        mCustomProgressDialog.dismiss();
                        if (response.body().equals(Constants.SUCCESS)) {
                            Toast.makeText(EditAccount.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditAccount.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EditAccountAPI.EditAccountResponse> call, Throwable t) {
                        mCustomProgressDialog.dismiss();
                        Log.i(TAG, t.getMessage());
                    }
                });

            }

        } else if (v.getId() == R.id.AEA_IV_camera) {
            MarshallMallowPermission();
            sendImage();
        }
    }

    public void sendImage() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
        android.app.AlertDialog.Builder mAlertBuilder = new android.app.AlertDialog.Builder(EditAccount.this);
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
            Picasso.with(EditAccount.this).load(new File(picturePath)).into(mAccountImage);
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
        Picasso.with(EditAccount.this).load(new File(picturePath)).into(mAccountImage);
        cursor.close();
    }

    private void MarshallMallowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            if (!hasPermissions(EditAccount.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(EditAccount.this, PERMISSIONS, PERMISSION_REQUEST_CODE);
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
