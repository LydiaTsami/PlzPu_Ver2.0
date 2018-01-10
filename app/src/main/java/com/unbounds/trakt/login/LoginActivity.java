package com.unbounds.trakt.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.unbounds.trakt.ApiWrapper;
import com.unbounds.trakt.BuildConfig;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.request.Code;
import com.unbounds.trakt.api.model.response.Token;

import rx.functions.Action1;

public class LoginActivity extends AppCompatActivity {

    public static Intent createIntent(final Activity activity) {
        return new Intent(activity, LoginActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final WebView webView = (WebView) findViewById(R.id.login_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                if (url.startsWith(getString(R.string.oauth_referrer))) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
                return false;
            }
        });
        webView.loadUrl(String.format("%s/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                BuildConfig.BASE_URL,
                BuildConfig.CLIENT_ID,
                getString(R.string.oauth_referrer)));
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        parseResponse(intent.getData());
    }

    private void parseResponse(final Uri uri) {
        if (uri != null && uri.toString().startsWith(getString(R.string.oauth_referrer))) {
            final String authCode = uri.getQueryParameter("code");
            final Code code = new Code(authCode, getString(R.string.oauth_referrer));
            ApiWrapper.getToken(code).subscribe(new Action1<Token>() {
                @Override
                public void call(final Token token) {
                    LoginManager.getInstance().setToken(token);
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }
}
