package info.juanmendez.android.intentservice.ui;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.service.download.DownloadService;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;

/**
 * Created by Juan on 8/1/2015.
 */
public class MagazineApp extends DaggerApplication {


  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    appComponent = DaggerAppComponent.factory().appComponent(this);
    return appComponent;
  }

  public String getLocalhost() {
    return getResources().getString(R.string.localhost);
  }

  public void inject(MagazineRow magazineRow) {
    appComponent.inject(magazineRow);
  }

  public void inject(DownloadService downloadService) {
    appComponent.inject(downloadService);

  }

  public void inject(MagazineListService magazineListService) {
    appComponent.inject(magazineListService);

  }
}
