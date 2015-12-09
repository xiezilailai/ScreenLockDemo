package com.example.xiezi.screenlock;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 蝎子莱莱123 on 2015/12/5.
 */
public class SetLock extends AppCompatActivity {
    private GetureLock getureLock;
    private Button reset,save;
    private List<Integer>passListt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setlock);

        getureLock=(GetureLock)findViewById(R.id.view);
        reset=(Button)findViewById(R.id.button_reset);
        save=(Button)findViewById(R.id.button_save);
        getureLock.setOnDrawFinishedListener(new GetureLock.OnDrawFinishedListener() {
            @Override
            public boolean OnDrawFinished(List<Integer> passList) {
                if (passList.size() <= 3) {
                    Toast.makeText(SetLock.this,"too short",Toast.LENGTH_SHORT).show();
                    return false;
                }
                else{
                    passListt=passList;
                    return true;
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getureLock.reset();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passListt!=null){
                    StringBuffer sb=new StringBuffer();
                    for(Integer p:passListt){
                        sb.append(p);
                    }
                    SharedPreferences sp=SetLock.this.getSharedPreferences("password", SetLock.this.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("password",sb.toString());
                    editor.commit();

                    Toast.makeText(SetLock.this,"save successful",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
