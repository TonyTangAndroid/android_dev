package info.juanmendez.android.intentservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import dagger.android.support.DaggerAppCompatActivity;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.helper.NetworkUtil;
import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.ui.listmagazine.IListMagazinesView;
import info.juanmendez.android.intentservice.ui.listmagazine.ListMagazinesPresenter;
import javax.inject.Inject;

/** Created by Juan on 7/29/2015. */
public class ListMagazinesActivity extends DaggerAppCompatActivity implements IListMagazinesView {

  ListView list;
  MagazineApp app;
  Button noNetworkButton;
  @Inject MagazineAdapter adapter;
  @Inject ListMagazinesPresenter presenter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    noNetworkButton = (Button) findViewById(R.id.noNetworkButton);

    noNetworkButton.setOnClickListener(
        v -> {
          if (NetworkUtil.isConnected(ListMagazinesActivity.this)) {
            presenter.refreshList(false);
            noNetworkButton.setVisibility(View.GONE);
          }
        });

    if (!NetworkUtil.isConnected(this)) {
      noNetworkButton.setVisibility(View.VISIBLE);
    }

    list = findViewById(R.id.list);
    list.setAdapter(adapter);

    app = (MagazineApp) getApplication();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.listmagazines_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_refresh) {
      presenter.refreshList(true);
      return true;
    } else if (id == R.id.action_settings) {
      Intent i = new Intent(this, SettingsHolderActivity.class);
      startActivity(i);
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onMagazineList() {}

  @Override
  public void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenter.pause();
  }

  @Override
  public void onNetworkStatus(Boolean connected, String type) {

    if (connected) {

      if (noNetworkButton.getVisibility() == View.VISIBLE) {
        noNetworkButton.setText(getString(R.string.error_network_refresh));
      }
    } else {
      noNetworkButton.setText(getString(R.string.error_network));
      noNetworkButton.setVisibility(View.VISIBLE);
    }
  }
}
