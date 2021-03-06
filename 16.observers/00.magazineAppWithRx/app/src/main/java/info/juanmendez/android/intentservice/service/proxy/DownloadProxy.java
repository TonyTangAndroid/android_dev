package info.juanmendez.android.intentservice.service.proxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import info.juanmendez.android.intentservice.module.ActivityScope;
import info.juanmendez.android.intentservice.service.download.DownloadService;
import javax.inject.Inject;

/**
 * The intent to invoke service wraps an instance of this class. That same service is able to get
 * the object from the intent received, and is able to call send method which is handled by
 * onReceiveResult.
 *
 * <p>Having a reference of our callback, we can call a specific method.
 */
@ActivityScope
public class DownloadProxy extends ResultReceiver {

  private final Activity activity;

  protected UiCallback callback;

  @Inject
  public DownloadProxy(Activity activity) {
    super(new Handler());
    this.activity = activity;
  }

  public void startService(UiCallback callback) {

    this.callback = callback;
    Intent i = new Intent(activity, DownloadService.class);
    i.putExtra("receiver", this);
    activity.startService(i);
  }

  public interface UiCallback {

    void onDownloadResult(int resultCode);
  }

  @Override
  protected void onReceiveResult(int resultCode, Bundle resultData) {

    super.onReceiveResult(resultCode, resultData);

    callback.onDownloadResult(resultCode);
  }
}
