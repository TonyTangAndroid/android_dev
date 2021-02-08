package info.juanmendez.android.intentservice.ui;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Juan on 8/24/2015.
 */
public class SettingsHolderActivity extends AppCompatActivity
{
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment() ).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}