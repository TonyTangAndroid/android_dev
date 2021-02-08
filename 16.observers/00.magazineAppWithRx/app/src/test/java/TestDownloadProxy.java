import android.content.ContentResolver;

import android.content.pm.ProviderInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;
import org.robolectric.shadows.ShadowLog;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.ui.ListMagazinesActivity;
import info.juanmendez.android.intentservice.ui.MagazineApp;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21 )
public class TestDownloadProxy
{
    private ContentResolver resolver;
    private ShadowContentResolver shadowResolver;
    private MagazineProvider provider;
    private MagazineApp app;

    static{
        ShadowLog.stream = System.out;
    }

    @Before
    public void prep(){

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
    public void testProxyMock(){

        ActivityController controller = Robolectric.buildActivity(ListMagazinesActivity.class).create().start();
        ListMagazinesActivity activity = (ListMagazinesActivity) controller.get();
    }
}
