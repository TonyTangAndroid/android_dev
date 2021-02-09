package info.juanmendez.android.intentservice.ui;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import info.juanmendez.android.intentservice.module.ActivityInjectorModule;
import info.juanmendez.android.intentservice.module.AppModule;
import info.juanmendez.android.intentservice.service.download.DownloadService;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;
import javax.inject.Singleton;

/** Created by Juan on 8/1/2015. */
@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class, ActivityInjectorModule.class})
public interface AppComponent extends AndroidInjector<MagazineApp> {

  void inject(DownloadService instance);

  void inject(MagazineListService instance);

  void inject(MagazineRow instance);

  @Component.Factory
  interface Factory {

    AppComponent appComponent(@BindsInstance Application application);
  }
}
