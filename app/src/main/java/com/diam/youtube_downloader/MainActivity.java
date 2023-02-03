package com.diam.youtube_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class MainActivity extends AppCompatActivity {

    EditText youtubeDownloadLink;
    Button downLoadButton;
    String newLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youtubeDownloadLink = findViewById(R.id.youtube_link);
        downLoadButton = findViewById(R.id.downloadButton);

        downLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = youtubeDownloadLink.getText().toString().trim();
                YouTubeUriExtractor youTubeUriExtractor = new YouTubeUriExtractor(MainActivity.this) {
                    @Override
                    public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                        if (ytFiles != null){
                            int tag = 137;
                            newLink = ytFiles.get(tag).getUrl();
                            String title = "my_video";
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(newLink));
                            request.setTitle(title);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title+".mp4");
                            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            request.allowScanningByMediaScanner();
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                            downloadManager.enqueue(request);
                        }
                    }
                };

                youTubeUriExtractor.execute(link);
            }
        });
    }
}