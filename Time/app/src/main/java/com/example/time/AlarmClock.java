package com.example.time;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;

public enum AlarmClock {
    INSTANCE;
    public void createAlarm(Context context, String message, int hour, int minutes, int resId) {
        ArrayList<Integer> testDays = new ArrayList<>();
//        testDays.add(Calendar.MONDAY);//周一
        String packageName = context.getPackageName();
        Uri ringtoneUri = Uri.parse("android.resource://" + packageName + "/" + resId);

        Intent intent = new Intent(android.provider.AlarmClock.ACTION_SET_ALARM)
                //闹钟的小时
                .putExtra(android.provider.AlarmClock.EXTRA_HOUR, hour)
                //闹钟的分钟
                .putExtra(android.provider.AlarmClock.EXTRA_MINUTES, minutes)
                //响铃时提示的信息
                .putExtra(android.provider.AlarmClock.EXTRA_MESSAGE, message)
                //用于指定该闹铃触发时是否振动
                .putExtra(android.provider.AlarmClock.EXTRA_VIBRATE, true)
                //一个 content: URI，用于指定闹铃使用的铃声，也可指定 VALUE_RINGTONE_SILENT 以不使用铃声。
                //如需使用默认铃声，则无需指定此 extra。
                .putExtra(android.provider.AlarmClock.EXTRA_RINGTONE, ringtoneUri)
                //对于一次性闹铃，无需指定此 extra
                .putExtra(android.provider.AlarmClock.EXTRA_DAYS, testDays)
                //如果为true，则调用startActivity()不会进入手机的闹钟设置界面
                .putExtra(android.provider.AlarmClock.EXTRA_SKIP_UI, true);
        if(intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
