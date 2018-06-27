package com.avinashiyer.sortingvisualizations.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by Avinash Iyer on 1/14/18.
 */

public class QuickSort extends AsyncTask<Void, DataPoint, String> {

    GraphView graph;
    BarGraphSeries<DataPoint> mSeries1;
    DataPoint[] arr;
    Context context;
    private long startTime, endTime;
    private int rate;
    public QuickSort(Context context,GraphView graph, BarGraphSeries<DataPoint> mSeries1, DataPoint[] arr,int rate){
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
        sort(arr,0, arr.length-1, rate);
        return null;
    }

    @Override
    protected void onProgressUpdate(DataPoint... values) {
        //super.onProgressUpdate(values);
        Log.d("Handling class", "event update called");
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
        //Log.d("Progress publish","Publishing progress now...");
        publishProgress(arr);
    }

    int partition(DataPoint arr[], int low, int high)
    {
        DataPoint pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j].getY() <= pivot.getY())
            {
                SystemClock.sleep(rate);
                i++;
                // swap arr[i] and arr[j]
                double temp = arr[i].getY();
                arr[i] = new DataPoint(i,arr[j].getY());
                arr[j] = new DataPoint(j,temp);
                publish(arr);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        SystemClock.sleep(rate);
        double temp = arr[i+1].getY();
        arr[i+1] = new DataPoint(i+1,arr[high].getY());
        arr[high] = new DataPoint(high,temp);
        publish(arr);
        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(DataPoint arr[], int low, int high, int rate)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1,rate);
            SystemClock.sleep(rate);
            sort(arr, pi+1, high,rate);
        }
    }
}
