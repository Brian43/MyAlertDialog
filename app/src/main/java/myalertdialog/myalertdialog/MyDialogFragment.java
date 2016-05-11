package myalertdialog.myalertdialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class MyDialogFragment extends DialogFragment {
    private EditText edit_email,edit_password;

    /*無參數建構子是必須的唷*/
    public MyDialogFragment() {
    }
    /*將資料傳給Activity*/
    public interface CallBack{
        /*返回的資料 user_email和password*/
        void onInputComplete(String user_email,String password);
        void onNullInput(String msg);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*透過inflater，讀取fragment.xml資源來生成view
        * 取得 inflater*/
        LayoutInflater inflater = getActivity().getLayoutInflater();
        /*從fragment.xml取得自訂的畫面
        * inflate(resource,viewGroup)*/
        View view = inflater.inflate(R.layout.fragment_my_dialog, null);
        /*初始化 edit_view*/
        edit_email = (EditText) view.findViewById(R.id.edite_email);
        edit_password = (EditText) view.findViewById(R.id.edite_password);
        /*建立AlertDialog*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /*設定自訂的View*/
        builder.setView(view)
                .setPositiveButton("登入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            /*在點擊按鈕時，把activity強轉成我們定義的interface 將用戶輸入的數據返回*/
                        CallBack call = (CallBack) getActivity();
                            /*取得email password的Text 放入onInputComplete裡*/
                        call.onInputComplete(edit_email.getText().toString(),
                                edit_password.getText().toString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CallBack call = (CallBack) getActivity();
                        String msg ="登入取消";
                        call.onNullInput(msg);
                    }
                });
        /*返回所建立的Dialog*/
        return builder.create();
    }
}
