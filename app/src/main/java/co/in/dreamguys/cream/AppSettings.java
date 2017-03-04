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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.AppsettingAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 03-03-2017.
 */

public class AppSettings extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    EditText mSplashText, mAppName, mDesc;
    ImageView mSplash, mLogo, mBackground, mSplashImage, mLogoImage, mBackgroundImage;
    Button mSave;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Uri mCapturedImageURI;
    String picturePath, chooseCategory = "", splashPath = "", logoPath = "", backgroundPath = "";
    int CAMERA_CAPTURE = 1;
    int PICK_IMAGE_FROM_GALLERY = 2;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = AppSettings.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MarshallMallowPermission();
        setContentView(R.layout.activity_app_settings);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        getAppSettings();

    }

    private void getAppSettings() {

        if (!isNetworkAvailable(this)) {
            Toast.makeText(AppSettings.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<AppsettingAPI.AppsettingsResponse> loginCall = apiService.getAppSettings();

            loginCall.enqueue(new Callback<AppsettingAPI.AppsettingsResponse>() {
                @Override
                public void onResponse(Call<AppsettingAPI.AppsettingsResponse> call, Response<AppsettingAPI.AppsettingsResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        AppsettingAPI.Datum mData = response.body().getData().get(0);
                        mSplashText.setText(mData.getSplashtext());
                        mAppName.setText(mData.getName());
                        mDesc.setText(mData.getDescription());
                    } else {
                        Toast.makeText(AppSettings.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppsettingAPI.AppsettingsResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });

        }

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_app_settings_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mSplashText = (EditText) findViewById(R.id.AAS_ET_app_splash_text);
        mAppName = (EditText) findViewById(R.id.AAS_ET_app_name);
        mDesc = (EditText) findViewById(R.id.AAS_ET_app_desc);

        mSplash = (ImageView) findViewById(R.id.AAS_IV_splash_screen);
        mLogo = (ImageView) findViewById(R.id.AAS_IV_logo);
        mBackground = (ImageView) findViewById(R.id.AAS_IV_background);
        mSplashImage = (ImageView) findViewById(R.id.AAS_IV_splash_image);
        mLogoImage = (ImageView) findViewById(R.id.AAS_IV_logo_image);
        mBackgroundImage = (ImageView) findViewById(R.id.AAS_IV_background_image);

        mSave = (Button) findViewById(R.id.AAS_BT_save);

        mSplash.setOnClickListener(this);
        mLogo.setOnClickListener(this);
        mBackground.setOnClickListener(this);
        mSave.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == mSplash.getId()) {
            sendImage();
            chooseCategory = "SPLASH";
        } else if (v.getId() == mLogo.getId()) {
            sendImage();
            chooseCategory = "LOGO";
        } else if (v.getId() == mBackground.getId()) {
            sendImage();
            chooseCategory = "BACKGROUND";
        } else if (v.getId() == mSave.getId()) {
            if (!isNetworkAvailable(this)) {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                MultipartBody.Part splash = null, logo = null, backgroud = null;
                if (!splashPath.isEmpty()) {
                    File file = new File(splashPath);
                    if (file != null) {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        splash = MultipartBody.Part.createFormData("splash", file.getName(), requestFile);
                    }
                }
                if (!logoPath.isEmpty()) {
                    File file = new File(logoPath);
                    if (file != null) {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        logo = MultipartBody.Part.createFormData("logo", file.getName(), requestFile);
                    }
                }
                if (!backgroundPath.isEmpty()) {
                    File file = new File(backgroundPath);
                    if (file != null) {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        backgroud = MultipartBody.Part.createFormData("bgimage", file.getName(), requestFile);
                    }
                }
                Call<UpdateUsersAPI.UpdateUsersResponse> repairsheet = apiService.saveAppSettings(sendPartMap(), splash, logo, backgroud);
                repairsheet.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(AppSettings.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AppSettings.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mCustomProgressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UpdateUsersAPI.UpdateUsersResponse> call, Throwable t) {
                        Log.i(TAG, t.getMessage());
                        mCustomProgressDialog.dismiss();
                    }
                });
            }
        }
    }

    public void sendImage() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
        android.app.AlertDialog.Builder mAlertBuilder = new android.app.AlertDialog.Builder(AppSettings.this);
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
            switch (chooseCategory) {
                case "SPLASH":
                    splashPath = cursor.getString(columnIndex);
                    System.out.println("Res picturePath :" + splashPath);
                    Picasso.with(AppSettings.this).load(new File(splashPath)).into(mSplashImage);
                    break;
                case "LOGO":
                    logoPath = cursor.getString(columnIndex);
                    System.out.println("Res picturePath :" + logoPath);
                    Picasso.with(AppSettings.this).load(new File(logoPath)).into(mLogoImage);
                    break;
                case "BACKGROUND":
                    backgroundPath = cursor.getString(columnIndex);
                    System.out.println("Res picturePath :" + backgroundPath);
                    Picasso.with(AppSettings.this).load(new File(backgroundPath)).into(mBackgroundImage);
                    break;
            }
            cursor.close();
        }
    }

    private void camera() {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(mCapturedImageURI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        switch (chooseCategory) {
            case "SPLASH":
                splashPath = cursor.getString(column_index_data);
                System.out.println("Res mCapturedImageURI :" + mCapturedImageURI);
                System.out.println("Res picturePath :" + splashPath);
                Picasso.with(AppSettings.this).load(new File(splashPath)).into(mSplashImage);
                break;
            case "LOGO":
                logoPath = cursor.getString(column_index_data);
                System.out.println("Res mCapturedImageURI :" + mCapturedImageURI);
                System.out.println("Res picturePath :" + logoPath);
                Picasso.with(AppSettings.this).load(new File(logoPath)).into(mLogoImage);
                break;
            case "BACKGROUND":
                backgroundPath = cursor.getString(column_index_data);
                System.out.println("Res mCapturedImageURI :" + mCapturedImageURI);
                System.out.println("Res picturePath :" + backgroundPath);
                Picasso.with(AppSettings.this).load(new File(backgroundPath)).into(mBackgroundImage);
                break;
        }

        cursor.close();
    }


    private void MarshallMallowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            if (!hasPermissions(AppSettings.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(AppSettings.this, PERMISSIONS, PERMISSION_REQUEST_CODE);
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

    private Map<String, RequestBody> sendPartMap() {
        RequestBody id = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
        RequestBody firstName = RequestBody.create(okhttp3.MultipartBody.FORM, mAppName.getText().toString());
        RequestBody lastname = RequestBody.create(okhttp3.MultipartBody.FORM, mSplashText.getText().toString());
        RequestBody emailAddress = RequestBody.create(okhttp3.MultipartBody.FORM, mDesc.getText().toString());


        Map<String, RequestBody> map = new HashMap<>();
        map.put(Constants.PARAMS_ID, id);
        map.put(Constants.PARAMS_LINK_NAME, firstName);
        map.put(Constants.PARAMS_SPLASH_TEXT, lastname);
        map.put(Constants.PARAMS_DESCRIPTION, emailAddress);
        return map;
    }
}
