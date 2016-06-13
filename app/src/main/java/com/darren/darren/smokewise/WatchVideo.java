package com.darren.darren.smokewise;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.HashMap;

public class WatchVideo extends Drawer implements View.OnClickListener, NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final int VIDEO_CAPTURE = 101;
    private static final int REQUEST_VIDEO = 102;
    private Uri fileUri;
    Button buttonUpload, buttonFacebookVideos, buttonRemoveVideo;
    VideoView videoView1;
    private int position = 0;
    String path, realPath;
    int stopPosition;
    private RelativeLayout.LayoutParams paramsNotFullscreen;

    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_watch_video);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(com.darren.darren.smokewise.R.layout.activity_watch_video, null, false);
        mDrawer.addView(contentView, 0);

        session = new SessionManagement(getApplicationContext());

        //session.setVideoPath(null);
        HashMap<String, String> videoPath = session.getVideoPath();
        path = videoPath.get(SessionManagement.KEY_VIDEO_PATH);



        buttonUpload = (Button) findViewById(com.darren.darren.smokewise.R.id.buttonUploadVideo);
        buttonUpload.setOnClickListener(this);

        buttonFacebookVideos = (Button) findViewById(R.id.buttonFacebookVideos);
        buttonFacebookVideos.setOnClickListener(this);

        buttonRemoveVideo = (Button) findViewById(R.id.buttonRemoveVideo);
        buttonRemoveVideo.setOnClickListener(this);


        videoView1 = (VideoView) findViewById(R.id.videoView1);
        videoView1.setMediaController(new MediaController(this));

        try {
            if (path != null) {
                videoView1.setVideoURI(Uri.parse(path));
            } else {
                videoView1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.smoking_kills));
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }


/*        try {
            videoView1.setMediaController(new MediaController(this));
            videoView1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.smoking_kills));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }*/
        Log.d("Video", "onCreate called at " + stopPosition);
        videoView1.requestFocus();
        videoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //progressDialog.dismiss();

                if (stopPosition == 0) {
                    videoView1.start();
                } else {
                    videoView1.seekTo(stopPosition);
                }
            }
        });

        Log.d("Get Uri", "" + path);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.darren.darren.smokewise.R.id.buttonUploadVideo:

                Intent videoIntent = new Intent(Intent.ACTION_PICK);
                videoIntent.setType("video/*");
                startActivityForResult(videoIntent, REQUEST_VIDEO);

                break;

            case R.id.buttonFacebookVideos:

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100010610742051"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/nz.smokefree/videos_by")));
                }

                break;


            case R.id.buttonRemoveVideo:

                session.removeVideoPath();
                videoView1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.smoking_kills));
                videoView1.start();

                break;

        }


    }


    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {



        if (requestCode == REQUEST_VIDEO) {
            //Uri selectedVideoLocation = data.getData();

            if (resultCode == RESULT_OK) {

                videoView1.setVideoURI(data.getData());


                realPath = getRealPathFromURI(data.getData());
                Log.d("data.getData()", " " + data.getData());
                session.setVideoPath(realPath);
                Log.d("Set URI", "" + realPath);

                videoView1.start();
            } else {
                Toast.makeText(this, "Cancelled ", Toast.LENGTH_LONG).show();
            }
        }
    }

        // Working
    public String getRealPathFromURI(Uri contentUri) {

        Cursor cursor = null;
        try {

            String[] proj = {MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(contentUri,  proj, null, null, null);
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            return cursor.getString(column_index);
        } finally {

            if (cursor != null) {

                cursor.close();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPosition = videoView1.getCurrentPosition(); //stopPosition is an int
        Log.d("Video", "onPause called at " + stopPosition);
        videoView1.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("Video", "onResume called at " + stopPosition);
        videoView1.seekTo(stopPosition);
        videoView1.start();
    }

}
