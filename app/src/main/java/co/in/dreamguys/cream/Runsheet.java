package co.in.dreamguys.cream;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.in.dreamguys.cream.adapter.DaysAdapter;
import co.in.dreamguys.cream.adapter.InnerStateListAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.InnerStateAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 11-02-2017.
 */

public class Runsheet extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mState, mDays;
    Button mSend;
    DaysAdapter aDaysAdapter;
    public static String TAG = Runsheet.class.getName();
    CustomProgressDialog mCustomProgressDialog;
    Calendar calendar;
    String day = "", selectedDay = "", selectedId = "";

    public enum Day {
        SUNDAY("Sunday", 1), MONDAY("Monday", 2), TUESDAY("Tuesday", 3), WEDNESDAY("Wednesday", 4), THURSDAY("Thrusday", 5), FRIDAY("Friday", 6),
        SATURDAY("Saturday", 7);

        private String stringValue;
        private int intValue;

        private Day(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runsheet);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        calendar = Calendar.getInstance();
        Date mDate = calendar.getTime();
        day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(mDate.getTime());
        for (Day dayText : Day.values())
            if (day.equalsIgnoreCase(dayText.toString())) {
                mDays.setText(dayText.toString());
                selectedDay = dayText.stringValue;
                Log.i(TAG, "" + selectedDay);
            }

        getInnerState();
    }

    private void getInnerState() {
        if (!isNetworkAvailable(this)) {
            Snackbar.make(findViewById(R.id.ARS_LL_parent), getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<InnerStateAPI.InnerStateResponse> loginCall = apiService.getInnerState();
            loginCall.enqueue(new Callback<InnerStateAPI.InnerStateResponse>() {
                @Override
                public void onResponse(Call<InnerStateAPI.InnerStateResponse> call, Response<InnerStateAPI.InnerStateResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Constants.INNERSTATE = response.body().getData();
                    } else {
                        Snackbar.make(findViewById(R.id.ARS_LL_parent), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<InnerStateAPI.InnerStateResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_runsheet_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mState = (TextView) findViewById(R.id.ARS_TV_from);
        mDays = (TextView) findViewById(R.id.ARS_TV_days);
        mSend = (Button) findViewById(R.id.ARS_BT_send);

        mState.setOnClickListener(this);
        mDays.setOnClickListener(this);
        mSend.setOnClickListener(this);
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
        if (v.getId() == mState.getId()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Runsheet.this);
            View driverlayout = getLayoutInflater().inflate(R.layout.dialog_sub_items_list, null);
            builder.setView(driverlayout);

            ListView mDriverViews = (ListView) driverlayout.findViewById(R.id.DSIL_LV_sub_lists);
            final EditText mSearchWord = (EditText) driverlayout.findViewById(R.id.DSIL_ET_search_word);
            final AlertDialog alert = builder.create();

            final InnerStateListAdapter aInnerStateListAdapter = new InnerStateListAdapter(Runsheet.this, Constants.INNERSTATE);
            mDriverViews.setAdapter(aInnerStateListAdapter);
            mDriverViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InnerStateAPI.Datum mInnerState = aInnerStateListAdapter.getItem(position);
                    mState.setText(mInnerState.getName());
                    selectedId = mInnerState.getId();
                    alert.dismiss();
                }
            });

            alert.show();

            mSearchWord.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    aInnerStateListAdapter.setSearchEnabled(true, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if (v.getId() == mDays.getId()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Runsheet.this);
            View driverlayout = getLayoutInflater().inflate(R.layout.dialog_days, null);
            builder.setView(driverlayout);
            final AlertDialog alert = builder.create();
            ListView mDriverViews = (ListView) driverlayout.findViewById(R.id.DD_LV_days);
            aDaysAdapter = new DaysAdapter(Runsheet.this, Day.values());
            mDriverViews.setAdapter(aDaysAdapter);

            mDriverViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Runsheet.Day day = aDaysAdapter.getItem(position);
                    mDays.setText(day.toString());
                    selectedDay = day.stringValue;
                    Log.i(TAG, "" + selectedDay);
                    alert.dismiss();
                }
            });
            alert.show();
        } else if (v.getId() == mSend.getId()) {
            if (selectedDay.isEmpty()) {
                Snackbar.make(findViewById(R.id.ARS_LL_parent), "Please select day of the week", Snackbar.LENGTH_SHORT).show();
            } else if (mState.getText().toString().isEmpty()) {
                Snackbar.make(findViewById(R.id.ARS_LL_parent), "Please select place", Snackbar.LENGTH_SHORT).show();
            } else if (!isNetworkAvailable(this)) {
                Snackbar.make(findViewById(R.id.ARS_LL_parent), getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show();
            } else {
                Intent mCallRunsheets = new Intent(Runsheet.this, RunsheetLists.class);
                mCallRunsheets.putExtra(Constants.WEEK_DAY, selectedDay);
                mCallRunsheets.putExtra(Constants.LOCATION, selectedId);
                startActivity(mCallRunsheets);
            }
        }
    }
}
