package co.in.dreamguys.cream;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by user5 on 14-02-2017.
 */

public class Users extends AppCompatActivity implements View.OnClickListener {

    Button update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accident_report);

     /*   initviews();*/

    }

   /* public void initviews(){

        update = (Button) findViewById(R.id.AUAD_BT_update);
        update.setOnClickListener(this);
    }*/


    @Override
    public void onClick(View v) {
      /*  startActivity(new Intent(Users.this, EditUserAccounts.class));*/
    }
}
