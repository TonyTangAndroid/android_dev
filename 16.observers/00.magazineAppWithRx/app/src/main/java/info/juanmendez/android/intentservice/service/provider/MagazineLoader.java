package info.juanmendez.android.intentservice.service.provider;

import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.helper.MagazineUtil;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.service.provider.table.SQLMagazine;
import java.util.ArrayList;

/**
 * Created by Juan on 8/2/2015.
 *
 * <p>This provider has been replaced for this Rx version with SqlBrite's contentProvider
 * observable. This file will stay as being just legacy of the growth of this project.
 */
@Deprecated
public class MagazineLoader extends AsyncTaskLoader<ArrayList<Magazine>> {

  ArrayList<Magazine> magazines = new ArrayList<Magazine>();
  ContentResolver resolver;

  public MagazineLoader(Context context) {
    super(context);

    resolver = context.getContentResolver();
  }

  @Override
  protected void onStartLoading() {
    super.onStartLoading();

    if (!magazines.isEmpty()) {
      deliverResult(magazines);
    } else {
      forceLoad();
    }
  }

  @Override
  public ArrayList<Magazine> loadInBackground() {

    magazines.clear();

    Uri uri =
        Uri.parse(
            "content://"
                + BuildConfig.APPLICATION_ID
                + ".service.provider.MagazineProvider/magazines/");
    Cursor query =
        resolver.query(
            uri,
            new String[] {
              SQLMagazine.ID,
              SQLMagazine.ISSUE,
              SQLMagazine.DATETIME,
              SQLMagazine.LOCATION,
              SQLMagazine.FILE_LOCATION,
              SQLMagazine.TITLE,
              SQLMagazine.STATUS
            },
            null,
            null,
            SQLMagazine.ISSUE + " desc");

    if (query != null) {
      while (query.moveToNext()) {
        magazines.add(MagazineUtil.fromCursor(query));
      }

      query.close();
    }

    return magazines;
  }

  @Override
  public void deliverResult(ArrayList<Magazine> magazines) {

    if (this.magazines == null) this.magazines = magazines;

    if (isStarted()) {
      super.deliverResult(magazines);
    }
  }
}
