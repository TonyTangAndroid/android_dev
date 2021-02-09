import static org.robolectric.Shadows.shadowOf;

import android.content.ContentResolver;
import android.content.pm.ProviderInfo;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;
import info.juanmendez.android.intentservice.ui.ListMagazinesActivity;
import info.juanmendez.android.intentservice.ui.MagazineApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowContentResolver;
import org.robolectric.shadows.ShadowLog;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadProxy {
  private ContentResolver resolver;
  private ShadowContentResolver shadowResolver;
  private MagazineProvider provider;
  private MagazineApp app;

  static {
    ShadowLog.stream = System.out;
  }

  @Before
  public void prep() {

    app = (MagazineApp) RuntimeEnvironment.application;
    provider = new MagazineProvider();
    resolver = RuntimeEnvironment.application.getContentResolver();
    shadowResolver = shadowOf(resolver);
    provider.onCreate();

    ProviderInfo info = new ProviderInfo();
    info.authority = MagazineProvider.AUTHORITY;
    Robolectric.buildContentProvider(MagazineProvider.class).create(info);
  }

  @Test
  public void testProxyMock() {

    ActivityController controller =
        Robolectric.buildActivity(ListMagazinesActivity.class).create().start();
    ListMagazinesActivity activity = (ListMagazinesActivity) controller.get();
  }
}
