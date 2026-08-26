package com.incomeoffers.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

        settings.setLoadWithOverviewMode(false);
        settings.setUseWideViewPort(false);

        /*
         * ==================================================
         * WEBVIEW NAVIGATION
         * ==================================================
         *
         * Local website pages:
         * file:///android_asset/www/...
         *
         * এগুলো WebView-এর ভিতরেই চলবে।
         *
         * External http/https:
         * Browser-এ খুলবে।
         */

        webView.setWebViewClient(
            new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(
                        WebView view,
                        WebResourceRequest request) {

                    Uri uri = request.getUrl();

                    String scheme =
                        uri.getScheme();

                    /*
                     * LOCAL APP FILE
                     *
                     * index.html
                     * offer.html
                     * history.html
                     * profile.html
                     * offer-detail.html
                     *
                     * সব WebView-এর ভিতরে খুলবে।
                     */

                    if (
                        "file".equalsIgnoreCase(scheme) ||
                        "content".equalsIgnoreCase(scheme)
                    ) {

                        return false;
                    }


                    /*
                     * EXTERNAL WEBSITE
                     */

                    if (
                        "http".equalsIgnoreCase(scheme) ||
                        "https".equalsIgnoreCase(scheme)
                    ) {

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


                    /*
                     * TELEGRAM / OTHER APP LINKS
                     */

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


                /*
                 * Compatibility for older Android WebView.
                 */

                @Override
                public boolean shouldOverrideUrlLoading(
                        WebView view,
                        String url) {

                    if (
                        url == null ||
                        url.trim().isEmpty()
                    ) {

                        return false;
                    }


                    /*
                     * Internal Android asset page.
                     */

                    if (
                        url.startsWith(
                            "file:///android_asset/"
                        )
                    ) {

                        return false;
                    }


                    /*
                     * Normal HTTP / HTTPS link.
                     */

                    if (
                        url.startsWith("http://") ||
                        url.startsWith("https://")
                    ) {

                        try {

                            Intent intent =
                                new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(url)
                                );

                            startActivity(intent);

                        } catch (Exception ignored) {
                        }

                        return true;
                    }


                    /*
                     * Any other local URL.
                     */

                    return false;
                }
            }
        );


        /*
         * ==================================================
         * CHROME / JAVASCRIPT
         * ==================================================
         */

        webView.setWebChromeClient(
            new WebChromeClient()
        );


        /*
         * ==================================================
         * START WEBSITE
         * ==================================================
         */

        webView.loadUrl(
            "file:///android_asset/www/index.html"
        );
    }


    /*
     * ==================================================
     * ANDROID BACK BUTTON
     * ==================================================
     */

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


    /*
     * ==================================================
     * CLEANUP
     * ==================================================
     */

    @Override
    protected void onDestroy() {

        if (webView != null) {

            webView.loadUrl("about:blank");

            webView.stopLoading();

            webView.clearHistory();

            webView.destroy();

            webView = null;
        }

        super.onDestroy();
    }
}
