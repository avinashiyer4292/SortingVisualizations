package com.avinashiyer.sortingvisualizations.activities;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.avinashiyer.sortingvisualizations.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.avinashiyer.sortingvisualizations.utils.HeapSort;
import com.avinashiyer.sortingvisualizations.utils.InsertionSort;
import com.avinashiyer.sortingvisualizations.utils.MergeSort;
import com.avinashiyer.sortingvisualizations.utils.QuickSort;
import com.avinashiyer.sortingvisualizations.utils.SelectionSort;

import java.util.Random;

public class SortingActivity extends AppCompatActivity {
    //sorting variables
    private BarGraphSeries<DataPoint> mSeries1;
    private DataPoint[] arrayToSort, pausedArrayToSort;
    private GraphView graph;
    private String type, algorithm;
    private int range, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        //get intent data

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            type = extras.getString("type");
            range = extras.getInt("range");
            algorithm = extras.getString("algorithm");
            rate = extras.getInt("rate");
        }

        //initialise graphview
        graph = (GraphView) findViewById(R.id.sortingGraphView1);
        mSeries1 = new BarGraphSeries<>(generateData(type, range));
        graph.addSeries(mSeries1);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);

        //setting a sleep delay of 2 seconds before sorting com.avinashiyer.visualization begins
        SystemClock.sleep(2000);

    }
    private DataPoint[] generateData(String type, int range) {
        //int r = new Random().nextInt(101);
        DataPoint[] values = new DataPoint[range];
        for (int i=0; i<range; i++) {
            double x = i;
            double y=-1;
            if(type.equals("Ascending"))
                y = (new Random().nextInt(2)+i) % range;
            else if(type.equals("Descending"))
                y = (new Random().nextInt(2)+range-i) % range;
            else
                y = new Random().nextInt() % range;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        arrayToSort = values;
        return values;
    }

    private void startSortingTask(DataPoint[] arrayToSort){
        if(algorithm.equals("Merge Sort"))
            new MergeSort(this,graph,mSeries1,arrayToSort,rate).execute();
        else if(algorithm.equals("Heap Sort"))
            new HeapSort(this,graph,mSeries1,arrayToSort,rate).execute();
        else if(algorithm.equals("Selection Sort"))
            new SelectionSort(this,graph,mSeries1,arrayToSort,rate).execute();
        else if(algorithm.equals("Insertion Sort"))
            new InsertionSort(this,graph,mSeries1,arrayToSort,rate).execute();
        else if(algorithm.equals("Quick Sort"))
            new QuickSort(this,graph,mSeries1,arrayToSort,rate).execute();
    }

    @Override
    public void onResume() {
          super.onResume();
            pausedArrayToSort = arrayToSort;
            startSortingTask(pausedArrayToSort);

    }

    @Override
    public void onPause() {
        //mHandler.removeCallbacks(mTimer2);
            super.onPause();
            pausedArrayToSort = arrayToSort;
    }



}
