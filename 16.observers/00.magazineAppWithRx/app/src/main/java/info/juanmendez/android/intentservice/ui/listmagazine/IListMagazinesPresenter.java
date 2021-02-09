package info.juanmendez.android.intentservice.ui.listmagazine;

import info.juanmendez.android.intentservice.service.network.NetworkUpdate;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;

/** Created by Juan on 8/19/2015. */
public interface IListMagazinesPresenter extends NetworkUpdate, DownloadProxy.UiCallback {

  void pause();

  void resume();

  void loadMagazine();

  void refreshList(Boolean forceQuery);
}
