package info.juanmendez.android.intentservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.viewpager.widget.ViewPager;
import dagger.android.support.DaggerAppCompatActivity;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.model.adapter.WebViewAdapter;
import info.juanmendez.android.intentservice.ui.magazine.MagazinePresenter;
import javax.inject.Inject;

public class MagazineActivity extends DaggerAppCompatActivity     {

    ViewPager viewPager;

 @Inject
 MagazinePresenter presenter;

 @Inject WebViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);
        viewPager = findViewById(R.id.pager);
         viewPager.setAdapter(adapter);

    }




    @Override
    public void onResume(){
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause(){
        super.onPause();
        presenter.pause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if( id == R.id.action_settings ){
            Intent i = new Intent( this, SettingsHolderActivity.class );
            startActivity( i );
        }

        return super.onOptionsItemSelected(item);
    }
}