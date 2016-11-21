/* BroadcastReceiver广播接收者...3句概述:
    1 全局广监听器.用于响应应用程序发出的Broadcast(广播)
    2 可实现客户端通过startService()启动的Service进行通信.
    3 本质上是一个系统级的监听器.与前面监听级区别(程序级,与应用同进程,随应用结束结束);系统级监听器有自己的进程.

 使用分2步:
    1 定义继承BroadcastReceiver的子类,重写onReceiver()方法.
    2 在AndroidManifest.xml中显示配置元素3属性<receiver name: action:(intent_filter下) priority:(-1000-1000)>
    当然也可在Java代码中进行指定可配置的Intent
            IntentFilter filter=new IntentFilter("action属性");
            BroadcastReceiver receiver=new BroadcastReceiver();
             registerReceiver(receiver,filter);

   应用发送广播分2步:
    1 创建Intent对象(指定要启动的组件),设置action参数
    2 sendBroadcast()/发送普通广播 sendOrderingBroadcast()/发有序广播. 若找不到接收对象,不会有任何问题.

    广播过程:广播事件(发出广播)-->实例化BroadcastReceiver对象-->执行onReceiver()-->销毁对象
    注意:onReceiver()方法内不能进行耗时任务,若有应考虑用Intent启动一个Service,而非新线程(进程里没有组件会优先被结束进程)

    广播分2类:普通广播(向四周炸开,大家同时收到);有序广播(sendOrdering()方法.):根据BroadcastReceiver的priority属性优先级排序.
    传播3句:
        1 优先级高的BroadcastReceiver最先接收到Broadcast
        2 对象可以调用abortBroadcast()方法,取消Broadcast继续向下传播,优先级低的将无法接收到.
        3 对象可以调用setResultExtras();将处理结果存入到Broadcast中,下一接收者可getResultExtras(true)方法取出上一结果.

接收系统的广播消息:1句,Android应用的大量事件都会向外发出标准广播.
          常用的广播Action常量有(查Intent)
           ACTION_BOOT_COMPLETED/系统启动完成
           ACTION_BATTERY_CHANGED/电池电量改变
           ACTION_BATTERY_LOW/电池电量低

 通过BroadcastReceiver监听特殊的广播即可随系统执行特定的操作.

 开机自动运行:系统启动完成广播-->3步
    1 定义BroadcastReceiver子类.
    2 注册BroadcastReceiver,能匹配的Intent,action属性加上ACTION_BOOT_COMPLETED
    3 加权限



*/
package com.example.tyxiong.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED: {
                Intent intent1 = new Intent(context, MyService.class);
                intent1.putExtra("boot", "开机完成>>>>");
                context.startService(intent1);
            }
            case "android.provider.Telephony.SMS_RECEIVED":

            {
                Log.w("xxx", "收到短信>>>");
            }
            case Intent.ACTION_TIME_CHANGED: {

                Log.w("xxx", "日期更改>>>");
                Intent intent1 = new Intent(context, MyService.class);
                intent1.putExtra("boot", "日期更改>>>>");
                context.startService(intent1);


            }

        }
    }


}




