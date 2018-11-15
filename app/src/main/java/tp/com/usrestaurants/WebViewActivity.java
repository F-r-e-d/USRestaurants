package tp.com.usrestaurants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.webViewActivity)
    WebView webView;

    @BindView(R.id.webViewProgressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL_MOBILE");
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
           @Override
           public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               progressBar.setVisibility(View.GONE);
            }
           }

        );
    }


}
