package tuc.ples_2019.group_11;

import android.graphics.Color;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.opencsv.CSVWriter;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;








public class plotclass {


    public CSVWriter csvWriter1;
    long starttime1,newentrytime ;

    public boolean resetzoom = false;

    public void startintializing(LineChart mChart) {

        // enable description text
        mChart.getDescription().setEnabled(true);
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        // set an alternative background color
        mChart.setBackgroundColor(Color.BLACK);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        mChart.getAxisLeft().setDrawGridLines(true);
        mChart.getXAxis().setDrawGridLines(true);
        mChart.setDrawBorders(false);
        mChart.setDoubleTapToZoomEnabled(false);
    }


    public void addEntry(byte[] data1, LineChart mChart, boolean recordrunning) {

        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            String datastring = new String(data1);
            float bluetoothfloatdata = Float.parseFloat(datastring);

            if (bluetoothfloatdata > 1) {
                data.addEntry(new Entry(set.getEntryCount(), bluetoothfloatdata), 0);
                //data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 80) + 10f), 0);
            }

            if (recordrunning == true && csvWriter1 != null) {
                newentrytime= System.currentTimeMillis();
                csvWriter1.writeNext(new String[]{getCurrentTimeUsingCalendar(), String.valueOf(newentrytime - starttime1), String.valueOf(bluetoothfloatdata)});
            }
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(150);

            //to view the range as per dynamic input, keep below line commented

            //mChart.setVisibleYRange(-10,10, YAxis.AxisDependency.LEFT);
            // move to the latest entry

            mChart.moveViewT(data.getEntryCount());
  /*          if (resetzoom==true)
            {
                mChart.fitScreen();
//                mChart.centerViewToY(bluetoothfloatdata, YAxis.AxisDependency.LEFT);

            }
*/
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Impedance values are in ohms \u2126");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(3f);
        set.setColor(Color.MAGENTA);
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        set.setDrawHighlightIndicators(true);
        return set;
    }


    public void onRecord(CSVWriter csvWriter, long starttime) {
        csvWriter1=csvWriter;
        starttime1=starttime;
    }

/*
    @Override
   public void onChartDoubleTapped(MotionEvent me)
   {
     resetzoom= true;
   }


   // Below methods are included, because OnChartGestureListener interface is overrided only for "onChartDoubleTapped" method
   public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture)
   {}

    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture)
    {}

    public void onChartLongPressed(MotionEvent me)
    {}

    public void onChartSingleTapped(MotionEvent me)
    {}

    public void onChartScale(MotionEvent me, float scaleX, float scaleY)
    {}

    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY)
    {}
    public void onChartTranslate(MotionEvent me, float dX, float dY)
    {}*/
public static String getCurrentTimeUsingCalendar() {
    Calendar cal = Calendar.getInstance();
    Date date = cal.getTime();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    String formattedDate=dateFormat.format(date);
    return  formattedDate;
    //System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
}
}