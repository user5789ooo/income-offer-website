# Income Offers
# Keep WebView and JavaScript related classes safe.

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface

-dontwarn android.webkit.**
