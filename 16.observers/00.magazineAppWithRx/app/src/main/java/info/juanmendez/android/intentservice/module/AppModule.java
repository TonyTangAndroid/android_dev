package info.juanmendez.android.intentservice.module;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.model.pojo.Log;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.MagazineNotificationSubject;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;
import info.juanmendez.android.intentservice.ui.MagazinePage;
import java.util.ArrayList;
import javax.inject.Singleton;

@Module
public class AppModule {


  @Provides
  @Singleton
  MagazineDispatcher providesMagazineDispatcher() {
    return new MagazineDispatcher();
  }

  @Provides
  @Singleton
  MagazineNotificationSubject providesMagazineNotificationSubject() {
    return new MagazineNotificationSubject();
  }

  @Provides
  @Singleton
  ArrayList<MagazinePage> providesPages() {
    return new ArrayList<MagazinePage>();
  }

  @Provides
  @Singleton
  ArrayList<Magazine> provideMagazines() {
    return new ArrayList<Magazine>();
  }

  @Provides
  @Singleton
  Log providesLog() {
    return new Log();
  }

  @Provides
  NotificationManager providesNotificationManager(Application application) {
    return (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
  }

  @Provides
  @Singleton
  SqlBrite providesSqlBrite() {
    return SqlBrite.create();
  }
}
