package com.example.wanderlogix.logix;


import static java.security.AccessController.getContext;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.graphics.drawable.DrawableContainerCompat;

import com.example.wanderlogix.R;
import com.example.wanderlogix.user.User;

import java.util.ArrayList;
import java.util.List;

public class LogixDatabase {
    User user=new User();
    List<Logix> resource=new ArrayList<Logix>();
    public LogixDatabase(){
        //simulated data
        Bitmap img1= BitmapFactory.decodeFile("https://img1.baidu.com/it/u=413643897,2296924942&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        for(int i=0;i<2;i++){
        Logix t=new Logix();
        t.content="dawadwadawdw"+i;
        t.image=img1;
        t.Introduce="dawadwadad"+i;
        t.title="dwawda"+i;
        t.titleImgae=img1;
        resource.add(t);
        }
    }

    public int getCount(){
        //Todo
        return 2;
    }
    public Logix getLogix(int postiion){
        //Todo
        return resource.get(postiion);
    }
}
