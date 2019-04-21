package life.lch.testsecond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Sq extends BaseActivity {
private EditText cha_name;
private RadioButton boy;
private RadioButton girl;
private Button cha_s;
private TextView cha_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sq);
cha_name=findViewById(R.id.Cha_name);
boy=findViewById(R.id.boy);
girl=findViewById(R.id.girl);
cha_s=findViewById(R.id.cha_start);
cha_r=findViewById(R.id.cha_result);

cha_s.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String s=cha_name.getText().toString();
        addAccount add=new addAccount();
        if (boy.isChecked()){
            //传入姓名，查询性别，textView(便于在线程中设置textView)
           add.chaNames(s,"names_one",cha_r);

        }
        if (girl.isChecked()){
            add.chaNames(s,"names_two",cha_r);
        }
    }
});

    }
}
