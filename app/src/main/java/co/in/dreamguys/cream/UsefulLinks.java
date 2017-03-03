package co.in.dreamguys.cream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.woxthebox.draglistview.DragListView;

/**
 * Created by user5 on 02-03-2017.
 */

public class UsefulLinks extends AppCompatActivity {

    DragListView mDragListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useful);

        initWidget();
    }

    private void initWidget() {
        mDragListView = (DragListView) findViewById(R.id.drag_list_view);
        mDragListView.setLayoutManager(new LinearLayoutManager(this));

//        UsefulLinksAdapter aUsefulLinksAdapter = new UsefulLinksAdapter(UsefulLinks.this);

//        ItemAdapter listAdapter = new ItemAdapter(mItemArray, R.layout.list_item, R.id.image, false);
//        mDragListView.setAdapter(listAdapter);
//        mDragListView.setCanDragHorizontally(false);
    }
}
