package net.saga.android.exaltedclient.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;

import net.saga.android.exaltedclient.R;
import net.saga.mooks.mookscommon.constants.Terrain;
import net.saga.mooks.mookscommon.message.Point;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author summerspittman
 */
public class MapTile {

    private List<ClientActor> actors = new ArrayList<ClientActor>();

    private Bitmap draw;
    private int tileSize;
    private Context context;
    private boolean dirty = true;
    private boolean touched = false;
    private final Point location;
    private Terrain terrain;

    public Point getLocation() {
        return location;
    }

    public MapTile(int tileSize, Context context, int x, int y) {
        this.context = context;
        this.tileSize = tileSize;
        dirty = true;
        location = new Point(x, y);
    }

    private void drawActors(final Canvas canvas) {
        ArrayList<ClientActor> localActors = new ArrayList<ClientActor>(this.actors);

        for (ClientActor ca : localActors) {
            Drawable tile = ca.getDrawable(this.context);
            tile.setBounds(0,0,32,32);
            tile.draw(canvas);
        }

    }


    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
        dirty = true;
    }

    public void pushActor(ClientActor ca) {
        this.actors.add(ca);
        dirty = true;
    }

    public ClientActor peekActor() {
        if (actors.size() > 0) {
            return this.actors.get(this.actors.size() - 1);
        }
        return null;
    }

    public ClientActor popActor() {
        if (actors.size() > 0) {
            ClientActor toReturn = this.actors.remove(this.actors.size() - 1);
            dirty = true;
            return toReturn;
        }
        return null;

    }

    public void remove(ClientActor cs) {
        this.actors.remove(cs);
        dirty = true;
    }

    public List<ClientActor> getActors() {
        return new ArrayList<ClientActor>(actors);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getBitmap() {
        if (dirty) {
            Drawable tile;
            if (terrain == Terrain.IMPASSIBLE) {
                tile =this.getContext().getResources().getDrawable(R.drawable.ic_launcher);
            } else {
                tile =this.getContext().getResources().getDrawable(R.drawable.ic_launcher);
            }
            Canvas canvas;
            draw = Bitmap.createBitmap(tileSize, tileSize, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(draw);
            tile.setBounds(0, 0, tileSize, tileSize);
            tile.draw(canvas);
            drawActors(canvas);
            if (touched) {
                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                paint.setStyle(Style.FILL);
                canvas.drawCircle(16, 16, 16, paint);
            }
            dirty = false;

        }
        return draw;
    }

    public void fireTouch() {
        touched = !touched;
        dirty = true;
    }

}