package com.asi.allchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
        viewPager = findViewById(R.id.fragmentContainer);

        toolbar = findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_vert_24);
        toolbar.setOverflowIcon(drawable);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.userProfileMenu:
                Intent intent = new Intent(MainActivity_Chat.this, SettingsProfile.class);
                startActivity(intent);
                break;

            case R.id.settingsMenu:
                Toast.makeText(getApplicationContext(), "Settings Is Selected", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }
}