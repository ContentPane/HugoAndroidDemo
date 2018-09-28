package com.hugo.pagerplayer.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.hugo.pagerplayer.R;
import com.hugo.pagerplayer.adapter.StringAdapter;
import com.hugo.pagerplayer.view.PermissionChecker;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> items = new ArrayList<>();

    StringAdapter stringAdapter;

    private static final String[] DEFAULT_PLAYBACK_DOMAIN_ARRAY = {
            "img.tianyav.com",
            "v6.bbobo.com",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        mListView = (ListView) findViewById(R.id.lv);

        for (int i = 0; i < 20; i++) {
            items.add("position==" + i);
        }
        stringAdapter = new StringAdapter(this, items);

        if(isPermissionOK()) {
            mListView.setAdapter(stringAdapter);
        }


    }

    public boolean isPermissionOK() {
        PermissionChecker checker = new PermissionChecker(this);
        boolean isPermissionOK = Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checker.checkPermission();
        if (!isPermissionOK) {
            Toast.makeText(this, "Some permissions is not approved !!!", Toast.LENGTH_SHORT).show();
        }
        return isPermissionOK;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
