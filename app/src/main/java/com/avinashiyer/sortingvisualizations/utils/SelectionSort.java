package com.avinashiyer.sortingvisualizations.utils;

/**
 * Created by Avinash Iyer on 1/15/18.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;


public class SelectionSort extends AsyncTask<Void, DataPoint, String> {
    GraphView graph;
    BarGraphSeries<DataPoint> mSeries1;
    DataPoint[] arr;
    Context context;
    private long startTime, endTime;
    private int rate;
    public SelectionSort(Context context,GraphView graph, BarGraphSeries<DataPoint> mSeries1, DataPoint[] arr, int rate){
        this.graph = graph;
        this.mSeries1 = mSeries1;
        this.arr = arr;
        this.context = context;
        this.rate = rate;
    }
    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        startTime = System.currentTimeMillis();
    }

    @Override
    protected String doInBackground(Void... voids) {
        sort(arr,rate);
        return null;
    }

    @Override
    protected void onProgressUpdate(DataPoint... values) {
        //super.onProgressUpdate(values);
        //Log.d("Handling class", "event update called");
        graph.removeAllSeries();
        mSeries1 = new BarGraphSeries<>(values);
        graph.addSeries(mSeries1);
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        //Log.d("Async task","Done updating UI changes");
        endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime)/1000;
        Toast.makeText(context, "Time taken to sort: "+timeTaken+" secs", Toast.LENGTH_SHORT).show();
    }

    public void publish(DataPoint[] arr){
        //Log.d("Progress publish","Selection Publishing progress now...");
        publishProgress(arr);
    }

    void sort(DataPoint arr[],int rate)
    {
        int n = arr.length;

        for (int i = 0; i < n-1; i++)
        {
            for (int j = i+1; j < n; j++)
                if (arr[j].getY() < arr[i].getY()) {
                    SystemClock.sleep(rate);
                    double temp = arr[j].getY();
                    arr[j] = new DataPoint(j, arr[i].getY());
                    arr[i] = new DataPoint(i, temp);
                    publish(arr);
                }
        }
    }

}

