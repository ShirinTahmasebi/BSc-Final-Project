package shirin.tahmasebi.mscfinalproject.inlineBrowser;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class InlineBrowserActivity extends MainActivity
        implements InlineBrowserPresenter.InlineBrowserView {
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = (WebView) findViewById(R.id.inlineBrowser_browser_webView);
        InlineBrowserPresenter mPresenter = new InlineBrowserPresenter(this);
        mPresenter.loadUrl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browser;
    }

    @Override
    protected int getActivityHelpHint() {
        return 0;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return 0;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void openUrl(String url) {
        mWebView.setWebViewClient(new InlineBrowser());
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        // TODO: Check XSS attack
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.loadUrl(url);
    }

    private class InlineBrowser extends WebViewClient {

        CircularProgressView progressView = (CircularProgressView)
                findViewById(R.id.inlineBrowser_loadingBar_circularProgressView);

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressView.stopAnimation();
            progressView.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
