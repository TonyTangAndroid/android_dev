package info.juanmendez.android.intentservice.model.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import info.juanmendez.android.intentservice.ui.MagazinePage;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by Juan on 8/8/2015.
 */
public class WebViewAdapter extends FragmentPagerAdapter implements Action1<List<MagazinePage>> {

  private final ArrayList<MagazinePage> list;


  public WebViewAdapter(FragmentManager fragmentManager,
      ArrayList<MagazinePage> list) {
    super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    this.list = list;

  }

  @Override
  public int getCount() {
    return list.size();
  }

  @NonNull
  @Override
  public Fragment getItem(int position) {
    return list.get(position);
  }

  @Override
  public void call(List<MagazinePage> list) {
    this.list.clear();
    this.list.addAll(list);
    notifyDataSetChanged();
  }
}
