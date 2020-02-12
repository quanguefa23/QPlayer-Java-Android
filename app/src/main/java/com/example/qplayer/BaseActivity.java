package com.example.qplayer;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    //function to create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        setTitle("QMP");
        return super.onCreateOptionsMenu(menu);
    }

    //config option item selection in options menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Search function is being developed", Toast.LENGTH_LONG).show();
                return true;
            case R.id.settings:
                Toast.makeText(this, "Settings function is being developed", Toast.LENGTH_LONG).show();
                return true;
            case R.id.contact:
                Toast.makeText(this, "Contact function is being developed", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
