package info.juanmendez.android.intentservice.ui.listmagazine;

import info.juanmendez.android.intentservice.service.network.NetworkUpdate;

/** Created by Juan on 8/19/2015. */
public interface IListMagazinesView extends NetworkUpdate {
  void onMagazineList();
}
