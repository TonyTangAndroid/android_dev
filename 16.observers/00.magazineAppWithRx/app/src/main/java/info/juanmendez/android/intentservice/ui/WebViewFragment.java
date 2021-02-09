package info.juanmendez.android.intentservice.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * WebViewFragment support for android.support.v4.app.Fragment This demo has been tested for SDK 10
 * and 22. @Author @juanmendezinfo
 *
 * @see <a href="http://www.juanmendez.info/2015/09/webviewfragment-which-supports.html">details</a>
 */
public class WebViewFragment extends Fragment {
  private WebView webView;
  private boolean mIsWebViewAvailable;
  private boolean mRotated = false;

  @Override
  public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    setRetainInstance(true);

    if (webView == null) {
      webView = new WebView(getActivity());
    }

    return webView;
  }

  /**
   * let us know if the webView has been rotated.
   *
   */
  public boolean rotated() {
    return mRotated;
  }

  /** Called when the fragment is visible to the user and actively running. Resumes the WebView. */
  @Override
  public void onPause() {
    super.onPause();

    webView.onPause();

    mRotated = true;
  }

  /** Called when the fragment is no longer resumed. Pauses the WebView. */
  @Override
  public void onResume() {
    webView.onResume();
    super.onResume();
  }

  /**
   * Called when the WebView has been detached from the fragment. The WebView is no longer available
   * after this time.
   */
  @Override
  public void onDestroyView() {
    mIsWebViewAvailable = false;

    if (webView != null) {
      ViewGroup parentViewGroup = (ViewGroup) webView.getParent();
      if (parentViewGroup != null) {
        parentViewGroup.removeView(webView);
      }
    }

    super.onDestroyView();
  }

  /** Called when the fragment is no longer in use. Destroys the internal state of the WebView. */
  @Override
  public void onDestroy() {
    if (webView != null) {
      webView.destroy();
      webView = null;
    }
    super.onDestroy();
  }

  /** Gets the WebView. */
  public WebView getWebView() {
    return mIsWebViewAvailable ? webView : null;
  }
}
