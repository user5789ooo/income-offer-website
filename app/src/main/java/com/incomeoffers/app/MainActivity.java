package com.incomeoffers.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);

        setContentView(webView);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(false);

        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        webView.setWebChromeClient(
            new WebChromeClient()
        );

        webView.setWebViewClient(
            new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(
                        WebView view,
                        WebResourceRequest request) {

                    Uri uri = request.getUrl();

                    String scheme =
                            uri.getScheme();

                    if (
                        scheme != null &&
                        (
                            scheme.equals("http") ||
                            scheme.equals("https")
                        )
                    ) {

                        view.loadUrl(
                            uri.toString()
                        );

                        return true;
                    }

                    try {

                        Intent intent =
                            new Intent(
                                Intent.ACTION_VIEW,
                                uri
                            );

                        startActivity(intent);

                    } catch (Exception ignored) {
                    }

                    return true;
                }

                @Override
                public boolean shouldOverrideUrlLoading(
                        WebView view,
                        String url) {

                    if (
                        url.startsWith("http://") ||
                        url.startsWith("https://")
                    ) {

                        view.loadUrl(url);

                    } else {

                        try {

                            Intent intent =
                                new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(url)
                                );

                            startActivity(intent);

                        } catch (Exception ignored) {
                        }
                    }

                    return true;
                }
            }
        );

        /*
         * Website files will be copied into
         * Android app assets during the
         * GitHub Actions build.
         */

        webView.loadUrl(
            "file:///android_asset/www/index.html"
        );
    }

    @Override
    public void onBackPressed() {

        if (
            webView != null &&
            webView.canGoBack()
        ) {

            webView.goBack();

        } else {

            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {

        if (webView != null) {

            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.destroy();
        }

        super.onDestroy();
    }
}
