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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.apis.UserTypeAPI;
import co.in.dreamguys.cream.model.UsersModel;
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

import static co.in.dreamguys.cream.utils.Util.buildCountryAlert;
import static co.in.dreamguys.cream.utils.Util.buildUserTypeAlert;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.isValidEmail;

/**
 * Created by user5 on 22-02-2017.
 */

public class ViewUsers extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    View mUsersView;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Uri mCapturedImageURI;
    String picturePath, currentDate, countryName, userType;
    int CAMERA_CAPTURE = 1;
    int PICK_IMAGE_FROM_GALLERY = 2;
    CircularImageView mAccountImage;
    ImageView mChooseCamera;
    EditText mFirstName, mLastname, mEmail, mPhoneNo, mStreetNo, mCity, mState, mPostalCode;
    TextView mUserType, mBranch, mCountry;
    Button mSave;
    public static String TAG = ViewUsers.class.getName();

    UsersModel mUsersModel;
    CustomProgressDialog mCustomProgressDialog;
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Calendar mCalendar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomProgressDialog = new CustomProgressDialog(this);
        if (getIntent().getIntExtra(Constants.VIEW_TYPE, -1) == 0) {
            mUsersView = getLayoutInflater().inflate(R.layout.activity_add_users, null);
            setContentView(mUsersView);
            intiEditWidgets();
        } else {
            mUsersView = getLayoutInflater().inflate(R.layout.activity_view_users, null);
            setContentView(mUsersView);
            intiWidgets();
        }
        mCalendar = new GregorianCalendar();
        currentDate = mSimpleDateFormat.format(mCalendar.getTime());

        MarshallMallowPermission();

        mUsersModel = (UsersModel) getIntent().getSerializableExtra(Constants.USERS_DATA);
        if (mUsersModel != null) {
            mFirstName.setText(mUsersModel.getFirst_name());
            mLastname.setText(mUsersModel.getLast_name());
            mEmail.setText(mUsersModel.getEmail());
            mPhoneNo.setText(mUsersModel.getPhone());


            JsonObject loadFrom = new Gson().fromJson(mUsersModel.getAddress(), JsonObject.class);
            mStreetNo.setText(loadFrom.get("street").toString().replace("\"", ""));
            mCity.setText(loadFrom.get("city").toString().replace("\"", ""));
            mState.setText(loadFrom.get("state").toString().replace("\"", ""));
            mCountry.setText(loadFrom.get("country").toString().replace("\"", ""));
            mPostalCode.setText(loadFrom.get("pincode").toString().replace("\"", ""));


            for (UserTypeAPI.Datum usertype : Constants.usertype) {
                if (mUsersModel.getUser_type().equalsIgnoreCase(usertype.getId())) {
                    mUserType.setText(usertype.getName());
                }
            }


            for (BranchAPI.Datum branch : Constants.countries) {
                if (branch.getId().equalsIgnoreCase(mUsersModel.getBranch_id()))
                    mBranch.setText(branch.getName());
            }

        }


    }

    private void intiEditWidgets() {
        mToolbar = (Toolbar) mUsersView.findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_edit_user_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mAccountImage = (CircularImageView) mUsersView.findViewById(R.id.AAU_CIV_profile_image);
        mChooseCamera = (ImageView) mUsersView.findViewById(R.id.AAU_IV_camera);
        mChooseCamera.setOnClickListener(this);
        mFirstName = (EditText) mUsersView.findViewById(R.id.AAU_ET_firstname);
        mLastname = (EditText) mUsersView.findViewById(R.id.AAU_ET_lastname);
        mEmail = (EditText) mUsersView.findViewById(R.id.AAU_ET_email);
        mPhoneNo = (EditText) mUsersView.findViewById(R.id.AAU_ET_phoneno);
        mStreetNo = (EditText) mUsersView.findViewById(R.id.AAU_ET_streetno);
        mCity = (EditText) mUsersView.findViewById(R.id.AAU_ET_city);
        mState = (EditText) mUsersView.findViewById(R.id.AAU_ET_state);
        mCountry = (TextView) mUsersView.findViewById(R.id.AAU_ET_country);
        mCountry.setOnClickListener(this);
        mPostalCode = (EditText) mUsersView.findViewById(R.id.AAU_ET_postal_code);
        mUserType = (TextView) mUsersView.findViewById(R.id.AAU_ET_usertype);
        mBranch = (TextView) mUsersView.findViewById(R.id.AAU_ET_branch_location);
        mBranch.setOnClickListener(this);
        mUserType.setOnClickListener(this);

        mSave = (Button) mUsersView.findViewById(R.id.AAU_BT_save);
        mSave.setOnClickListener(this);
    }

    private void intiWidgets() {
        mToolbar = (Toolbar) mUsersView.findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_view_user_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mAccountImage = (CircularImageView) mUsersView.findViewById(R.id.AVU_CIV_profile_image);
        mFirstName = (EditText) mUsersView.findViewById(R.id.AVU_ET_firstname);
        mLastname = (EditText) mUsersView.findViewById(R.id.AVU_ET_lastname);
        mEmail = (EditText) mUsersView.findViewById(R.id.AVU_ET_email);
        mPhoneNo = (EditText) mUsersView.findViewById(R.id.AVU_ET_phoneno);
        mStreetNo = (EditText) mUsersView.findViewById(R.id.AVU_ET_streetno);
        mCity = (EditText) mUsersView.findViewById(R.id.AVU_ET_city);
        mState = (EditText) mUsersView.findViewById(R.id.AVU_ET_state);
        mCountry = (TextView) mUsersView.findViewById(R.id.AVU_ET_country);
        mPostalCode = (EditText) mUsersView.findViewById(R.id.AVU_ET_postal_code);
        mUserType = (TextView) mUsersView.findViewById(R.id.AVU_ET_usertype);
        mBranch = (TextView) mUsersView.findViewById(R.id.AVU_ET_branch_location);
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
            buildCountryAlert(ViewUsers.this, getLayoutInflater(), mCountry);
        } else if (v.getId() == R.id.AAU_ET_usertype) {
            buildUserTypeAlert(ViewUsers.this, getLayoutInflater(), mUserType);
        } else if (v.getId() == R.id.AAU_ET_branch_location) {
            Constants.AdminMenu.getLocations(ViewUsers.this, mBranch, Constants.FromString);
        } else if (v.getId() == R.id.AAU_BT_save) {
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
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                MultipartBody.Part body = null;
                if (picturePath != null) {
                    File file = new File(picturePath);
                    if (file != null) {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    }
                }

                for (UserTypeAPI.Datum usertype : Constants.usertype) {
                    if (mUserType.getText().toString().equalsIgnoreCase(usertype.getName())) {
                        userType = usertype.getId();
                    }
                }
                for (BranchAPI.Datum branch : Constants.countries) {
                    if (branch.getName().equalsIgnoreCase(mBranch.getText().toString()))
                        countryName = branch.getId();
                }

                Call<UpdateUsersAPI.UpdateUsersResponse> repairsheet = apiService.getEditUser(sendPartMap(), body);
                repairsheet.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Constants.USERSCLASS.mUserWidget.setAdapter(null);
                            Constants.USERSCLASS.getUserLists();
                            finish();
                            Toast.makeText(ViewUsers.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ViewUsers.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        android.app.AlertDialog.Builder mAlertBuilder = new android.app.AlertDialog.Builder(ViewUsers.this);
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
            Picasso.with(ViewUsers.this).load(new File(picturePath)).into(mAccountImage);
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
        Picasso.with(ViewUsers.this).load(new File(picturePath)).into(mAccountImage);
        cursor.close();
    }

    private void MarshallMallowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            if (!hasPermissions(ViewUsers.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(ViewUsers.this, PERMISSIONS, PERMISSION_REQUEST_CODE);
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


    @Override
    protected void onStop() {
        super.onStop();
        if (mCustomProgressDialog!=null && mCustomProgressDialog.isShowing()){
            mCustomProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCustomProgressDialog!=null && mCustomProgressDialog.isShowing()){
            mCustomProgressDialog.dismiss();
        }
    }

    private Map<String, RequestBody> sendPartMap() {
        RequestBody userid = RequestBody.create(okhttp3.MultipartBody.FORM, SessionHandler.getStringPref(Constants.USER_ID));
        RequestBody firstName = RequestBody.create(okhttp3.MultipartBody.FORM, mFirstName.getText().toString());
        RequestBody lastname = RequestBody.create(okhttp3.MultipartBody.FORM, mLastname.getText().toString());
        RequestBody emailAddress = RequestBody.create(okhttp3.MultipartBody.FORM, mEmail.getText().toString());
        RequestBody phoneno = RequestBody.create(okhttp3.MultipartBody.FORM, mPhoneNo.getText().toString());
        RequestBody streetno = RequestBody.create(okhttp3.MultipartBody.FORM, mStreetNo.getText().toString());
        RequestBody city = RequestBody.create(okhttp3.MultipartBody.FORM, mCity.getText().toString());
        RequestBody state = RequestBody.create(okhttp3.MultipartBody.FORM, mState.getText().toString());
        RequestBody country = RequestBody.create(okhttp3.MultipartBody.FORM, mCountry.getText().toString());
        RequestBody pincode = RequestBody.create(okhttp3.MultipartBody.FORM, mPostalCode.getText().toString());
        RequestBody usertype = RequestBody.create(okhttp3.MultipartBody.FORM, userType);
        RequestBody userbranch = RequestBody.create(okhttp3.MultipartBody.FORM, countryName);
        RequestBody orderAsc = RequestBody.create(okhttp3.MultipartBody.FORM, currentDate);

        Map<String, RequestBody> map = new HashMap<>();
        map.put(Constants.PARAMS_ID, userid);
        map.put(Constants.PARAMS_FTNAME, firstName);
        map.put(Constants.PARAMS_LTNAME, lastname);
        map.put(Constants.PARAMS_EMAIL, emailAddress);
        map.put(Constants.PARAMS_PHONE, phoneno);
        map.put(Constants.PARAMS_STREET, streetno);
        map.put(Constants.PARAMS_CITY, city);
        map.put(Constants.PARAMS_STATE, state);
        map.put(Constants.PARAMS_COUNTRY, country);
        map.put(Constants.PARAMS_PINCODE, pincode);
        map.put(Constants.PARAMS_UTYPE, usertype);
        map.put(Constants.PARAMS_UBRANCH, userbranch);
        map.put(Constants.PARAMS_CDATE, orderAsc);
        return map;
    }

}
