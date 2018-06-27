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
 * Created by Avinash Iyer on 1/15/18.
 */

public class MergeSort extends AsyncTask<Void, DataPoint, String>{

    GraphView graph;
    BarGraphSeries<DataPoint> mSeries1;
    DataPoint[] arr;
    Context context;
    private long startTime, endTime;
    private int rate;
    public MergeSort(Context context,GraphView graph, BarGraphSeries<DataPoint> mSeries1, DataPoint[] arr, int rate){
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
        sort(arr,0, arr.length-1,rate);
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

    public DataPoint[] merge(DataPoint arr[], int left, int mid, int right, int rate) {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        /* Create temp arrays */
        DataPoint L[] = new DataPoint[n1];
        DataPoint R[] = new DataPoint[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[mid + 1 + j];


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i].getY() <= R[j].getY()) {
                SystemClock.sleep(rate);
                arr[k] = new DataPoint(k,L[i].getY());
                publish(arr);
                i++;
            } else {
                SystemClock.sleep(rate);
                arr[k] = new DataPoint(k,R[j].getY());
                publish(arr);
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            SystemClock.sleep(rate);
            arr[k] = new DataPoint(k,L[i].getY());
            publish(arr);
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            SystemClock.sleep(rate);
            arr[k] = new DataPoint(k,R[j].getY());
            publish(arr);
            j++;
            k++;
        }
        return arr;
    }

    // Main function that sorts arr[l..r] using
    // merge()
    public DataPoint[] sort(DataPoint arr[], int left, int right, int rate) {
        if (left < right) {
            // Find the middle point
            int m = (left + right) / 2;

            // Sort first and second halves
            sort(arr, left, m, rate);
            SystemClock.sleep(rate);
            sort(arr, m + 1, right, rate);

            // Merge the sorted halves
            DataPoint[] result = merge(arr, left, m, right, rate);
            return result;
        }
        return null;
    }

}
