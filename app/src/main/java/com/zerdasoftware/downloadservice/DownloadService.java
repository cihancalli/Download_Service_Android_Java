package com.zerdasoftware.downloadservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class DownloadService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AsyncTaskClass asyncTaskClass = new AsyncTaskClass();
        try {
            asyncTaskClass.execute("https://cihancalli.com/").get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    class AsyncTaskClass extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url =  new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;

                    data = inputStreamReader.read();
                }

                return result;

            }catch (Exception e){
                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("Result: "+s);
            super.onPostExecute(s);
        }
    }
}
