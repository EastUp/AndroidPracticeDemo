#<center>MPAndroidChart(待续)<center>

```
	MPAndroidChart:Android上强大图表工具
```
[官方文档说明](https://weeklycoding.com/mpandroidchart-documentation/)
## Barchart(柱状图)
### 常用方法说明:
``` kotlin
        barChart.animateXY(800,1000) //动画
        barChart.setExtraOffsets(0f,0f,0f,10f) //设置图表的偏移量
        barChart.minOffset = 0f //为BarChart左右设置填充 默认是15f
        barChart.setNoDataText("好像没有数据")
        barChart.description.isEnabled = false //禁用描述
        barChart.setTouchEnabled(false) //不允许用户触摸
        //设置左边Y轴
        val axisLeft = barChart.axisLeft
        axisLeft.isEnabled = true
        barChart.axisRight.isEnabled = false
        axisLeft.setDrawAxisLine(false)//不画左边Y轴的坐标轴
        axisLeft.setDrawLabels(false)//不画左边Y周的lables
        axisLeft.setDrawGridLines(true) // 画垂直于Y轴的线
        axisLeft.gridColor = Color.parseColor("#e3e3e3")//设置Y轴的网格线颜色
        axisLeft.spaceTop = 30f  //设置图表中的最高值的顶部间距占最高值的值的百分比（设置的百分比 = 最高柱顶部间距/最高柱的值）。默认值是10f，即10% 。

        //设置X轴
        val xAxis = barChart.xAxis
        xAxis.axisLineColor = Color.parseColor("#e3e3e3")//x轴颜色
        xAxis.textColor = Color.parseColor("#484848")//x轴lable的颜色
        xAxis.textSize = 8f//x轴lable的字体大小  单位dp。
        xAxis.valueFormatter = barChartValueFormatter//x轴的lable自定义f
        xAxis.setCenterAxisLabels(true)//x轴lable居中
        xAxis.setDrawGridLines(false) //不画锤子x轴的网格线
        xAxis.position = XAxis.XAxisPosition.BOTTOM //设置X轴的位置及lable的在x轴的里外
        xAxis.setAvoidFirstLastClipping(false)//避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。
        xAxis.axisMaximum = 13f //设置该轴的最大值。 如果设置了，这个值将不会是根据提供的数据计算出来的。
        xAxis.axisMinimum = 1f  //设置该轴的自定义最小值。 如果设置了，这个值将不会是根据提供的数据计算出来的。
        xAxis.isGranularityEnabled = true
        xAxis.granularity = 1f  //x轴最小密度是1f
        xAxis.setCenterAxisLabels(true) //lable 居中
//        xAxis.labelRotationAngle = 90f //设置画x坐标lable时的方向
//        xAxis.xOffset = 3f
        xAxis.setLabelCount(13,false) // 设置y轴的标签数量。 请注意，这个数字是不固定 if(force == false)，只能是近似的。 如果 if(force == true)，
                                            // 则确切绘制指定数量的标签，但这样可能导致轴线分布不均匀。

        //设置legend 图例
        val legend = barChart.legend
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT //在最右边
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP //在最顶部
        legend.orientation = Legend.LegendOrientation.HORIZONTAL //图例 水平摆放
        legend.textColor = Color.parseColor("#7e7e7e") //设置图例标签的颜色。
        legend.textSize = 9f //设置图例标签的大小 单位dp
        legend.formToTextSpace = 5f //设置 legend-form 和 legend-label 之间的空间。单位dp。
        legend.formSize = 3f //设置 legend-forms 的大小，单位dp。
        legend.setDrawInside(false) //图例会知道chart的外面
``` 
### BarData设置说明: 
``` kotlin
        //设置数据(如果分组的话会覆盖掉x值)
        val groupSpace = 0.64f
        val barSpace = 0.03f // x4 DataSet
        val barWidth = 0.2f // x4 DataSet
        var barEntity = BarEntry(1f, 20.3f)   //bardata.groupBars分组的第一组
        var barEntity1 = BarEntry(2f, 40.3f)
        var barEntity2 = BarEntry(3f, 60.3f)
        var barEntity3 = BarEntry(4f, 10.3f)
        var barEntity31 = BarEntry(12f, 10.3f)

        var barEntity4 = BarEntry(5f, 10.3f) //bardata.groupBars分组的第一组
        var barEntity5 = BarEntry(4f, 80.3f)
        var barEntity6 = BarEntry(3f, 50.3f)
        var barEntity61 = BarEntry(12f, 50.3f)

        var barEntity7 = BarEntry(4f, 40.3f)  //bardata.groupBars分组的第一组
        var barEntity8 = BarEntry(3f, 10.3f)
        var barEntity9 = BarEntry(2f, 100.3f)
        var barEntity91 = BarEntry(12f, 100.3f)

//        var barEntity = BarEntry(changeX(1f,0), 20.3f)
//        var barEntity1 = BarEntry(changeX(2f,0), 40.3f)
//        var barEntity2 = BarEntry(changeX(3f,0), 60.3f)
//        var barEntity3 = BarEntry(changeX(4f,0), 10.3f)
//        var barEntity31 = BarEntry(changeX(5f,0), 10.3f)
//
//        var barEntity4 = BarEntry(changeX(5f,1), 10.3f)
//        var barEntity5 = BarEntry(changeX(4f,1), 80.3f)
//        var barEntity6 = BarEntry(changeX(3f,1), 50.3f)
//        var barEntity61 = BarEntry(changeX(12f,1), 50.3f)
//
//        var barEntity7 = BarEntry(changeX(4f,2), 40.3f)
//        var barEntity8 = BarEntry(changeX(3f,2), 10.3f)
//        var barEntity9 = BarEntry(changeX(2f,2), 100.3f)
//        var barEntity91 = BarEntry(changeX(12f,2), 100.3f)
        var bardata = BarData()
        var barDataSet1 = BarDataSet(listOf(barEntity, barEntity1, barEntity2, barEntity3,barEntity31), "空调")
        barDataSet1.color = Color.parseColor("#1d6359")
        var barDataSet2 = BarDataSet(listOf(barEntity4, barEntity5, barEntity6,barEntity61), "灯光")
        barDataSet2.color = Color.parseColor("#42b39a")
        var barDataSet3 = BarDataSet(listOf(barEntity7, barEntity8, barEntity9,barEntity91), "其它")
        barDataSet3.color = Color.parseColor("#73c382")
        bardata.addDataSet(barDataSet1)
        bardata.addDataSet(barDataSet2)
        bardata.addDataSet(barDataSet3)
        bardata.barWidth = 0.1f //设置bar的宽度
        bardata.groupBars(1f, groupSpace, barSpace) //设置开头,每组的间距,每组内bar的间距(请仔细算算这个间距),组所处的x坐标位置已经被重写
```
## PieChart(饼状图)
### 常用方法说明: 
``` kotlin
        //饼状图的方法说明
        pieChart.animateY(800, Easing.EaseInOutQuad) //设置饼状图动画
        pieChart.setExtraOffsets(0f,0f,0f,0f) //设置图表的偏移量
        pieChart.minOffset = 0f //为BarChart左右设置填充 默认是15f
        pieChart.setNoDataText("好像没有数据")
        pieChart.description.isEnabled = false //禁用描述
        pieChart.setTouchEnabled(true) //允许用户触摸
        pieChart.isRotationEnabled = true //允许滑动旋转
        pieChart.setUsePercentValues(true) //在图表内的值绘制在百分之，而不是与它们的原始值。 规定的值ValueFormatter进行格式化，然后以百分比规定。
        pieChart.setDrawEntryLabels(false) // 设置为true，在扇区绘制lable值。
        pieChart.setEntryLabelTextSize(3f)
        pieChart.isHighlightPerTapEnabled = false // 不允许点击手势突出显示值。
        pieChart.holeRadius = 52f/70f*100 //设置中心圆孔半径占整个饼状图半径的百分比（100f 是最大=整个图表的半径），默认的50％的百分比（即50f）。
        pieChart.transparentCircleRadius = 2f  //把透明圆设置的比hole小就看不到啦
                                            // 则确切绘制指定数量的标签，但这样可导致轴线分布不均匀。
        pieChart.centerText = generateCenterSpannableText() //通过SpannableString可设置中心文字的样式

        //设置legend 图例
        val legend = pieChart.legend
        legend.isEnabled = false //不让图例显示
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER //在最右边
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM //在最顶部
        legend.orientation = Legend.LegendOrientation.HORIZONTAL //图例 水平摆放
        legend.textColor = Color.parseColor("#7e7e7e") //设置图例标签的颜色。
        legend.textSize = 9f //设置图例标签的大小 单位dp
        legend.formToTextSpace = 5f //设置 legend-form 和 legend-label 之间的空间。单位dp。
        legend.formSize = 3f //设置 legend-forms 的大小，单位dp。
        legend.setDrawInside(false) //图例会知道chart的外面
```

### PieChart中心文字多样式例子
```kotlin
  /**
     *  通过SpannableString来设置CenterText的样式
     */
    private fun generateCenterSpannableText(): SpannableString {

        val s = SpannableString("23125.9\n10月份总用电")
        s.setSpan(RelativeSizeSpan(1.5f), 0, 7, 0)  //饼状图中心文字的大小 是经过他们处理了的,不能直接设置为效果图中的大小
        s.setSpan(StyleSpan(Typeface.NORMAL), 0, 7, 0)
        s.setSpan(ForegroundColorSpan(Color.parseColor("#484848")), 0, 7,  0)
        s.setSpan(RelativeSizeSpan(0.5f), 7, s.length, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 7, s.length,0)
        s.setSpan(ForegroundColorSpan(Color.parseColor("#7e7e7e")), 7, s.length,0)


//        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
//        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
//        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
//        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
//        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
//        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
//        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }s
```

### PieData设置说明
``` kotlin
        var barEntity = PieEntry(1f, "空调")
        var barEntity1 = PieEntry(1f, "灯光")
        var barEntity2 = PieEntry(1f, "其它")

        var barSet = PieDataSet(listOf(barEntity,barEntity1,barEntity2),"用电比重")
//        barSet.setDrawValues(false) //不绘制像 20f 20f 60f这些

        val colors = listOf<Int>(Color.parseColor("#1d6359"), Color.parseColor("#42b39a"), Color.parseColor("#73c382"))
        barSet.colors = colors
        barSet.sliceSpace = 0f //每个entity之间的间距
        var bardata = PieData(barSet)
        bardata.setValueFormatter(PercentFormatter(pieChart,false)) //设置成百分比
        bardata.setValueTextSize(3f) //设置比重的字体大小 33.3%
        bardata.setValueTextColor(Color.WHITE) //设置比重字体的颜色
```
