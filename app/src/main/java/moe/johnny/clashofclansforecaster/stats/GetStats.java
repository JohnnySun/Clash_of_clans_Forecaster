package moe.johnny.clashofclansforecaster.stats;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import rx.subjects.PublishSubject;

import moe.johnny.clashofclansforecaster.stats.COCJsonFormat;
/**
 * Created by JohnnySun on 2016/4/2.
 */
public class GetStats {
    private static final String TAG="GetStats";
    public static  void GetCOCStats() {
        try {
            URL url = new URL("http://clashofclansforecaster.com/STATS.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                COCJsonFormat.JsonFormat(readStream(in));
            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private static String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        //Log.d(TAG,sb.toString());
        return sb.toString();
    }
}
