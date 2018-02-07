package com.example.mnecas.helpinghand;

/**
 * Created by mnecas on 7.2.18.
 */

public class Device {
    public String ip;
    public String state;
    public String name;
    public Device(String state,String ip,String name){
        this.ip=ip;
        this.state=state;
        this.name=name;
    }
}
