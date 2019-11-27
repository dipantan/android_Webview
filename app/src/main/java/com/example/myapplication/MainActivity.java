package com.example.myapplication;




import android.Manifest;
//import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
//import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    //String url = "https://drive.google.com/file/d/0B_rn9jkskDivX1dfZ3B3M2JVX2M/view?usp=drive_web";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Runtime External storage permission for saving download files
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }


        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("http://dipantan.zfreehost.xyz");

        //handle downloading
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));
                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading File...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                                url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.favourites_page){
            //System.out.println("Hiii");
            Toast.makeText(this,"Created by Dipantan Chakraborty",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    //ActionBar actionbar = getActionBar();

    //Setting up Action bar color using # color code.
 //actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00cfaa")));
}

