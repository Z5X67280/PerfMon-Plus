package xzr.perfmon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

public class MainActivity extends Activity {
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main=new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        setContentView(main);

        if(!FloatingWindow.do_exit){
            Toast.makeText(MainActivity.this,"PerfMon+ is running, please close it first.",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //Move from service:onCreate in order to show the supporting list
        RefreshingDateThread.cpunum=Tools.getCpuNum();
        FloatingWindow.linen=Support.CheckSupport();
        SharedPreferencesUtil.init(this);

        addview();

    }

    void addview(){
        {
            TextView textView=new TextView(this);
            textView.setText("CPU frequency monitoring: "+Tools.bool2text(Support.support_cpufreq));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("CPU load monitoring: "+Tools.bool2text(Support.support_cpuload));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("GPU frequency monitoring: "+Tools.bool2text(Support.support_adrenofreq));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("GPU load monitoring: "+Tools.bool2text(Support.support_adrenofreq));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("CPUBW frequency monitoring: "+Tools.bool2text(Support.support_cpubw));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("M4m frequency monitoring: "+Tools.bool2text(Support.support_m4m));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("Temperature monitoring: "+Tools.bool2text(Support.support_temp));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("RAM usage monitoring: "+Tools.bool2text(Support.support_mem));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("Current monitoring: "+Tools.bool2text(Support.support_current));
            main.addView(textView);
        }
        {
            TextView textView=new TextView(this);
            textView.setText("Some features may not be supported by your device");
            main.addView(textView);
        }
        {
            Button button = new Button(this);
            button.setText("Turn on PerfMon+");
            main.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    permissioncheck();
                    finish();
                }
            });
        }
        {
            Button button = new Button(this);
            button.setText("Settings");
            main.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    xzr.perfmon.Settings.creatDialog(MainActivity.this);
                }
            });
        }
        {
            Button button=new Button(this);
            button.setText("Set permissive SElinux");
            main.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Process process=new ProcessBuilder("su").redirectErrorStream(true).start();
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(process.getInputStream()));
                        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(process.getOutputStream());
                        outputStreamWriter.write("setenforce 0\nexit\n");
                        outputStreamWriter.flush();
                        String log="";
                        String cache;
                        while ((cache=bufferedReader.readLine())!=null){
                            log=log+cache+"\n";
                        }
                        if (log.equals("")){
                            Toast.makeText(MainActivity.this,"Success. Please restart the app.",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,log,Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this,"Failed to get root access.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        {
            TextView textView=new TextView(this);
            textView.setText("This operation can make some devices crash!\n");
            main.addView(textView);
        }
        {
            LinearLayout line=new LinearLayout(MainActivity.this);
            main.addView(line);
            {
                TextView textView = new TextView(this);
                textView.setText("Github ");
                line.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://github.com/xzr467706992/PerfMon-Plus");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }

            {
                TextView textView = new TextView(this);
                textView.setText("| Coolapk (Give me 5 stars review!!!) ");
                line.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://www.coolapk.com/apk/xzr.perfmon");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    void permissioncheck(){

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(MainActivity.this, FloatingWindow.class);
                startService(intent);
            } else {
                /*Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Toast.makeText(MainActivity.this,"需要取得权限以使用悬浮窗",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                */
                try {
                    Class clazz = Settings.class;
                    Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
                    Intent intent = new Intent(field.get(null).toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                    this.startActivity(intent);
                }
                catch (Exception e){}
            }
        } else {
            //SDK在23以下，不用管.
            Intent intent = new Intent(MainActivity.this, FloatingWindow.class);
            startService(intent);
        }
    }
}
