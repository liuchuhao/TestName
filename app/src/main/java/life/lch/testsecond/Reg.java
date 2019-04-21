package life.lch.testsecond;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Reg extends BaseActivity {
    String ac;
    String pa;
    EditText account;
    EditText password;
    EditText yaoqingma;
    Button reg;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        account=findViewById(R.id.reg_account);
        password=findViewById(R.id.reg_password);
        yaoqingma=findViewById(R.id.yaoqingma);
        reg=findViewById(R.id.button_reg);
        progressBar = findViewById(R.id.progress_bar);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ac=account.getText().toString();
                pa=password.getText().toString();
                //过滤非法输入
                if (yaoqingma.getText().toString().equals("520")){
                    if (!TextUtils.isEmpty(ac)&&!TextUtils.isEmpty(pa)){

                   if (2<ac.length()&&ac.length()<12&&pa.length()>5&&pa.length()<12){
                       if (progressBar.getVisibility() == View.GONE)
                       {
                           progressBar.setVisibility(View.VISIBLE);

                       }
                               addAccount add=new addAccount();
                               add.putAccount(Reg.this, Reg.this, ac, pa, new CallbackProgressBar() {
                                   @Override
                                   public void closeBar() {
                                       progressBar.setVisibility(View.GONE);
                                   }
                               });

                   }else {
                      toastMessage("用户名长度3-11，密码长度6-11");
                   }

                    }else{
                        toastMessage("用户名或密码不能为空");
                    }
                }else {
                   toastMessage("请输入正确的邀请码");
                }
            }
        });
    }
    private void toastMessage(String string){
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -400);
        toast.show();
    }
}
