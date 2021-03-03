package com.shu_da_shi.myapplication.glide.load.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.bumptech.glide.load.model.Model;
import com.shu_da_shi.myapplication.glide.load.Objectkey;
import com.shu_da_shi.myapplication.glide.load.model.data.FileUriFetcher;

import java.io.InputStream;

public class FileUriLoader implements ModelLoader<Uri, InputStream>{
    private final ContentResolver contentResolver;

    public FileUriLoader(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public boolean handles(Uri uri) {
        return contentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme());
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        return new LoadData<>(new Objectkey(uri), new FileUriFetcher(uri,contentResolver));
    }

    public static class  Factory implements ModelLoader.ModelLoaderFactory<Uri, InputStream>{
        private final ContentResolver contentResolver;

        public Factory(ContentResolver contentResolver) {
            this.contentResolver = contentResolver;
        }

        @Override
        public ModelLoader<Uri, InputStream> build(ModelLoaderRegistry registry) {
            return new FileUriLoader(contentResolver);
        }
    }
}
