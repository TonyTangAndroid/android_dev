package info.juanmendez.android.intentservice.ui.magazine;

import android.database.Cursor;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;
import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.helper.PageUtil;
import info.juanmendez.android.intentservice.model.adapter.WebViewAdapter;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;
import info.juanmendez.android.intentservice.service.provider.table.SQLPage;
import info.juanmendez.android.intentservice.ui.MagazinePage;
import java.util.ArrayList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/** Created by Juan on 8/20/2015. */
public class MagazinePresenter {

  private final AppCompatActivity activity;

  private final ArrayList<MagazinePage> pageList;

  private final MagazineDispatcher dispatcher;

  private final BriteContentResolver briteContentResolver;

  Observable<SqlBrite.Query> queryObservable;

  WebViewAdapter adapter;

  public MagazinePresenter(
      AppCompatActivity activity,
      ArrayList<MagazinePage> pageList,
      MagazineDispatcher dispatcher,
      BriteContentResolver briteContentResolver) {
    this.activity = activity;

    this.pageList = pageList;
    this.dispatcher = dispatcher;
    this.briteContentResolver = briteContentResolver;
  }

  public void pause() {

    if (queryObservable != null) queryObservable.unsubscribeOn(AndroidSchedulers.mainThread());
  }

  public void resume() {
    Magazine mag = dispatcher.getMagazine();

    if (mag.getId() > 0 && pageList.size() == 0) startLoader(mag);
    else adapter.notifyDataSetChanged();
  }

  private void startLoader(Magazine magazine) {
    Uri uri =
        Uri.parse(
            "content://" + BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider/pages");

    queryObservable =
        briteContentResolver.createQuery(
            uri,
            new String[] {SQLPage.ID, SQLPage.POSITION, SQLPage.NAME, SQLPage.MAG_ID},
            SQLPage.MAG_ID + " = ?",
            new String[] {Integer.toString(magazine.getId())},
            null,
            false);

    queryObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(
            query -> {
              ArrayList<MagazinePage> list = new ArrayList<>();

              Cursor cursor = query.run();
              while (cursor.moveToNext()) {
                list.add(PageUtil.fromCursor(magazine, cursor));
              }

              cursor.close();
              return list;
            })
        .subscribe(adapter);
  }
}
