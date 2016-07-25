/*
 * CS 193A, Marty Stepp
 * This service downloads files in the background.
 * Even if the downloader app is exited, the service remains running.
 */

package cs193a.stanford.edu.downloader;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.Nullable;
import android.util.Log;

public class DownloaderService extends IntentService {
    // these constants are used with the setAction method on intents
    // to differentiate the various things this service can do
    public static final String ACTION_GET_LINKS          = "getLinks";
    public static final String ACTION_DOWNLOAD           = "downloadFile";
    public static final String ACTION_DOWNLOAD_FINISHED  = "imFinished";

    /* Required constructor. */
    public DownloaderService() {
        this("downloader");
    }

    /* Required constructor. */
    public DownloaderService(String name) {
        super(name);
    }

    /*
     * This method is called every time the DownloadActivity wants to download a file.
     * It starts a download and sets up a notification when the download is complete.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals(ACTION_DOWNLOAD)) {
            String url = intent.getStringExtra("url");

            boolean fake = intent.getBooleanExtra("fake", true);
            int delayMS = intent.getIntExtra("delay", 0);
            Log.d("svc", "service download " + url);

            // (pretend to) download the requested file
            Downloader.downloadFake(url, delayMS);

            // now that download is done, notify the app
            Intent done = new Intent();
            done.setAction(ACTION_DOWNLOAD_FINISHED);
            sendBroadcast(done);

            // create a notification that the download is done;
            // the pending intent will execute when the user clicks the notification
            Intent appIntent = new Intent(this, DownloaderActivity.class);
            appIntent.putExtra("url", url);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, appIntent, 0);

            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.icon_download)
                    .setContentTitle("This is the title")
                    .setContentText("This is the content text")
                    .setAutoCancel(true)
                    .setOngoing(true)   // a-hole mode
                    .setContentIntent(pendingIntent);
            Notification notify = builder.build();

            // make the notification show up on screen
            NotificationManager manager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(123, notify);
        }
    }

    /*
     * This method returns null to indicate that our service does not
     * support bind mode.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
