package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import co.in.dreamguys.cream.utils.CustomProgressDialog;

/**
 * Created by user5 on 01-03-2017.
 */

public class AddCustomFields extends AppCompatActivity {
    Toolbar mToolbar;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = CustomFields.class.getName();
    EditText mNewFields;
    TextView mType;
    Button mAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_fields);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_add_custom_fields_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mNewFields = (EditText) findViewById(R.id.AACF_ET_new_fields);
        mType = (TextView) findViewById(R.id.AACF_ET_type);
        mAdd = (Button) findViewById(R.id.AACF_BT_add);
    }
}
