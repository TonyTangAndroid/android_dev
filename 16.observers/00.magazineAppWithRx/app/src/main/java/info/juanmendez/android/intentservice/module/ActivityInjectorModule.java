package info.juanmendez.android.intentservice.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import info.juanmendez.android.intentservice.service.download.DownloadService;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;
import info.juanmendez.android.intentservice.ui.ListMagazinesActivity;

/** Created by Juan on 7/29/2015. */
@Module
public abstract class ActivityInjectorModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = ListMagazinesActivityModule.class)
  abstract ListMagazinesActivity listMagazinesActivity();

  @ActivityScope
  @ContributesAndroidInjector
  abstract DownloadService DownloadService();

  @ActivityScope
  @ContributesAndroidInjector
  abstract MagazineListService magazineListService();
}
