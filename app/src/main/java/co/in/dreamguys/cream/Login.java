package co.in.dreamguys.cream;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import co.in.dreamguys.cream.utils.Util;

/**
 * Created by user5 on 09-02-2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    /*CustomAdapter mCustomAdapter;
    ListView mListView;
    Button mSearch;
    */

    EditText email, password;
    Button login;
    TextView title;
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initviews();






      /*  mListView = (ListView) findViewById(R.id.lv_search_items);
        mSearch = (Button) findViewById(R.id.bt_search);
        mCustomAdapter = new CustomAdapter();
        mListView.setAdapter(mCustomAdapter);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Company.class));
            }
        });*/

    }


    public void initviews() {

        email = (EditText) findViewById(R.id.ALET_email);
        password = (EditText) findViewById(R.id.ALET_password);
        login = (Button) findViewById(R.id.ALBT_signin);
        title = (TextView) findViewById(R.id.ALTV_title);
        login.setOnClickListener(this);
        title.setText(getString(R.string.toolbar_title));
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));


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
        if (v.getId() == R.id.ALBT_signin) {


            if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                email.setError(getString(R.string.err_email));
                password.setError(getString(R.string.err_password));
                email.requestFocus();
                password.requestFocus();
            } else if (email.getText().toString().isEmpty()) {
                email.setError(getString(R.string.err_email));
                email.requestFocus();
            } else if (!Util.isValidEmail(email.getText().toString())) {
                email.setError(getString(R.string.err_emailInvalid));
                email.requestFocus();
            } else if (password.getText().toString().isEmpty()) {
                password.setError(getString(R.string.err_password));
                password.requestFocus();
            } else if (password.getText().toString().length() < 6) {
                password.setError(getString(R.string.err_password_length));
                password.requestFocus();
            } else if (!Util.isNetworkAvailable(this)) {
                Toast.makeText(Login.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }


        }
    }

   /* private class CustomAdapter extends BaseAdapter {


        LayoutInflater mInflater;

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.adapter_view_drivers, null);
            }
            return convertView;
        }
    }*/
}
