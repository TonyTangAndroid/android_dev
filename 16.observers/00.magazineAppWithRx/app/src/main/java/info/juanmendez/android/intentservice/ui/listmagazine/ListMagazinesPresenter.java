package info.juanmendez.android.intentservice.ui.listmagazine;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;
import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;
import info.juanmendez.android.intentservice.helper.MagazineUtil;
import info.juanmendez.android.intentservice.helper.NetworkUtil;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.model.pojo.Log;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.MagazineNotificationSubject;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;
import info.juanmendez.android.intentservice.service.network.NetworkReceiver;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;
import info.juanmendez.android.intentservice.service.provider.table.SQLMagazine;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.ui.MagazineActivity;
import java.util.ArrayList;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Juan on 8/19/2015.
 *
 * <p>This presenter takes care to feed a list adapter and commands other services to pull new
 * magazines, store them on the db and finally query to fill the adapter and indirectly feed the
 * activity's list view
 */
public class ListMagazinesPresenter implements IListMagazinesPresenter {

  private final Activity activity;
  private final MagazineAdapter adapter;

  // pull new magazines from restful service
  // and populate db
  private final DownloadProxy downloadProxy;

  // detect connection and notify this presenter
  private final NetworkReceiver networkReceiver;

  private final Log log;

  private final ArrayList<Magazine> magazines;

  private final MagazineDispatcher magazineDispatcher;

  private final MagazineNotificationSubject magazineNotificationSubject;

  private final SqlBrite sqlBrite;

  Subscription magazineSubscription;
  Subscription notificationSubscription;

  private final BriteContentResolver briteContentResolver;
  Observable<SqlBrite.Query> queryObservable;

  private final IListMagazinesView view;

  @Inject
  public ListMagazinesPresenter(
      Activity activity,
      MagazineAdapter adapter,
      DownloadProxy downloadProxy,
      NetworkReceiver networkReceiver,
      Log log,
      ArrayList<Magazine> magazines,
      MagazineDispatcher magazineDispatcher,
      MagazineNotificationSubject magazineNotificationSubject,
      SqlBrite sqlBrite,
      BriteContentResolver briteContentResolver,
      IListMagazinesView view) {
    this.activity = activity;
    this.adapter = adapter;
    this.downloadProxy = downloadProxy;
    this.networkReceiver = networkReceiver;
    this.log = log;
    this.magazines = magazines;
    this.magazineDispatcher = magazineDispatcher;
    this.magazineNotificationSubject = magazineNotificationSubject;
    this.sqlBrite = sqlBrite;
    this.briteContentResolver = briteContentResolver;
    this.view = view;
  }

  public void resume() {

    magazineSubscription =
        magazineDispatcher.subscribe(
            magazine -> {
              switch (magazine.getStatus()) {
                case MagazineStatus.PENDING:
                  loadMagazine();
                  break;

                case MagazineStatus.READ:
                  Intent intent = new Intent(activity, MagazineActivity.class);
                  activity.startActivity(intent);
                  break;
              }
            });

    notificationSubscription =
        magazineNotificationSubject.subscribe(
            magazinesNotification -> {
              if (magazinesNotification.getResultCode() == Activity.RESULT_OK
                  && log.getState() == Log.Integer.DIRTY) {
                Toast.makeText(
                        activity,
                        "New magazines to download! " + magazinesNotification.getMagazines().size(),
                        Toast.LENGTH_LONG)
                    .show();
                startLoader();
              }
            });

    networkReceiver.register(this);
    networkReceiver.refresh();
    refreshList(false);
  }

  public void pause() {

    magazineDispatcher.unsubscribe(magazineSubscription);
    magazineNotificationSubject.unsubscribe(notificationSubscription);
    networkReceiver.unregister();

    if (queryObservable != null) queryObservable.unsubscribeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public void refreshList(Boolean aggressive) {

    if (aggressive && NetworkUtil.isConnected(activity)) {
      getLatestMagazines();
    } else {
      if (log.getState() == Log.Integer.CLEAN) {
        feedListView();
      } else {
        startLoader();
      }
    }
  }

  /**
   * v.01 we want to detect and add new magazines to the list before we do the query
   *
   * <p>v.02 we want to detect connection to do v.01 otherwise just do the query and populate any
   * magazine listing left
   */
  private void getLatestMagazines() {

    if (NetworkUtil.isConnected(activity)) {
      Intent i = new Intent(activity, MagazineListService.class);
      activity.startService(i);
    } else {
      startLoader();
    }
  }

  private void startLoader() {

    Uri uri = Uri.parse("content://" + MagazineProvider.AUTHORITY + "/magazines");

    queryObservable =
        briteContentResolver.createQuery(
            uri,
            new String[] {
              SQLMagazine.ID,
              SQLMagazine.ISSUE,
              SQLMagazine.TITLE,
              SQLMagazine.LOCATION,
              SQLMagazine.FILE_LOCATION,
              SQLMagazine.DATETIME,
              SQLMagazine.STATUS
            },
            null,
            null,
            SQLMagazine.ISSUE + " desc",
            false);

    queryObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(
            query -> {
              ArrayList<Magazine> list = new ArrayList<>();

              Cursor cursor = query.run();
              while (cursor.moveToNext()) {
                list.add(MagazineUtil.fromCursor(cursor));
              }

              return list;
            })
        .subscribe(adapter);
  }

  @Override
  public void loadMagazine() {
    downloadProxy.startService(this);
  }

  @Override
  public void onNetworkStatus(Boolean connected, String type) {

    view.onNetworkStatus(connected, type);
  }

  @Override
  public void onDownloadResult(int resultCode) {
    adapter.notifyDataSetChanged();
  }

  private void feedListView() {
    adapter.addAll(magazines);
    adapter.notifyDataSetChanged();
    view.onMagazineList();
  }
}
