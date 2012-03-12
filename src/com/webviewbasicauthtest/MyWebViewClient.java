package com.webviewbasicauthtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {

	private String loginCookie;
	private Context mContext;
	private WebView mWebView;

	public MyWebViewClient(Context context, WebView webview) {
		super();

		mContext = context;
		mWebView = webview;
	}

	@Override
	public void onPageStarted( WebView view, String url, Bitmap favicon ) {
	}

	@Override
	public void onPageFinished( WebView view, String url ) {
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setCookie(url, loginCookie);
	}

	@Override
	public void onReceivedError( WebView view, int errorCode, String description, String failingUrl ) {
		Toast.makeText(view.getContext(), "�y�[�W�ǂݍ��݃G���[", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLoadResource( WebView view, String url ){
		CookieManager cookieManager = CookieManager.getInstance();
		loginCookie = cookieManager.getCookie(url);
	}

	@Override
	public boolean shouldOverrideUrlLoading( WebView view, String url ) {
		return false;
	}

	@Override
	public void onReceivedSslError( WebView view, SslErrorHandler handler, SslError error ) {
		handler.proceed();
	}

	@Override
	public void onReceivedHttpAuthRequest( WebView view, final HttpAuthHandler handler, final String host, final String realm ){
		// ����host�ɂ�setHttpAuthUsernamePassword�̑�1�����Őݒ肵�������񂪓����Ă��܂��B
		// Android 4.*�i�����������3.*����j�ł͂Ȃ�������host�ɏ���Ƀ|�[�g�ԍ����ǋL����Ă��܂��܂��B
		// ��̓I�ɂ́u:80�v���ǋL����Ă��܂��܂��B

		String userName = null;
		String userPass = null;

		if (handler.useHttpAuthUsernamePassword() && view != null) {
			String[] haup = view.getHttpAuthUsernamePassword(host, realm);
			if (haup != null && haup.length == 2) {
				userName = haup[0];
				userPass = haup[1];
			}
		}

		if (userName != null && userPass != null) {
			handler.proceed(userName, userPass);
		}
		else {
			showHttpAuthDialog(handler, host, realm, null, null, null);
		}
	}

	private void showHttpAuthDialog( final HttpAuthHandler handler, final String host, final String realm, final String title, final String name, final String password ) {
		LinearLayout llayout = new LinearLayout((Activity)mContext);
		final TextView textview1 = new TextView((Activity)mContext);
		final EditText edittext1 = new EditText((Activity)mContext);
		final TextView textview2 = new TextView((Activity)mContext);
		final EditText edittext2 = new EditText((Activity)mContext);
		llayout.setOrientation(LinearLayout.VERTICAL);
		textview1.setText("username:");
		textview2.setText("password:");
		llayout.addView(textview1);
		llayout.addView(edittext1);
		llayout.addView(textview2);
		llayout.addView(edittext2);

		final Builder mHttpAuthDialog = new AlertDialog.Builder((Activity)mContext);
		mHttpAuthDialog.setTitle("Basic Authentication")
			.setView(llayout)
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					EditText etUserName = edittext1;
					String userName = etUserName.getText().toString();
					EditText etUserPass = edittext2;
					String userPass = etUserPass.getText().toString();

					mWebView.setHttpAuthUsernamePassword(host, realm, name, password);

					handler.proceed(userName, userPass);
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					handler.cancel();
				}
			})
			.create().show();
	}

}


