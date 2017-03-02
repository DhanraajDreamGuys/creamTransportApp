package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import co.in.dreamguys.cream.adapter.UseFulLinkAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.UsefulLinksAPI;
import co.in.dreamguys.cream.interfaces.FridgeCodeTypeInterface;
import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.DragListener;
import co.in.dreamguys.cream.utils.DragNDropListView;
import co.in.dreamguys.cream.utils.DropListener;
import co.in.dreamguys.cream.utils.RemoveListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by Dhanraaj on 3/2/2017.
 */
public class UsefulLinks extends AppCompatActivity implements FridgeCodeTypeInterface {
    Toolbar mToolbar;
    DragNDropListView mDragNDropListView;
    CustomProgressDialog mCustomProgressDialog;
    UseFulLinkAdapter aUseFulLinkAdapter;
    public static String TAG = UsefulLinks.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useful_links);
        Constants.USEFULLINK = this;
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

        getUseFulLinks();

    }

    private void getUseFulLinks() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(UsefulLinks.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UsefulLinksAPI.UsefulLinksResponse> loginCall = apiService.getUsefulLinks();

            loginCall.enqueue(new Callback<UsefulLinksAPI.UsefulLinksResponse>() {
                @Override
                public void onResponse(Call<UsefulLinksAPI.UsefulLinksResponse> call, Response<UsefulLinksAPI.UsefulLinksResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        fillsUsefulLinks(response.body().getData());
                    } else {
                        Toast.makeText(UsefulLinks.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UsefulLinksAPI.UsefulLinksResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });

        }


    }

    private void fillsUsefulLinks(List<UsefulLinksAPI.Datum> data) {
        aUseFulLinkAdapter = new UseFulLinkAdapter(UsefulLinks.this, data);
        mDragNDropListView.setAdapter(aUseFulLinkAdapter);
        aUseFulLinkAdapter.notifyDataSetChanged();
        if (mDragNDropListView instanceof DragNDropListView) {
            ((DragNDropListView) mDragNDropListView).setDropListener(mDropListener);
            ((DragNDropListView) mDragNDropListView).setRemoveListener(mRemoveListener);
            ((DragNDropListView) mDragNDropListView).setDragListener(mDragListener);
        }
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_useful_links_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mDragNDropListView = (DragNDropListView) findViewById(R.id.AUSL_DLV_useful_list);
    }

    private DropListener mDropListener =
            new DropListener() {
                public void onDrop(int from, int to) {
                    if (aUseFulLinkAdapter instanceof UseFulLinkAdapter) {
                        ((UseFulLinkAdapter) aUseFulLinkAdapter).onDrop(from, to);
                        mDragNDropListView.invalidateViews();
                    }
                }
            };

    private RemoveListener mRemoveListener =
            new RemoveListener() {
                public void onRemove(int which) {
                    if (aUseFulLinkAdapter instanceof UseFulLinkAdapter) {
                        ((UseFulLinkAdapter) aUseFulLinkAdapter).onRemove(which);
                        mDragNDropListView.invalidateViews();
                    }

                }
            };

    private DragListener mDragListener =
            new DragListener() {
                int defaultBackgroundColor;

                public void onDrag(int x, int y, ListView listView) {
                    // TODO Auto-generated method stub
                }

                public void onStartDrag(View itemView) {
                    itemView.setVisibility(View.INVISIBLE);
                    defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
                    itemView.setBackgroundColor(defaultBackgroundColor);
                    LinearLayout mCardView = (LinearLayout) itemView.findViewById(R.id.AUL_LL_useful_links);
                    if (mCardView != null) mCardView.setVisibility(View.VISIBLE);
                }

                public void onStopDrag(View itemView) {
                    itemView.setVisibility(View.VISIBLE);
                    itemView.setBackgroundColor(defaultBackgroundColor);
                    LinearLayout mCardView = (LinearLayout) itemView.findViewById(R.id.AUL_LL_useful_links);
                    if (mCardView != null) mCardView.setVisibility(View.VISIBLE);
                }

            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_trips_hours, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_add_trip_hours) {
            ActivityConstants.callPage(UsefulLinks.this, AddUsefulLinks.class);
        }
        return true;
    }

    @Override
    public void typeSearch(String type) {

    }

    @Override
    public void refresh() {
        getUseFulLinks();
    }
}
