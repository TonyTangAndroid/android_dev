package info.juanmendez.android.intentservice.module;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.ui.ListMagazinesActivity;
import info.juanmendez.android.intentservice.ui.MagazineActivity;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.model.adapter.WebViewAdapter;
import info.juanmendez.android.intentservice.ui.listmagazine.ListMagazinesPresenter;
import info.juanmendez.android.intentservice.ui.magazine.MagazinePresenter;

/**
 * Created by Juan on 7/29/2015.
 */
@Module(
injects = {
        ListMagazinesActivity.class,
        ListMagazinesPresenter.class,
        MagazineActivity.class,
        WebViewAdapter.class,
        MagazinePresenter.class
},
        addsTo = AppModule.class,
        library = true
)
public class ActivityModule
{
    private final AppCompatActivity activity;

    public ActivityModule( AppCompatActivity activity ){
        this.activity = activity;
    }

    @Provides
    @Singleton
    public AppCompatActivity activityProvider()
    {
        return this.activity;
    }

    @Provides
    public DownloadProxy providesProxy(AppCompatActivity activity )
    {
        return new DownloadProxy(activity);
    }

    @Provides
    public ConnectivityManager providesConnectivityManager(){
        return (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    public BriteContentResolver providesContentResolver( SqlBrite sqlBrite ){

        return sqlBrite.wrapContentProvider( activity.getContentResolver() );
    }
}