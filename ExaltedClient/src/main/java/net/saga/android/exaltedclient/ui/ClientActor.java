package net.saga.android.exaltedclient.ui;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.math.BigInteger;

public interface ClientActor {


    public Point getLocation();
    public void setLocation(Point point);

    public Drawable getDrawable(Context context);
    public BigInteger getId();
}