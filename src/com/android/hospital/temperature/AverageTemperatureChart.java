/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.hospital.temperature;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.android.hospital.constant.AppConstant;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.util.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Average temperature demo chart.
 */
public class AverageTemperatureChart extends AbstractDemoChart {
	
  private static final long HOUR = 3600 * 1000;

  private static final long DAY = HOUR * 24;

  private static final int HOURS = 24;
	
	
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Average temperature";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The average temperature in 4 Greek islands (line chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
    String[] titles = new String[] { "��������ͼ" };
    List<Date[]> x = getTempTimeArr();
    List<double[]> values = getTempArr();

    int[] colors = new int[] { Color.BLUE };
    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
    }
    renderer.setBackgroundColor(Color.BLACK);
    renderer.setApplyBackgroundColor(true); 
    DebugUtil.debug("gettime--->"+x.get(0)[0]
            .getTime());
    DebugUtil.debug("����Ϊ--->"+x.get(0).length);
    DebugUtil.debug("gettime-24-1-->"+x.get(0)[x.get(0).length - 1]
            .getTime());
    List<Date[]> allX=getAllTempTimeArr();//���е�ʱ��
/*    setChartSettings(renderer, "����ͼ", "ʱ��", "����", allX.get(0)[0]
            .getTime(), allX.get(0)[HOURS - 1].getTime(), 30, 50,
        Color.LTGRAY, Color.LTGRAY);*/
    setChartSettings(renderer, "����ͼ", "ʱ��", "����", x.get(0)[0]
            .getTime(), x.get(0)[x.get(0).length - 1].getTime(), 30, 50,
        Color.LTGRAY, Color.LTGRAY);
    renderer.setXLabels(10);
    renderer.setYLabels(10);
    renderer.setShowGrid(true);
    renderer.setXLabelsAlign(Align.RIGHT);
    renderer.setYLabelsAlign(Align.RIGHT);
    renderer.setZoomButtonsVisible(true);
    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

    Intent intent = ChartFactory.getTimeChartIntent(context, buildDateDataset(titles, x, values),
        renderer, "MM-dd HH:mm");
    return intent;
  }

  /**
   * 
  * @Title: getTempTimeArr 
  * @Description: TODO(�õ��¶ȶ�Ӧ��ʱ��) 
  * @param @return    �趨�ļ� 
  * @return List<Date[]>    �������� 
  * @throws
   */
  public List<Date[]> getTempTimeArr(){
	  String[] tempTimeArr=AppConstant.temperatureTimeArr;//�¶�ʱ��
	  List<Date[]> x = new ArrayList<Date[]>();
	  Date[] dates = new Date[tempTimeArr.length];
	  try {
		  for (int i = 0; i < tempTimeArr.length; i++) {
				dates[i]=Util.converToDate(tempTimeArr[i]);
				DebugUtil.debug("ת�����ʱ��Ϊ--->"+dates[i]);
			  }
		  x.add(dates);
	  } catch (Exception e) {
		// TODO: handle exception
	  }
	  return x;
  }
  /**
   * 
  * @Title: getTempArr 
  * @Description: TODO(�õ��¶�) 
  * @param @return    �趨�ļ� 
  * @return List<double[]>    �������� 
  * @throws
   */
  public List<double[]> getTempArr(){
	  String[] tempArr=AppConstant.temperatureArr;//�¶�����
      
	  List<double[]> values = new ArrayList<double[]>();
	  double[] doubles=new double[tempArr.length];
	  for (int i = 0; i < tempArr.length; i++) {
		doubles[i]=Double.parseDouble(tempArr[i]);
		DebugUtil.debug("ת������¶�Ϊ--->"+doubles[i]);
	}
	  values.add(doubles);
	  return values;
  }
  
  /**
   * 
  * @Title: getAllTempTi���е�ʱ��) 
  * @param @return    �趨�ļ� 
  * @return List<Date[]>    �������� 
  * @throws
   */
  public List<Date[]> getAllTempTimeArr(){
	  long now = Math.round(new Date().getTime() / DAY) * DAY;
	  List<Date[]> x = new ArrayList<Date[]>();
	  Date[] dates = new Date[HOURS];
	  for (int i = 0; i < HOURS; i++) {
	    dates[i] = new Date(now - (HOURS - i) * HOUR);
	  }
	  x.add(dates);
	  return x;
  } 
  
}
