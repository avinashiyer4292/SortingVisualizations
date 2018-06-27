package com.avinashiyer.sortingvisualizations.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.avinashiyer.sortingvisualizations.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private AppCompatSpinner mRangeSelector, mTypeOfRangeSelector, mSortSelector, mRateSelector;
    private Button mBtn;
    private String type,algorithm;
    private int range, rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initialising spinners and button
        mRangeSelector = (AppCompatSpinner)findViewById(R.id.rangeSelector);
        mTypeOfRangeSelector = (AppCompatSpinner)findViewById(R.id.typeSelector);
        mSortSelector = (AppCompatSpinner)findViewById(R.id.sortSelector);
        mRateSelector = (AppCompatSpinner)findViewById(R.id.rateSelector);
        mBtn = (Button)findViewById(R.id.nextBtn);


//        setting data for spinners
        int[] rangeArray = new int[]{10,50,100,200,500,1000};
        final ArrayAdapter<Integer> mRangeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getRangeList(rangeArray));
        mRangeSelector.setAdapter(mRangeAdapter);

        String[] typeArray = new String[]{"Ascending","Descending","Random"};
        ArrayAdapter<String> mTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getTypeList(typeArray));
        mTypeOfRangeSelector.setAdapter(mTypeAdapter);

        String[] sortArray = new String[]{"Merge Sort","Insertion Sort","Quick Sort" ,"Selection Sort", "Heap Sort"};
        ArrayAdapter<String> mSortAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getTypeList(sortArray));
        mSortSelector.setAdapter(mSortAdapter);

        String[] rateArray = new String[]{"1ms","2ms","3ms"};
        ArrayAdapter<String> mRateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getTypeList(rateArray));
        mRateSelector.setAdapter(mRateAdapter);


//        setting spinner item selector listeners
        mRangeSelector.setOnItemSelectedListener(this);
        mTypeOfRangeSelector.setOnItemSelectedListener(this);
        mSortSelector.setOnItemSelectedListener(this);
        mRateSelector.setOnItemSelectedListener(this);

//        setting btn functionality
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(range!=0 && type!=null){
                    Intent i=new Intent(MainActivity.this, SortingActivity.class);
                    Log.d("MainActvity","Type: "+type+" Range: "+range+" Algo: "+algorithm);
                    i.putExtra("type",type);
                    i.putExtra("range",range);
                    i.putExtra("algorithm", algorithm);
                    i.putExtra("rate",rate);
                    startActivity(i);
                }
                else
                    Toast.makeText(MainActivity.this,"Please select range, sort and type",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.rangeSelector: range = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                break;
            case R.id.typeSelector: type = String.valueOf(adapterView.getItemAtPosition(i));
                break;
            case R.id.sortSelector: algorithm = String.valueOf(adapterView.getItemAtPosition(i));
                break;
            case R.id.rateSelector: rate = i+1;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        range = Integer.parseInt(adapterView.getItemAtPosition(0).toString());
        type= String.valueOf(adapterView.getItemAtPosition(0));
        algorithm = String.valueOf(adapterView.getItemAtPosition(0));
        rate = 1;

    }

    private List<Integer> getRangeList(int[] array){
        List<Integer> rangeList = new ArrayList<>();
        for(int i:array)
            rangeList.add(i);
        return rangeList;
    }

    private List<String> getTypeList(String[] array){
        List<String> typeList = new ArrayList<>();
        for(String s: array)
            typeList.add(s);
        return typeList;
    }


}
