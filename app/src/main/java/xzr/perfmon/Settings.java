package xzr.perfmon;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

class Settings{
    private static AlertDialog dialog;
    static void creatDialog(Context context){
        dialog=new AlertDialog.Builder(context)
                .setView(settingsView(context))
                .setTitle("Settings")
                .create();
        dialog.show();
    }

    private static View settingsView(Context context){
        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        {
            LinearLayout line=new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView=new TextView(context);
            line.addView(textView);
            textView.setText("Refreshing Delay");

            EditText editText=new EditText(context);
            line.addView(editText);
            editText.setHint("Default");
            editText.setText(SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.delay, SharedPreferencesUtil.default_delay)+"");
            editText.setWidth(500);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try{
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.delay,Integer.parseInt(editable.toString())).commit();
                    }
                    catch (Exception e){
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.delay).commit();
                    }
                }
            });


        }
        {
            TextView textView=new TextView(context);
            linearLayout.addView(textView);
            textView.setText("* If refreshing delay too short will me make additional CPU load");
        }

        {
            LinearLayout line=new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView=new TextView(context);
            line.addView(textView);
            textView.setText("Window Width");

            EditText editText=new EditText(context);
            line.addView(editText);
            editText.setHint("Default");
            editText.setText(SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.width, SharedPreferencesUtil.default_width)+"");
            editText.setWidth(500);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try{
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.width,Integer.parseInt(editable.toString())).commit();
                    }
                    catch (Exception e){
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.width).commit();
                    }

                }
            });


        }

        {
            LinearLayout line=new LinearLayout(context);
            linearLayout.addView(line);

            TextView textView=new TextView(context);
            line.addView(textView);
            textView.setText("Window Height");

            EditText editText=new EditText(context);
            line.addView(editText);
            editText.setHint("Default");
            int height=SharedPreferencesUtil.sharedPreferences.getInt(SharedPreferencesUtil.height, SharedPreferencesUtil.default_height);
            if (height!=-1)
                editText.setText(height+"");
            editText.setWidth(500);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try{
                        SharedPreferencesUtil.sharedPreferences.edit().putInt(SharedPreferencesUtil.height,Integer.parseInt(editable.toString())).commit();
                    }
                    catch (Exception e){
                        SharedPreferencesUtil.sharedPreferences.edit().remove(SharedPreferencesUtil.height).commit();
                    }

                }
            });


        }

        return linearLayout;
    }
}
