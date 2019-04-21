package life.lch.testsecond;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class addAccount {
    static Connection connection;
    static Statement sql;
    Context mContext;
    Activity activity;
    ResultSet resultSet;
    int a;
    private static final String TAG = "addAccount";
    //得到连接
    public static Connection getConnection() {
        Log.d(TAG, "尝试连接");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.d(TAG, "数据库驱动启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "启动失败");
        }
        try {
            connection= DriverManager.getConnection("jdbc:mysql:"+"//39.108.58.9:3306/websites","root","root");
            Log.d(TAG, "连接成功");
        } catch (Exception e) {
            e.printStackTrace();	}finally {
            Log.d(TAG, "zheshi="+connection);
        }


        return connection;
    }
    public void putAccount(final Activity activity,final Context context,
                           final String user_account,
                           final String user_password,final CallbackProgressBar callbackProgressBar
                           ){

        mContext=context;
        this.activity=activity;
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection=getConnection();
                try {
                    sql=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    //向数据库查询该账户
                    resultSet=sql.executeQuery("select*from users where user_account ='"+user_account+"'");

                    //当resultSet.first()有数据为True，无数据为False
                    //判断账户是否已经存在
                    if (resultSet.first()){
                    Message msg=new Message();
                    msg.what=3;
                    mHandler.sendMessage(msg);

                    }else{
                        a=sql.executeUpdate("INSERT INTO users (user_account,user_password)\r\n" +
                                "VALUES ('"+user_account+"','"+user_password+"');");
                        if (a==1) {
                            Log.d(TAG, "添加成功");
                            Message msg=new Message();
                            msg.what=1;
                            mHandler.sendMessage(msg);

                        }else {
                            Log.d(TAG, "添加失败");
                            Message msg=new Message();
                            msg.what=2;
                            mHandler.sendMessage(msg);

                        }
                    }
                    callbackProgressBar.closeBar();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(connection!=null){
                        try {
                            connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if(sql!=null){
                        try {
                            sql.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();


    }
    public void vertify(final Activity activity, final Context context, final String Vaccount,
                        final String Vpassword,final CallbackSave callbackSave){
        mContext=context;
        this.activity=activity;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "获取连接");
                connection=getConnection();
                try {
                    sql=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    resultSet=sql.executeQuery("select*from users where user_account ='"+Vaccount+"'");

                    if (resultSet.first()){
                        if (resultSet.getString("user_password").equals(Vpassword)){//密码正确
                            callbackSave.onFinish();//回调方法，
                            Message message=new Message();
                            message.what=6;
                            mHandler.sendMessage(message);
                        }else {
                            Message message=new Message();
                            message.what=5;
                            mHandler.sendMessage(message);
                        }
                    }else {
                        //账户不存在
                        Message message=new Message();
                        message.what=4;
                        mHandler.sendMessage(message);
                    }
                    callbackSave.onClose_LoginBar();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                   toastMessage("注册成功");
                    Intent intent=new Intent(mContext,LoginActivity.class);
                    mContext.startActivity(intent);
                    activity.finish();

                    break;
                case 2:
                    toastMessage("添加失败");
                    break;
                case 3:
                  toastMessage("该账户已经存在");
                    break;
                case 4:
                    toastMessage("该账户不存在");
                    break;
                case 5:
                    toastMessage("密码错误");
                    break;
                case 6:
                   toastMessage("登录成功");
                    Intent login=new Intent(mContext,Sq.class);
                    mContext.startActivity(login);
                    activity.finish();
                     break;
                default:
                    break;

            }
        }
    };
    private void toastMessage(String string){
        Toast toast = Toast.makeText(mContext, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -400);
        toast.show();
    }
}