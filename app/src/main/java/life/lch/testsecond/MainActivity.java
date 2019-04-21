package life.lch.testsecond;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Button summitTest;
    private EditText name1;
    private EditText name2;
    private TextView name_result;
    private String result;
    private List<String> list;
    private String str;
    private static final String TAG = "MainActivity";
    String s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        summitTest=findViewById(R.id.submit_name);
        name1=findViewById(R.id.name_1);
        name2=findViewById(R.id.name_2);
        name_result=findViewById(R.id.name_result);
        list=new ArrayList<>();
        summitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取输入的第一个字符
               s1= name1.getText().toString().substring(0,1);
               s2=name2.getText().toString().substring(0,1);
                Log.d(TAG, "s1="+s1);

                   //读取资源文件
                   try {
                       InputStream fileInputStream=getResources().openRawResource(R.raw.bjx);
                       InputStreamReader reader = new InputStreamReader(fileInputStream);
                       BufferedReader bufferedReader=new BufferedReader(reader);
                       String str;
                       while ((str=bufferedReader.readLine())!=null) {
                           //百家姓加入列表
                           list.add(str);
                       }

                           fileInputStream.close();
                           reader.close();
                           bufferedReader.close();

                   }catch (IOException e){
                       e.printStackTrace();
                   }
                   //查找字符在资源文件中的位置
               int i1=list.indexOf(s1);
                   int i2=list.indexOf(s2);
                   Log.d(TAG, "i1="+i1);
                   //算法
                   int a=i1+i2;
                   Log.d(TAG, "a="+a);
                   int b=a%3;
                   //判断
                   switch (b) {
                       case 0:
                           result="在初见时，十对有九对会发出火花，有如磁铁般的相互吸引、一见钟情，同时进展快速而浓烈。您俩的性格和观念近似，是恩爱火热又耀眼的组合。";
                           break;
                       case 1:
                           result="两种截然不同的类型，也几乎找不着什么共通性，好比建立在沙上的城堡一般，外表看似瑰丽浪漫，终究易因缺乏根基而难以久固。";
                           break;
                       case 2:
                           result="虽然是满分的天生一对，可是这要两人皆具成熟人格做为前提，否则两个都那么自我、个性强，难免时常会擦枪走火。";
                           break;
                       default:
                           break;
                   }

                Log.d(TAG, "result="+result);
               name_result.setText(result);
            }

        });
    }


    //菜单栏操作
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.login_admin:
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.login_reg:
                Intent intent1=new Intent(MainActivity.this,Reg.class);
                startActivity(intent1);
                break;
            case R.id.exit:
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("提示：");
                dialog.setMessage("您真的要离开吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCollector.finishAll();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            default:
                break;
        }
        return true;
    }

}
