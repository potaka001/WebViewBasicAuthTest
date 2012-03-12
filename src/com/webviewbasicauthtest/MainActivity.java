package com.webviewbasicauthtest;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;

public class MainActivity extends Activity {

	/**
	 * �uURL�v�ɂ�Basic�F�؂̂���T�C�g��URL��ݒ肵�܂��B
	 * �uUSERNAME�v�ɂ�Basic�F�؂̂���T�C�g�̃��[�U�[�l�[����ݒ肵�܂��B
	 * �uPASSWORD�v�ɂ�Basic�F�؂̂���T�C�g�̃p�X���[�h��ݒ肵�܂��B
	 * �uHOST�v�ɂ�Basic�F�؂̂���T�C�g�̃h���C����ݒ肵�܂��B
	 * �uREALM�v�ɂ�Basic�F�؂̂���T�C�g�Őݒ肵�Ă��镶�����ݒ肵�܂��B
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