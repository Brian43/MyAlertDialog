package myalertdialog.myalertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener
        ,MyDialogFragment.CallBack {
    private TextView textView;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builder2;
    private Map<String,Integer>map = new HashMap<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    public void init() {
        textView = (TextView) findViewById(R.id.mtv);
    }
    /*按下第一個按鈕*/
    public void clickAlertDialog(View view) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message)
                .setPositiveButton(R.string.positive, this)
                .show();
    }
    /*按下第二個按鈕*/
    public void clickAlertDialogYesNo(View view){
        builder2 = new AlertDialog.Builder(this);
        builder2.setMessage(R.string.message)
                .setPositiveButton(R.string.positive, this)
                .setNegativeButton(R.string.negative, this)
                .show();
    }
    /*用reference判斷是哪個Dialog */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (dialog == builder) {
            textView.setText(R.string.positive);
        }else{
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    textView.setText(R.string.positive);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    textView.setText(R.string.negative);
            }
        }
    }
    /*第三個按鈕*/
    public void clickAlertDialogYesNoCancle(View view){
        new AlertDialog.Builder(this)
                .setMessage(R.string.message)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(R.string.positive);
                    }
                })
                .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(R.string.negative);
                    }
                })
                .setNeutralButton(R.string.neutral, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(R.string.neutral);
                    }
                })
                .show();
    }
    /*第四個按鈕
    * 提供項目清單的Dialog*/
    public void clickAlertDialogItems(View view){
        /*取得xml string-array裡的資料*/
       final String[] response = getResources().getStringArray(R.array.response);
        new AlertDialog.Builder(this,android.R.style.Theme_Holo_Dialog_NoActionBar)
                .setTitle(R.string.message)
                /*設定清單將清單的資料注入*/
                .setItems(response, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(response[which]);
                    }
                })
                .show();
    }
    /*第五個按鈕
    * 項目可複選的Dialog*/
    public void clickAlertDialogMultiChoice(View view){
        /*每個項目的字串內容*/
        final String[] response = getResources().getStringArray(R.array.response);
        /*記錄每個項目的勾選狀態*/
        final boolean [] selected =  new boolean[response.length];

        new AlertDialog.Builder(this)
                .setTitle(R.string.message)
                /*設定多選將資料注入*/
                .setMultiChoiceItems(response, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    /*每次勾選或取消勾選時執行
                    * which 這次勾選了哪個項目
                    * isChecked 所點選的項目是否打勾*/
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selected[which]= isChecked;
                    }
                })
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder sb = new StringBuilder();
                        /*走訪selected陣列 將有勾選的項目字串加入sb裡*/
                        for (int i = 0; i < selected.length; i++) {
                            if (selected[i]) {
                                sb.append(response[i]).append("\n");
                            }
                        }
                        textView.setText(sb);
                    }
                })
                .setNegativeButton("無語", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(R.string.negative);
                    }
                })
                .show();
    }
    /*第六個按鈕
    項目多選一的Dialog*/
    private int mChoice ;

    public void clickAlertDialogSingleChoice(View view){
        final String[]response = getResources().getStringArray(R.array.response);
        new AlertDialog.Builder(this)
                .setTitle(R.string.message)
                .setSingleChoiceItems(response, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*此onClick執行完，which會消失，所以要記錄在欄位裡*/
                        mChoice = which;
                    }
                })
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(response[mChoice]);
                    }
                })
                .setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(R.string.cancle);
                    }
                })
                .show();
    }
    /*第七按鈕
    *自訂Dialog  DialogFragment */
    public void clickFragmentDialog(View view){
        /*建立自訂Dialog*/
        DialogFragment dialog = new MyDialogFragment();
        /*顯示Dialog*/
        dialog.show(getSupportFragmentManager(),"MyDialogFragment");
    }
    /*實現(implements)DialogFragment的介面(interface)*/
    @Override
    public void onInputComplete(String user_email, String password) {
        /*將登入者 注入字串陣列當中*/
        String[] data = new String[]{user_email};
        /*走訪陣列*/
        for(String key:data){
            /*判斷是不是同一個登入者*/
            if(map.containsKey(key)){
                /*Key相同時 loginCount++*/
                int loginCount = map.get(key);
                loginCount++;
                /*將key對應的loginCount 這就是登入的次數 注入回map裡*/
                map.put(key,loginCount);
            }else{
                /*第一次登入*/
                map.put(key,1);
            }
        }
        textView.setText("歡迎 " +user_email+"登入,登入"+map.get(user_email)+"次數");
        Toast.makeText(this, "Oops Password need secret", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNullInput(String msg) {
        textView.setText(msg);
    }
}
