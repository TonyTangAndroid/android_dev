package info.juanmendez.android.intentservice.service.download;

import info.juanmendez.android.intentservice.helper.rx.SubscriptionShell;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 11/28/2015.
 */
@Singleton
public class MagazineDispatcher extends SubscriptionShell<Magazine>
{
    Magazine magazine = new Magazine();

    @Inject
    public MagazineDispatcher(){
        super(PublishSubject.create());
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
        subject.onNext( magazine );
    }
}
