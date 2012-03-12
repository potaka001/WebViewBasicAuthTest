package com.webviewbasicauthtest;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;

public class MainActivity extends Activity {

	/**
	 * 「URL」にはBasic認証のあるサイトのURLを設定します。
	 * 「USERNAME」にはBasic認証のあるサイトのユーザーネームを設定します。
	 * 「PASSWORD」にはBasic認証のあるサイトのパスワードを設定します。
	 * 「HOST」にはBasic認証のあるサイトのドメインを設定します。
	 * 「REALM」にはBasic認証のあるサイトで設定してある文字列を設定します。
	 */
	private final static String URL = "http://www.hoge.com/";
	private final static String USERNAME = "username";
	private final static String PASSWORD = "password";
	private final static String HOST = "www.hoge.com";
	private final static String REALM = "Users Only";

	private WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		mWebView = (WebView) findViewById(R.id.webView1);

		mWebView.getSettings().setJavaScriptEnabled(true);

		WebViewDatabase.getInstance(this).clearHttpAuthUsernamePassword();

		mWebView.setHttpAuthUsernamePassword(HOST, REALM, USERNAME, PASSWORD);

		mWebView.setWebViewClient(new MyWebViewClient(this, mWebView));

		mWebView.loadUrl(URL);
	}
}