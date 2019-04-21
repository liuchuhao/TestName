package life.lch.testsecond;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    private EditText accountEdit;
    private EditText accountPassword;
    private Button login;
    private SharedPreferences sRead;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPassword;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sRead= PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit=findViewById(R.id.account);
        accountPassword=findViewById(R.id.password);
        rememberPassword=findViewById(R.id.rememberPassword);
        progressBar=findViewById(R.id.login_bar);
        login=findViewById(R.id.login);
        boolean isRemeber=sRead.getBoolean("remember_password",false);
        if (isRemeber)
        {
            //提取记住的账号和密码
            String account=sRead.getString("account","");
            String password=sRead.getString("password","");
            accountEdit.setText(account);
            accountPassword.setText(password);
            rememberPassword.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String account=accountEdit.getText().toString();
                final String password=accountPassword.getText().toString();
                if (progressBar.getVisibility()==View.GONE){
                    progressBar.setVisibility(View.VISIBLE);
                }
                addAccount a=new addAccount();
                a.vertify(LoginActivity.this, LoginActivity.this, account,
                        password, new CallbackSave() {
                            @Override
                            public void onClose_LoginBar() {
                                progressBar.setVisibility(View.GONE);//关闭进度条
                            }

                            @Override//使用接口将方法传过去：回调
                            public void onFinish() {
                                editor=sRead.edit();
                                if (rememberPassword.isChecked()){
                                    editor.putBoolean("remember_password",true);
                                    editor.putString("account",account);
                                    editor.putString("password",password);
                                }else{
                                    editor.clear();
                                }
                                editor.apply();

                            }
                        });

            }
        });
    }
}
