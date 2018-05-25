package com.asg.ashish.rine;

public class Model {
    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;

    public int type;
    public String data;

    public Model(int type, String data)
    {
        this.type=type;
        this.data=data;

    }
}
