@[TOC](变色字体) 

## 一、变色字体(canvas.clipRect)


### 直接上代码

``` kotlin 
                mPaint.color = Color.RED
                var path = Path()
                val triangleY = (width * sin(Math.toRadians(60.0))).toFloat()
                path.moveTo((width/2).toFloat(), 0F)
                path.lineTo(0f, triangleY)
                path.lineTo(width.toFloat(), triangleY)
                path.close() //会自动连接到起始点
                canvas.drawPath(path,mPaint)
```

Math.sin(Math.toRadians(30.0)) = 1 * 1/2 = 0.5




