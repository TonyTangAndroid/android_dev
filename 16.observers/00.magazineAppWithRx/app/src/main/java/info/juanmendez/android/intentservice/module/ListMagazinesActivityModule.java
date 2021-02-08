package info.juanmendez.android.intentservice.module;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.ui.ListMagazinesActivity;

/**
 * Created by Juan on 7/29/2015.
 */
@Module
public abstract class ListMagazinesActivityModule {

  @Binds
  @ActivityScope
  public abstract AppCompatActivity appCompatActivity(ListMagazinesActivity activity);

  @Binds
  @ActivityScope
  public abstract Activity Activity(ListMagazinesActivity activity);

  @ActivityScope
  @Provides
  public static ConnectivityManager providesConnectivityManager(Activity activity) {
    return (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  @Provides
  @ActivityScope
 static public BriteContentResolver providesContentResolver(Activity activity, SqlBrite sqlBrite) {

    return sqlBrite.wrapContentProvider(activity.getContentResolver());
  }
}