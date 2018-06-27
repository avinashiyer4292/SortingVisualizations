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

public class HeapSort extends AsyncTask<Void, DataPoint, String> {
    GraphView graph;
    BarGraphSeries<DataPoint> mSeries1;
    DataPoint[] arr;
    Context context;
    private long startTime, endTime;
    private int rate;
    public HeapSort(Context context,GraphView graph, BarGraphSeries<DataPoint> mSeries1, DataPoint[] arr, int rate){
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
        Log.d("Progress publish","Heap Publishing progress now...");
        publishProgress(arr);
    }

    void sort(DataPoint arr[], int rate)
    {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end
            SystemClock.sleep(rate);
            double temp = arr[0].getY();
            arr[0] = new DataPoint(0,arr[i].getY());
            arr[i] = new DataPoint(i,temp);
            publish(arr);
            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    void heapify(DataPoint arr[], int n, int i)
    {
        int largest = i;  // Initialize largest as root
        int l = 2*i + 1;  // left = 2*i + 1
        int r = 2*i + 2;  // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l].getY() > arr[largest].getY())
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r].getY() > arr[largest].getY())
            largest = r;

        // If largest is not root
        if (largest != i)
        {
            SystemClock.sleep(rate);
            double swap = arr[i].getY();
            arr[i] = new DataPoint(i,arr[largest].getY());
            arr[largest] = new DataPoint(largest,swap);
            publish(arr);
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
}
