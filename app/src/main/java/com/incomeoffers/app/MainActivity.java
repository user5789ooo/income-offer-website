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

import androidx.webkit.WebViewAssetLoader;

public class MainActivity extends Activity {

    private WebView webView;
    private WebViewAssetLoader assetLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        webView = new WebView(this);

        setContentView(webView);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(false);

        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        settings.setLoadWithOverviewMode(false);
        settings.setUseWideViewPort(false);

        /*
         * Local website files are served through
         * a proper HTTPS origin instead of file://
         */

        assetLoader =
            new WebViewAssetLoader.Builder()
                .addPathHandler(
                    "/assets/",
                    new WebViewAssetLoader.AssetsPathHandler(this)
                )
                .build();


        /*
         * WebView navigation
         */

        webView.setWebViewClient(
            new WebViewClient() {

                @Override
                public android.webkit.WebResourceResponse
                shouldInterceptRequest(
                    WebView view,
                    WebResourceRequest request
                ) {

                    return assetLoader.shouldInterceptRequest(
                        request.getUrl()
                    );
                }


                @Override
                public boolean shouldOverrideUrlLoading(
                    WebView view,
                    WebResourceRequest request
                ) {

                    Uri uri = request.getUrl();

                    String url = uri.toString();

                    /*
                     * Website pages inside APK
                     */

                    if (
                        url.startsWith(
                            "https://appassets.androidplatform.net/assets/"
                        )
                    ) {

                        return false;
                    }


                    /*
                     * External HTTP / HTTPS links
                     */

                    if (
                        "http".equalsIgnoreCase(uri.getScheme()) ||
                        "https".equalsIgnoreCase(uri.getScheme())
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
                     * Telegram / WhatsApp / mail / other apps
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


                @Override
                public boolean shouldOverrideUrlLoading(
                    WebView view,
                    String url
                ) {

                    if (
                        url == null ||
                        url.trim().isEmpty()
                    ) {

                        return false;
                    }


                    if (
                        url.startsWith(
                            "https://appassets.androidplatform.net/assets/"
                        )
                    ) {

                        return false;
                    }


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
            }
        );


        /*
         * JavaScript / alerts / popup support
         */

        webView.setWebChromeClient(
            new WebChromeClient()
        );


        /*
         * Start website
         */

        webView.loadUrl(
            "https://appassets.androidplatform.net/assets/www/index.html"
        );
    }


    /*
     * Android back button
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
     * Cleanup
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
