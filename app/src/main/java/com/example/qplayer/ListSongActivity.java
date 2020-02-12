package com.example.qplayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class ListSongActivity extends BaseActivity {

    ListView lv_song;
    ArrayList<Song> listSong;
    SongAdapter adapter;
    String playlist_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("State", "2 onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        //map listView and initial data
        lv_song = findViewById(R.id.lv_song);
        initialListSong();

        //initial adapter and adapt to listView
        adapter = new SongAdapter(ListSongActivity.this, R.layout.layout_song, listSong);
        lv_song.setAdapter(adapter);

        //show pop up menu if long click
        lv_song.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMenu(view, i);
                return false;
            }
        });

        //action if click
        lv_song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.select(i);
                adapter.notifyDataSetChanged();
                playSong(i);
            }
        });
    }

    int position = -1;
    private void playSong(int i) {
        position = i;
        Intent intent = new Intent(ListSongActivity.this, PlaySongActivity.class);
        intent.putExtra("playlist_name", playlist_name);
        intent.putExtra("position", i);
        intent.putParcelableArrayListExtra("listsong", listSong);
        startActivity(intent);
    }

    //change option bar title
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //change action bar title
        Intent intent = getIntent();
        playlist_name = intent.getStringExtra("playlist_name");
        setTitle(playlist_name);
        return true;
    }

    //function to create pop-up menu and config option item selection
    private void showMenu(View view,final int i) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_listsong, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.play:
                        playSong(i);
                        return true;
                    case R.id.moveup:
                        adapter.moveUp(i);
                        adapter.notifyDataSetChanged();
                        return true;
                    case R.id.movedown:
                        adapter.moveDown(i);
                        adapter.notifyDataSetChanged();
                        return true;
                    case R.id.delete:
                        confirmDelete(i);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void confirmDelete(final int id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm your action");
        alertDialog.setMessage("Do you want to delete item?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.remove(id);
                adapter.notifyDataSetChanged();
                Toast.makeText(ListSongActivity.this, "Delete successfully", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton("No", null);

        alertDialog.show();
    }

    private void initialListSong() {
        listSong = new ArrayList<>();
        listSong.add(new Song("Gucci Gang", "Lil Pump", "Lossless", R.drawable.guccigang, R.raw.guccigang));
        listSong.add(new Song("Something Just Like This", "The Chainsmokers; Coldplay", "Lossless", R.drawable.somethingjustlikethis, R.raw.somethingjustlikethis));
        listSong.add(new Song("Lullaby", "R3HAB; Mike Williams", "320kbps", R.drawable.lullaby, R.raw.lullaby));
        listSong.add(new Song("Gucci Gang", "Lil Pump", "Lossless", R.drawable.guccigang, R.raw.guccigang));
        listSong.add(new Song("Something Just Like This", "The Chainsmokers; Coldplay", "Lossless", R.drawable.somethingjustlikethis, R.raw.somethingjustlikethis));
        listSong.add(new Song("Lullaby", "R3HAB; Mike Williams", "320kbps", R.drawable.lullaby, R.raw.lullaby));
        listSong.add(new Song("Gucci Gang", "Lil Pump", "Lossless", R.drawable.guccigang, R.raw.guccigang));
        listSong.add(new Song("Something Just Like This", "The Chainsmokers; Coldplay", "Lossless", R.drawable.somethingjustlikethis, R.raw.somethingjustlikethis));
        listSong.add(new Song("Lullaby", "R3HAB; Mike Williams", "320kbps", R.drawable.lullaby, R.raw.lullaby));
        listSong.add(new Song("Gucci Gang", "Lil Pump", "Lossless", R.drawable.guccigang, R.raw.guccigang));
        listSong.add(new Song("Something Just Like This", "The Chainsmokers; Coldplay", "Lossless", R.drawable.somethingjustlikethis, R.raw.somethingjustlikethis));
        listSong.add(new Song("Lullaby", "R3HAB; Mike Williams", "320kbps", R.drawable.lullaby, R.raw.lullaby));
        listSong.add(new Song("Something Just Like This", "The Chainsmokers; Coldplay", "Lossless", R.drawable.somethingjustlikethis, R.raw.somethingjustlikethis));
        listSong.add(new Song("Lullaby", "R3HAB; Mike Williams", "320kbps", R.drawable.lullaby, R.raw.lullaby));
        listSong.add(new Song("Gucci Gang", "Lil Pump", "Lossless", R.drawable.guccigang, R.raw.guccigang));
        listSong.add(new Song("Something Just Like This", "The Chainsmokers; Coldplay", "Lossless", R.drawable.somethingjustlikethis, R.raw.somethingjustlikethis));
        listSong.add(new Song("Lullaby", "R3HAB; Mike Williams", "320kbps", R.drawable.lullaby, R.raw.lullaby));
        listSong.add(new Song("Gucci Gang", "Lil Pump", "Lossless", R.drawable.guccigang, R.raw.guccigang));
        listSong.add(new Song("Something Just Like This", "The Chainsmokers; Coldplay", "Lossless", R.drawable.somethingjustlikethis, R.raw.somethingjustlikethis));
        listSong.add(new Song("Lullaby", "R3HAB; Mike Williams", "320kbps", R.drawable.lullaby, R.raw.lullaby));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("State", "2 onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("State", "2 onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("State", "2 onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("State", "2 onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("State", "2 onStop");
    }
}
