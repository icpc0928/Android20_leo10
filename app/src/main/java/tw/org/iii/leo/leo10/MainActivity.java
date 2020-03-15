package tw.org.iii.leo.leo10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private File sdroot,approot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        //如果有多個要求全縣的話寫這裡
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);


            }
         else {
            // Permission has already been granted
            Log.v("leo","debug1");
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.v("leo","debug2");
                init();
            }else{
                Log.v("leo","debug3");
                //不願意授權的話直接卸載
                finish();
            }
        }
    }

    //當有拿到授權的話做的事情
    private void init(){
        String state = Environment.getExternalStorageState();
        Log.v("leo",state);  //mounted or removed  掛載 或 移除
        sdroot = Environment.getExternalStorageDirectory();
        Log.v("leo",sdroot.getAbsolutePath());
        //getAbs..Path 取得絕對路徑

        approot = new File(sdroot,
                "Android/data/"+ getPackageName());
        if (!approot.exists()){
            //如果這個路徑不存在就幫我新增 用mkdirs 加s會自動新增 沒加就沒有
            approot.mkdirs();
        }
    }
//sdroot / 內部檔案都可以寫 test1寫在sdroot/內   test2寫在sdroot/Android/data/tw.org...leo10/內
    public void test1(View view) {
        try {
            FileOutputStream fout = new FileOutputStream(sdroot.getAbsoluteFile()+"/001.txt");
            fout.write("Hello,world".getBytes());
            fout.close();
            fout.flush();
            Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Log.v("leo",e.toString());
        }
    }
    public void test2(View view) {
        try {
            FileOutputStream fout = new FileOutputStream(approot.getAbsoluteFile()+"/001.txt");
            fout.write("Hello,world".getBytes());
            fout.close();
            fout.flush();
            Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Log.v("leo",e.toString());
        }
    }

    public void test3(View view) {
        try{
            FileInputStream fin = new FileInputStream(sdroot.getAbsoluteFile()+"/001.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            String line = reader.readLine();
            fin.close();
            Log.v("leo",line);

        }catch (Exception e){
            Log.v("leo",e.toString());
        }
    }

    public void test4(View view) {
        try{
            FileInputStream fin = new FileInputStream(approot.getAbsoluteFile()+"/001.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            String line = reader.readLine();
            fin.close();
            Log.v("leo",line);

        }catch (Exception e){
            Log.v("leo",e.toString());
        }
    }

    public void test5(View view) {
        //公用的路徑  然後尋訪
        File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
           //存在  且  是一個資料夾
        if(musicDir.exists() && musicDir.isDirectory()){
            File[] musics = musicDir.listFiles();
            for(File music : musics){
                Log.v("leo",music.getAbsolutePath());
            }
        }
    }
}
