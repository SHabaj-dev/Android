package com.asi.allchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity_Chat extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabChat, tabStatus, tabCall;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        tabLayout = findViewById(R.id.tabToolbar);
        tabChat = findViewById(R.id.chats);
        tabStatus = findViewById(R.id.status);
        tabCall = findViewById(R.id.calls);

        toolbar = findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);

    }
}