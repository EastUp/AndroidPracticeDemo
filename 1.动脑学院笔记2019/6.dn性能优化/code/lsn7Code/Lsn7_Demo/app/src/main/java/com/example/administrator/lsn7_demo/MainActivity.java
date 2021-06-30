package com.example.administrator.lsn7_demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
            return;
        }

        ImageCache.getInstance().init(this,Environment.getExternalStorageDirectory()+"/dn");

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(this));
        //假设是从网络上来的
//        BitmapFactory.Options options=new BitmapFactory.Options();
//        //如果要复用，需要设计成异变
//        options.inMutable=true;
//        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.wyz_p,options);
//        for(int i=0;i<100;i++){
//            options.inBitmap=bitmap;
//            bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.wyz_p,options);
//        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                ImageCache.getInstance().init(this,Environment.getExternalStorageDirectory()+"/dn");

                ListView listView = findViewById(R.id.listView);
                listView.setAdapter(new MyAdapter(this));

            }
        }
    }

    void i(Bitmap bitmap){
        Log.i("jett","图片"+bitmap.getWidth()+"x"+bitmap.getHeight()+" 内存大小是:"+bitmap.getByteCount());
    }

}
