package com.scau.beyondboy.plugindemo1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    public static final String CLA_PATH = "com.scau.beyondboy.plugindemo1.DynamicClass1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // DynamicClass1.starLoad();
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  RefClassLoad();
               // pathClaLoad();
//                DexClaLoad();
            }
        });
    }
    private void pathClaLoad(){
        String dexDir=getApplicationInfo().dataDir;
        PathClassLoader pathClassLoader=new PathClassLoader(dexDir,getClassLoader());
        try{
            Class<?> clazz=pathClassLoader.loadClass(CLA_PATH);
            Object object=clazz.newInstance();
            Method method=clazz.getMethod("getTip");
            String showTip=(String)method.invoke(object);
            Toast.makeText(this,showTip,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void RefClassLoad(){
        try {
            Class<?> clazz=Class.forName("dalvik.system.DexPathList");
            Class<?> clazz1=Class.forName("dalvik.system.BaseDexClassLoader");
            Field filed=clazz1.getDeclaredField("pathList");
            filed.setAccessible(true);
            Method method=clazz.getDeclaredMethod("findClass",String.class,List.class);
            Class a=(Class) method.invoke(filed.get(getClassLoader()),CLA_PATH,new ArrayList<Throwable>());
            Toast.makeText(this,((DynamicClass1)a.newInstance()).getTip(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DexClaLoad(){
        String packName=getApplicationInfo().packageName;
        String apkPath=getApplicationInfo().sourceDir;
        //获取当前应用dex输出目录
        String dexOutputDir=getApplicationInfo().dataDir;
        String libPath=getApplicationInfo().nativeLibraryDir;
        DexClassLoader classLoader=new DexClassLoader(apkPath,dexOutputDir,libPath,this.getClassLoader());
        try{
            Class<?> clazz=classLoader.loadClass(CLA_PATH);
            Object object=clazz.newInstance();
            Toast.makeText(this,((DynamicClass1)object).getTip(),Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void otherClassLoad(){
        String packName=getApplicationInfo().packageName;
        String apkPath=getApplicationInfo().sourceDir;
        //获取当前应用dex输出目录
        String dexOutputDir=getApplicationInfo().dataDir;
        String libPath=getApplicationInfo().nativeLibraryDir;
        DexClassLoader classLoader=new DexClassLoader(apkPath,dexOutputDir,libPath,this.getClassLoader());
        try{
            Class<?> clazz=classLoader.loadClass(packName+".DynamicClass1");
            Object object=clazz.newInstance();
            Log.i(MainActivity.class.getName(),(object instanceof DynamicClass1)+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
