package co.in.dreamguys.cream;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by user5 on 10-02-2017.
 */

public class Company extends AppCompatActivity {

    FloatingActionButton mEditCompanyRecords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        mEditCompanyRecords = (FloatingActionButton) findViewById(R.id.fab_edit_company);
        mEditCompanyRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Company.this, EditCompany.class));
            }
        });
    }
}
