package msplab4.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class MidInterval extends NotificationBroadcasterSupport implements Serializable, MidIntervalMBean {
    long lastPointTime;

    List<Long> AllInterval;
    public MidInterval(){
        lastPointTime = System.currentTimeMillis();
        AllInterval = new ArrayList<>();
    }

    public long getMidInterval(){
        long midInterval = 0;
        long answer;
        for(long pointInterval : AllInterval){
            //сумма
            midInterval += pointInterval;
        }
        if (AllInterval.size() == 0){
            answer = 0;
        } else {
            long ans0 =  (midInterval/(AllInterval.size()));
            answer = TimeUnit.MILLISECONDS.toSeconds(ans0);
        }
        return answer;
    }
    @Override
    public void addPoint(){
        long currenShotTime = System.currentTimeMillis();
        AllInterval.add(currenShotTime - lastPointTime);
        lastPointTime = currenShotTime;
    }

}


