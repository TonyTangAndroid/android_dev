import static org.robolectric.Shadows.shadowOf;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;
import info.juanmendez.android.intentservice.service.proxy.MagazineListProxy;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowContentResolver;
import org.robolectric.shadows.ShadowLog;

@RunWith(RobolectricTestRunner.class)
public class MagazineTest {

  static {
    ShadowLog.stream = System.out;
  }

  @Before
  public void init() {

    ContentResolver resolver = RuntimeEnvironment.application.getContentResolver();
    ShadowContentResolver shadowResolver = shadowOf(resolver);
  }

  @Test
  public void testMagazineListService() {
    MagazineListProxyMock mock = new MagazineListProxyMock();
    mock.startService(
        new MagazineListProxy.UiCallBack() {
          @Override
          public void onMagazineListResult(int resultCode) {
            Assert.assertTrue(
                "magazine list is up to date in content provider!",
                resultCode == Activity.RESULT_OK);
          }
        });
  }

  public class MagazineListProxyMock extends MagazineListProxy {

    public void startService(UiCallBack callback) {

      this.callback = callback;
      Intent intent = new Intent(RuntimeEnvironment.application, MagazineListServiceMock.class);
      intent.putExtra("receiver", this);

      MagazineListServiceMock service = new MagazineListServiceMock();
      service.onCreate();
      service.onHandleIntent(intent);
    }
  }

  public class MagazineListServiceMock extends MagazineListService {

    public void onHandleIntent(Intent intent) {
      super.onHandleIntent(intent);
    }
  }
}
