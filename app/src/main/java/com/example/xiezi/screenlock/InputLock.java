package com.example.xiezi.screenlock;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 蝎子莱莱123 on 2015/12/5.
 */
public class InputLock extends AppCompatActivity {

    private GetureLock lock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputlock);

        SharedPreferences sp=getSharedPreferences("password",this.MODE_PRIVATE);
        final String password=sp.getString("password","");


        lock=(GetureLock)findViewById(R.id.lock);
        lock.setOnDrawFinishedListener(new GetureLock.OnDrawFinishedListener() {
            @Override
            public boolean OnDrawFinished(List<Integer> passList) {
                StringBuffer sb=new StringBuffer();
                for(Integer p:passList){
                    sb.append(p);
                }
                if(sb.toString().equals(password)){
                    Toast.makeText(InputLock.this,"Yes",Toast.LENGTH_SHORT).show();
                    lock.reset();
                    return true;
                }else{
                    Toast.makeText(InputLock.this,"No",Toast.LENGTH_SHORT).show();
                    lock.reset();
                    return false;
                }

            }
        });
    }
}
