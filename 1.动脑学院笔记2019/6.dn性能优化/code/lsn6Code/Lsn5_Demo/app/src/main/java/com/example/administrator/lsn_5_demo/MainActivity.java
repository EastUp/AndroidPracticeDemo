package com.example.administrator.lsn_5_demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    Bitmap inputBitmap=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }

//        File input = new File(Environment.getExternalStorageDirectory(), "girl.jpg");
//        inputBitmap = BitmapFactory.decodeFile(input.getAbsolutePath());

//        /**
//         * 质量压缩
//         */
//        compress(bitmap, Bitmap.CompressFormat.JPEG,50,Environment.getExternalStorageDirectory()+"/test_q.jpeg");
//        /**
//         * 尺寸压缩
//         */
//        //filter 图片滤波处理 色彩更丰富
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
//        compress(scaledBitmap, Bitmap.CompressFormat.JPEG,100,Environment.getExternalStorageDirectory()+"/test_scaled.jpeg");
//        //png格式
//        compress(bitmap, Bitmap.CompressFormat.PNG,100,Environment.getExternalStorageDirectory()+"/test.png");
//        //webp格式
//        compress(bitmap, Bitmap.CompressFormat.WEBP,100,Environment.getExternalStorageDirectory()+"/test.webp");

    }

    /**
     * 压缩图片到制定文件
     *
     * @param bitmap 待压缩图片
     * @param format 压缩的格式
     * @param q      质量
     * @param path   文件地址
     */
//    private void compress(Bitmap bitmap, Bitmap.CompressFormat format, int q, String path) {
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(path);
//            bitmap.compress(format, q, fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != fos) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native void nativeCompress(Bitmap bitmap, int q, String path);

    public void click(View view) {

        File input = new File(Environment.getExternalStorageDirectory(), "girl.jpg");
        inputBitmap = BitmapFactory.decodeFile(input.getAbsolutePath());

        nativeCompress(inputBitmap, 50, Environment.getExternalStorageDirectory() + "/girl2.jpg");
        Toast.makeText(this, "执行完成", Toast.LENGTH_SHORT).show();
    }
}
