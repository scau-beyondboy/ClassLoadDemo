package com.scau.beyondboy.classloaddemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import intefa.IDynamic;

public class MainActivity extends AppCompatActivity {

    private Resources mResources;
    private ClassLoader mPluClsLoad;
    private AssetManager mAssetManager = null;
    private boolean isPluClsLoad = false;
    private Resources.Theme mTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dnyLoadPluCls();
            }
        });
    }

    //加载插件资源
    private void loadDynRes(String dexPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addPath.setAccessible(true);
            addPath.invoke(assetManager, dexPath);
            Resources resources = getResources();
            mResources=new Resources(assetManager,resources.getDisplayMetrics(),resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //动态加载其他已安装的插件
    private void dynamicLoad() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.scau.beyondboy.plugindemo1");
        //获取得包管理器
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        if (resolveInfos.size() == 0) {
            return;
        }
        ActivityInfo activityInfo = resolveInfos.get(0).activityInfo;
        String packName = activityInfo.packageName;
        String apkPath = activityInfo.applicationInfo.sourceDir;
        //获取当前应用dex输出目录
        String dexOutputDir = getApplicationInfo().dataDir;
        String libPath = activityInfo.applicationInfo.nativeLibraryDir;
        DexClassLoader classLoader = new DexClassLoader(apkPath, dexOutputDir, libPath, this.getClassLoader());
        try {
            Class<?> clazz = classLoader.loadClass(packName + ".DynamicClass1");
            IDynamic object = (IDynamic) clazz.newInstance();
            String showTip = object.getTip();
            Toast.makeText(this, showTip, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载寄住APK的某个类
    private void dnyLoadPluCls() {
        String path = "plugindemo1-debug.apk";
        String dir = getFilesDir().getAbsolutePath();
        copyFileToDexDir(this, path, dir, path);
        String packName = "com.scau.beyondboy.plugindemo1";
        String apkPath = dir + File.separator + path;
        //获取当前应用dex输出目录
        String dexOutputDir = getApplicationInfo().dataDir;
        String libPath = getApplicationInfo().nativeLibraryDir;
        loadDynRes(apkPath);
        DexClassLoader classLoader = new DexClassLoader(apkPath, dexOutputDir, libPath, this.getClassLoader());
        try {
            Class<?> clazz = classLoader.loadClass(packName + ".DynamicClass1");
            IDynamic object = (IDynamic) clazz.newInstance();
            String showTip = object.getTip();
            Toast.makeText(this, showTip, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //不能加载未安装的apk
    private void pathClsLoad() {
        String path = "plugindemo1-debug.apk";
        String dir = getFilesDir().getAbsolutePath();
        copyFileToDexDir(this, path, dir, path);
        String packName = "com.scau.beyondboy.plugindemo1";
        String apkPath = dir + File.separator + path;
        String libPath = getApplicationInfo().nativeLibraryDir;
        PathClassLoader classLoader = new PathClassLoader(apkPath, libPath, this.getClassLoader());
        try {
            Class<?> clazz = classLoader.loadClass(packName + ".DynamicClass1");
            IDynamic object = (IDynamic) clazz.newInstance();
            String showTip = object.getTip();
            Toast.makeText(this, showTip, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //从Assets拷贝dex到dirpath路径
    private void copyFileToDexDir(Context context, String path, String dirpath, String apkName) {
        InputStream inputStream;
        BufferedInputStream buffer = null;
        File file;
        FileOutputStream out = null;
        try {
            inputStream = context.getAssets().open(path);
            buffer = new BufferedInputStream(inputStream);
            byte[] content = new byte[1024];
            file = new File(dirpath, apkName);
            out = new FileOutputStream(file);
            int length = -1;
            while ((length = buffer.read(content)) != -1) {
                out.write(content, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(buffer);
            StreamUtil.close(out);
        }
    }

    //加载插件资源
    private void dnyLoadPluRes() {
        String path = "plugindemo1-debug.apk";
        String dexpath = getApplicationInfo().dataDir + File.separator + "plugindemo1-debug.dex";
        String dir = getFilesDir().getAbsolutePath();
        copyFileToDexDir(this, path, dir, path);
        String packName = "com.scau.beyondboy.plugindemo1";
        String apkPath = dir + File.separator + path;
        //获取当前应用dex输出目录
        String dexOutputDir = getApplicationInfo().dataDir;
        String libPath = getApplicationInfo().nativeLibraryDir;
        loadDynRes(apkPath);
        DexClassLoader classLoader = new DexClassLoader(apkPath, dexOutputDir, libPath, this.getClassLoader());
        try {
            Class<?> clazz = classLoader.loadClass(packName + ".DynamicClass1");
            IDynamic object = (IDynamic) clazz.newInstance();
            Toast.makeText(this, object.getString(mResources), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mResources==null?super.getResources():mResources;
    }
}
