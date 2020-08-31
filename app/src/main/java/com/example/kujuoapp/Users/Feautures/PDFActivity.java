package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.kujuoapp.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PDFActivity extends AppCompatActivity {

    static PDFView pdfView;

    WebView webview;
    ProgressDialog progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f);

       // pdfView = findViewById(R.id.pdf_view);
        //getPdf();
      //  getmyPdf();

        /*Murtuza Work*/
        progressbar = new ProgressDialog(PDFActivity.this);
        progressbar.show();

        getmyPDF();

    }

    private void getmyPDF()
    {
        webview = (WebView)findViewById(R.id.webView);


        webview.getSettings().setJavaScriptEnabled(true);
        String filename ="https://www.who.int/intellectualproperty/documents/thereport/CIPIH23032006.pdf";
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + filename);

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here

                progressbar.dismiss();

            }
        });
    }

    public static void getPdf() {
        new AsyncTask<Void, Void, Void>() {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    InputStream input = new URL("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf").openStream();
                    pdfView.fromStream(input).load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public static void getmyPdf(){
        new Runnable(){
            @Override
            public void run() {
                try {
                    InputStream input = new URL("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf").openStream();
                    pdfView.fromStream(input).load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();
    }
}