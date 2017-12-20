package com.example.phillip.okhttp;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

public class MmpMainActivity extends AppCompatActivity implements GlideModule{
    private static final int DISK_CACHE_SIZE = 100 * 1024 * 1024;
    public static final int MAX_MEMORY_CACHE_SIZE = 10 * 1024 * 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmp_main);
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        File storageDirectory = Environment.getExternalStorageDirectory();
        String downloadDirectoryPath=storageDirectory+"/GlideCache";
        //设置磁盘缓存的路径 path
        final File cacheDir = new File(downloadDirectoryPath);
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(cacheDir,DISK_CACHE_SIZE);
            }
        });
        //设置内存缓存大小，一般默认使用glide内部的默认值
        builder.setMemoryCache(new LruResourceCache(MAX_MEMORY_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
