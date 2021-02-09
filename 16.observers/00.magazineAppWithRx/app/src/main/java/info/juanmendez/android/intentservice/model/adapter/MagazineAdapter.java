package info.juanmendez.android.intentservice.model.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.module.ActivityScope;
import info.juanmendez.android.intentservice.ui.MagazineRow;
import java.util.List;
import javax.inject.Inject;
import rx.functions.Action1;

/**
 * Created by Juan on 8/1/2015.
 */
@ActivityScope
public class MagazineAdapter extends ArrayAdapter<Magazine> implements Action1<List<Magazine>> {

  private final List<Magazine> magazines;

  @Inject
  public MagazineAdapter(Activity context, List<Magazine> list) {
    super(context, 0, list);
    magazines = list;
  }

  @Override
  public Magazine getItem(int position) {
    return magazines.get(position);
  }

  @Override
  public int getPosition(Magazine item) {
    return magazines.indexOf(item);
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).getId();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    convertView = MagazineRow.inflate(parent);

    Magazine magazine = getItem(position);
    ((MagazineRow) convertView).setItem(magazine);

    return convertView;
  }

  @Override
  public int getCount() {
    return magazines.size();
  }

  @Override
  public void call(List<Magazine> list) {

    magazines.clear();
    magazines.addAll(list);
    this.notifyDataSetChanged();
  }
}
