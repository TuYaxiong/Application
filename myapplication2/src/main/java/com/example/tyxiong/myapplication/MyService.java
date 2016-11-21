/*
*Service服务:与Activity最相似;都继承了Context;有自己的生命周期;也可作为可执行的程序.
*区别是运行在后台,不会有界面.
* 使用中如果需要用户交互应该用Activity;否则应该选用Service.
* 生命周期方法5个:onCreate() onBind() onStartCommand() onUnbind() onDestroy()
* onCreate() Service第一次实例化回调
* onBind() 该方法返回IBinder对象,用于应用程序与Service通信;必须重官吏的方法,通常继承Binder()子类来实现自己的IBinder对象.
* onStartCommand()当调用startService()方法启动Service时会回调方法.
* onUnbind()Service上的客户端都断开连接时回调
* onDestroy()Service被关闭时回调.
*
*同样使用Service需要继承基类或其子类 IntentService
* 使用步骤2
*   1 定义继承Service的子类,重写其周期方法 onBind()为必须重写的方法.
*   2 在AndroidManifest.xml中配置<service name:> 四大组件须显式配置.
*
*   Android5.0开始,要求须显式Intent启动Service.
*   启动关闭(配套使用的)Service startService()    stopService() 这样启动的Service与启动它的客户端没有什么联系.
*                               bindService()   unBindService()这样启动的Service与客户端绑定在一起了,当
*                               访问者退出,Service与退出.
*
*  当bindService(ServiceConnection)方法启动, onBinder()方法返回的IBinder对象会给到 ServiceConnection对象onServiceConnected()方法
*
*  当客户端连接到Service,onServiceConnected()被回调;异常中止回调onServiceDisconnected();(正常unBindService()不会回调)
*
*生命周期分2种:客户端调用startService()方法-->onCreate()回调-->onStartCommand()回调-->调用stopService()-->onDestroy()回调.
* 客户端调用bindService()方法-->onCreate()回调-->onBind()回调-->unBindService()-->onUnBind()回调-->onDestroy()回调
*
* Service有2缺陷: 与所在的应用为同一进程;不是单独的线程,不能进行耗时任务.
*
* IntentService:继承了Service, 5个优点:
*   1 用队列的形式来处理Intent.
*   2 单独的线程处理onHandlerIntent()方法
*   3 当Intent处理完成,自动停止.
*   4 重写了onBind()方法 onStartCommand()方法
*   5 使用IntentService只需要实现OnHandlerIntent()方法即可...(由于会单独线程,无须担心阻塞线程或ANR)
*
* */

package com.example.tyxiong.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


public class MyService extends IntentService {


    public MyService() {
        super("MyService");//指定该线路线线程的线线程名...
    }


    @Override
    protected void onHandleIntent(final Intent intent) {//该方法不会阻塞UI线程，也不会ANR, 单独的线程.
        Log.w("xxx", intent.getStringExtra("boot"));
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), intent.getStringExtra("boot"),
                        Toast.LENGTH_SHORT).show();

            }
        });//这里不能直接处理Toast,隐藏显示的时候,onHandleIntent()线程挂了,需要交给主线程的Handler处理.


    }
}

