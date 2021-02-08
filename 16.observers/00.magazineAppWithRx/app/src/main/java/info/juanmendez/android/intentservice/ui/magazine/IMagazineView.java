package info.juanmendez.android.intentservice.ui.magazine;


import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by Juan on 8/20/2015.
 */
public interface IMagazineView {

    void inject( Object object );
    void setAdapter( PagerAdapter adapter );
}
