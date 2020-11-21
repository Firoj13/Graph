package com.example.orahi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.orahi.model.GraphModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity {


    private RequestQueue requestQueue;

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String > labelNames;
    ArrayList<GraphModel> monthDatesDataArrayList = new ArrayList<GraphModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        textView = findViewById(R.id.textView);

        barChart = findViewById(R.id.barChart);
        //
        barEntryArrayList = new ArrayList<>();
        labelNames = new ArrayList<>();

        barEntryArrayList.clear();
        labelNames.clear();


        requestQueue = Volley.newRequestQueue(this);

        String url = "https://demo5636362.mockable.io/stats";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        String Month = data.getString("month");
                        String Stat = data.getString("stat");

                        monthDatesDataArrayList.add(new GraphModel(Month,Stat));

                    }
                    for (int i=0; i<monthDatesDataArrayList.size(); i++){
                        String month = monthDatesDataArrayList.get(i).getMonth();
                        String stat = monthDatesDataArrayList.get(i).getState();

                        barEntryArrayList.add(new BarEntry(i, Float.parseFloat(stat)));
                        labelNames.add(month);
                    }

                    BarDataSet barDataSet= new BarDataSet(barEntryArrayList,"Month stat");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    Description description = new Description();
                    description.setText("Months");
                    barChart.setDescription(description);
                    BarData barData =  new BarData(barDataSet);
                    barChart.setData(barData);
                    //xaxis value
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
                    //set position month
                    xAxis.setPosition(XAxis.XAxisPosition.TOP);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);
                    xAxis.setGranularity(1f);
                    xAxis.setLabelCount(labelNames.size());
                    xAxis.setLabelRotationAngle(270);
                    barChart.animateY(2000);
                    barChart.invalidate();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);


    }


}
