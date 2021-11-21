package com.asi.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String []items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.showMusic);
        runTimePermission();


    }
    public void runTimePermission(){
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displaySong();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> findSongs(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        try {

            for(File singleFile : files) {

                if ((singleFile.isDirectory() && !singleFile.isHidden())) {

                    arrayList.addAll(findSongs(singleFile));

                }else {

                    if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {
                        arrayList.add(singleFile);

                    }

                }

            }

        }catch (Exception e){

            e.printStackTrace();

        }
        return arrayList;
    }

    public void displaySong(){
        final ArrayList<File> allSongs = findSongs(Environment.getExternalStorageDirectory());
        items = new String[allSongs.size()];

        for(int i = 0; i < allSongs.size(); i++){
            items[i] = allSongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
        }

//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(myAdapter);

        customAdapter adapter = new customAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = (String) listView.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(), activity_player.class)
                .putExtra("songs", allSongs)
                .putExtra("songName", songName)
                .putExtra("pos", position));
            }
        });

    }


    class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = getLayoutInflater().inflate(R.layout.item_view, null);
            TextView songName = myView.findViewById(R.id.textSong);
            songName.setSelected(true);
            songName.setText(items[position]);
            return myView;
        }
    }
}