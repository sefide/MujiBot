package com.example.sejini.mujibot.inner;

import android.os.Handler;
import android.os.SystemClock;

import com.example.sejini.mujibot.MainActivity;
import com.example.sejini.mujibot.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sejini on 2016-12-18.
 */

//배고픔 4시간마다
//졸림 3시간마다
//1초 1000 10초 10000
    //60초 1분 60000
    //60분 1시간 360000
    //3시간  10800000
    //4시간  14400000

public class PhysiologicalNeed extends Thread{
    Handler handler;
    int count = 0;
    Timer timer_sleepy;
    Timer timer_hungry;
    public static boolean isPhysiologic = false;

    public PhysiologicalNeed(Handler handler){
        this.handler = handler;
    }

    public void run(){
        while(true){

            if(count %2 == 1){  //졸림 발현
                handler.post(new Runnable() {
                    public void run() {
                        startSleepyTimer();
                        MainActivity.mujiBot_sleepy.setImageResource(R.drawable.sleepy2);
                        isPhysiologic = true;
                    }
                });
                SystemClock.sleep(10000);

            }
            else if(count %2 == 0){
                handler.post(new Runnable() {
                    public void run() {   //배고픔 발현
                        startHungryTimer();
                        MainActivity.mujiBot_hungry.setImageResource(R.drawable.hungry2);
                        isPhysiologic = true;
                    }
                });
                SystemClock.sleep(10000);

            }

            count++;

            handler.sendEmptyMessage(0);
            try{
                Thread.sleep(10000);
                //감정우선순위 최대값 10 이내로 차이나는 거 고르기
            }catch (InterruptedException e){
                e.printStackTrace();

            }
        }
    }

    private void startSleepyTimer(){  //졸림 발현 지속 시간
        timer_sleepy = new Timer();
        timer_sleepy.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    public void run() {
                        MainActivity.mujiBot_sleepy.setImageResource(R.drawable.sleepy_state);
                        isPhysiologic = false;
                    }
                });
                SystemClock.sleep(1000);
            }
        }, 3000);   //3초 뒤
     }

    private void startHungryTimer(){   //배고픔 발현 지속 시간
        timer_hungry = new Timer();
        timer_hungry.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    public void run() {
                        MainActivity.mujiBot_hungry.setImageResource(R.drawable.hunger_state);
                        isPhysiologic = false;
                    }
                });
                SystemClock.sleep(1000);
            }
        }, 4000);  //3초 뒤
    }

    public void stopTimer()
    {
        timer_hungry.cancel();
        timer_sleepy.cancel();

    }
}