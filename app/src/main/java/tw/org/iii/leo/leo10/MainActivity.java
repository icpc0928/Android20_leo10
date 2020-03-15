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

import java.io.File;

public class MainActivity extends AppCompatActivity {

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
        File sdroot = Environment.getExternalStorageDirectory();
        Log.v("leo",sdroot.getAbsolutePath());
        //getAbs..Path 取得絕對路徑
    }
}
