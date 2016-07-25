/*
 * CS 193A, Marty Stepp
 * This activity is the main GUI for the downloader app.
 * You can type a URL in the top edit text pane, then press Go,
 * and all links in that page are shown as items in a list view.
 * You can click the list items to download them in the background
 * using a service.
 * Even if the downloader app is exited, the download will get finished
 * because it is running in a service.
 *
 * Today's version adds text-to-speech and speech-to-text.
 */

package cs193a.stanford.edu.downloader;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import stanford.androidlib.*;

public class DownloaderActivity extends SimpleActivity implements AdapterView.OnItemClickListener {
    // web domain where files will be downloaded from
    private static final String DOMAIN = "http://www.martystepp.com/files/";

    private ArrayList<String> listOfLinks;   // these are for the ListView
    private ArrayAdapter<String> adapter;

    /*
     * This method runs when the activity is started up.
     * Sets up initial state of the GUI.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloader);


        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            if (url != null) {
                toast("Done with download of " + url);
            }
        }

        // set up the ListView
        // listOfLinks.setOnItemClickListener(this);

    }

    /*
     * This method is called when the "Go" button is clicked.
     * It fetches all links contained in the given web page.
     */
    public void goClick(View view) {
        EditText edit = (EditText) findViewById(R.id.the_url);
        String webPageURL = edit.getText().toString();

        // TODO: read the list of files (hard-coded)

    }

    /*
     * This method is called when items in the ListView are clicked.
     * It initiates a request to the DownloadService to download the file.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        super.onItemClick(parent, view, index, id);
        String domain = findEditText(R.id.the_url).getText().toString();
        String url = domain + listOfLinks.get(index);
        boolean fake = findCheckBox(R.id.fake).isChecked();
        int delayMS = Integer.valueOf(findEditText(R.id.delay).getText().toString());

        // TODO: use a service to do the download

    }
}
