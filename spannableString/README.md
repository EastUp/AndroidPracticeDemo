```
    SpannableString和SpannabledStringBuilder学习
```
###  一丶常用的Span类有：

     BackgroundColorSpan 背景色
     ClickableSpan 文本可点击，有点击事件
     ForegroundColorSpan 文本颜色（前景色）
    MaskFilterSpan 修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
    MetricAffectingSpan 父类，一般不用
    RasterizerSpan 光栅效果
    StrikethroughSpan 删除线（中划线）
    SuggestionSpan 相当于占位符
    UnderlineSpan 下划线
    AbsoluteSizeSpan 绝对大小（文本字体）
    DynamicDrawableSpan 设置图片，基于文本基线或底部对齐。
    ImageSpan 图片
    RelativeSizeSpan 相对大小（文本字体）
    ReplacementSpan 父类，一般不用
    ScaleXSpan 基于x轴缩放
    StyleSpan 字体样式：粗体、斜体等
    SubscriptSpan 下标（数学公式会用到）
    SuperscriptSpan 上标（数学公式会用到）
    TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）
    TypefaceSpan 文本字体
    URLSpan 文本超链接

## SpannableString
String字符串，这个咱们经常用，一定很熟悉，而这个SpanableString这个就不常用了，其实它们都可以表示字符串，只不过后者可以轻松地利用官方提供的Api对字符串进行各种风格的设置。
### 日常开发中都有哪些应用场景呢？比如：
    显示的文字加上下划线、中划线、斜体、加粗等
    给TextView中部分文字加上点击事件或颜色背景
    TextView中部分文字需要显示的字体大，部分文字需要显示的字体小
    TextView中的文字可要求显示表情符号（在聊天应用中经常会出现）等等等

这点需求虽说实现起来不是什么难事，不过选择一个好的方案，可以事半功倍。接下来介绍今天的主角：SpannableString

先看看官方怎么定义这个类的

    This is the class for text whose content is immutable but to which markup objects can be attached and detached. For mutable text, see SpannableStringBuilder.


    大体意思是：这是文本的类，该文本的内容是不可变的，但可以附加和分离标记对象。对于可变文本，请参见SpannableStringBuilder。

这个类源码很少，100行代码都不到，感兴趣的可以去看看。其中有一个核心方法：setSpan（what,start,end,flags）共有4个参数

    what:是一个Object对象，用来设置字符串的显示风格（其中官方已经给我们提供一些常用的Span类，当然也可以自己定义了）
    sart:从字符串的第几位开始，设置的风格起作用
    end:设置的风格到第几位后，不在起作用；其实与start构成了一个作用区间
    flags:有四种形式，就是确定作用空间是否包括收尾，待会说

## 常用的Span类有：

    BackgroundColorSpan 背景色
    ClickableSpan 文本可点击，有点击事件
    ForegroundColorSpan 文本颜色（前景色）
    MaskFilterSpan 修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
    MetricAffectingSpan 父类，一般不用
    RasterizerSpan 光栅效果
    StrikethroughSpan 删除线（中划线）
    SuggestionSpan 相当于占位符
    UnderlineSpan 下划线
    AbsoluteSizeSpan 绝对大小（文本字体）
    DynamicDrawableSpan 设置图片，基于文本基线或底部对齐。
    ImageSpan 图片
    RelativeSizeSpan 相对大小（文本字体）
    ReplacementSpan 父类，一般不用
    ScaleXSpan 基于x轴缩放
    StyleSpan 字体样式：粗体、斜体等
    SubscriptSpan 下标（数学公式会用到）
    SuperscriptSpan 上标（数学公式会用到）
    TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）
    TypefaceSpan 文本字体
    URLSpan 文本超链接

flags共有四种属性：

    Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终了下标，包括起始下标
    Spanned.SPAN_INCLUSIVE_INCLUSIVE从起始下标到终了下标，同时包括起始下标和终了下标
    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE从起始下标到终了下标，但都不包括起始下标和终了下标
    Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终了下标，包括终了下标

事例


ForegroundColorSpan :给一段文字中个别的字设置颜色，这个比较常用了






图1(![]<img data-original-src="//upload-images.jianshu.io/upload_images/2026112-c922641ec5825266.png" data-original-width="612" data-original-height="194" data-original-format="image/png" data-original-filesize="14273" class="" style="cursor: zoom-in;" src="//upload-images.jianshu.io/upload_images/2026112-c922641ec5825266.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/612/format/webp">)


ForegroundColorSpan，为文本设置前景色，效果和TextView的setTextColor()类似，不过如果用setTextColor()估计得用TextView拼接或者用到Html类加载html片段实现。ForegroundColorSpan，实现如下：


        TextView txtInfo = findViewById(R.id.textView);
        SpannableString span = new SpannableString("设置前背景色");
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)),3, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        txtInfo.setMovementMethod(LinkMovementMethod.getInstance());
        txtInfo.setHighlightColor(getResources().getColor(android.R.color.transparent));
        txtInfo.setText(span);



ImageSpan：在文字中显示表情符号（其实就是设置图片）






图2


如果对一段文字中特殊文字进行处理，就可以实现聊天效果中带有表情符。
怎么实现呢，代码如下，其实我们知道有这个用法就行了


        TextView tv8 = findViewById(R.id.tv8);
        SpannableString spanStr8 = new SpannableString("文字里添加表情(表情)");
        Drawable image = getResources().getDrawable(R.mipmap.ic_launcher);
        image.setBounds(new Rect(0,0,50,50));
        spanStr8.setSpan(new ImageSpan(image),5, 7,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv8.setText(spanStr8);



ClickableSpan：可以为文字加上点击事件，比如常见的：






图3


实现方式：


        SpannableString spanStr10 = new SpannableString("请准守协议《XXXXXX协议》");
        spanStr10.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //点击事件
                Toast.makeText(MainActivity.this,"点击了我，可以写跳转逻辑",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor("#ff4d40"));
            }
        },5,spanStr10.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv10.setMovementMethod(LinkMovementMethod.getInstance()); // 必须的设置这个，不然点击效果 不生效
        tv10.setHighlightColor(Color.parseColor("#ffffff")); //点击后显示的背景色，我这里设置了白色，默认颜色不好看
        tv10.setText(spanStr10);

还有其它一些常规用法，我就不一一列举了，上图：





图3

github地址：https://github.com/A-How/SpanableStringDemo/tree/master
SpanableStringBuilder
定义字符串用String,对于大量字符串进行拼接，我们可以使用StringBuilder进行处理；SpanableStringBuilder也是对大量SpanableString拼接进行处理的，也同样使用append方法。举个例子：






图4

代码实现

        String beforeText = "快快下单";
        String afterText = "(立享200元优惠)";
        int beforeSize = 20;
        int afterSize = 15;
        SpannableStringBuilder builder = new SpannableStringBuilder(beforeText);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffdf40")),0,beforeText.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(beforeSize,true),0,beforeText.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(afterText);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff6940")),beforeText.length(),builder.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(afterSize,true),beforeText.length(),builder.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv12.setText(builder);