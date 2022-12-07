package com.example.bookstore.ServiceImpl;

import com.example.bookstore.Service.TimerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope("session")
public class TimerServiceImpl implements TimerService {
    Date beginTime;

    @Override
    public boolean setTimer() {
        beginTime=new Date();
        return true;
    }

    @Override
    public boolean endTimer() {
        beginTime=null;
        return true;
    }

    @Override
    public Long getRemainTime() {
        if (beginTime!=null)
        {
            return new Date().getTime()-beginTime.getTime();
        }
        return new Long(0);
    }
}
