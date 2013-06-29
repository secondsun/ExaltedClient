package net.saga.android.exaltedclient.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.saga.android.exaltedclient.R;
import net.saga.mooks.mookscommon.constants.Terrain;
import net.saga.mooks.mookscommon.message.Point;

import java.util.ArrayList;

/**
 * TileView: a View-variant designed for handling arrays of "icons" or other
 * drawables.
 *
 */
public class TileView extends View {

    /**
     * Parameters controlling the size of the tiles and their range within view.
     * Width/Height are in pixels, and Drawables will be scaled to fit to these
     * dimensions. X/Y Tile Counts are the number of tiles that will be drawn.
     */

    protected static int mTileSize = 32;

    protected static int mXTileCount = 10;
    protected static int mYTileCount = 10;
    private RefreshHandler mRedrawHandler = new RefreshHandler();

    private static int mXOffset;
    private static int mYOffset;

    /**
     * A two-dimensional array of integers in which the number represents the
     * index of the tile that should be drawn at that locations
     */
    private int[][] mTileGrid;
    private ArrayList<MapTile> mTiles = new ArrayList<MapTile>();

    public ArrayList<MapTile> getMapTiles() {
        return mTiles;
    }

    private final Paint mPaint = new Paint();



    public boolean running = true;

    public TileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TileView);

        mTileSize = a.getInt(R.styleable.TileView_tileSize, 32);

        a.recycle();
    }

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TileView);

        mTileSize = a.getInt(R.styleable.TileView_tileSize, 32);

        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mTileSize = Math.min(getWidth(), getHeight()) / 10;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mXTileCount = (int) 10;
        mYTileCount = (int) 10;

        mTileSize = Math.min(getWidth(), getHeight()) / 10;

        mXOffset = ((w - (mTileSize * mXTileCount)) / 2);
        mYOffset = ((h - (mTileSize * mYTileCount)) / 2);

        mTileGrid = new int[mXTileCount][mYTileCount];
        mTiles = new ArrayList<MapTile>(mXTileCount * mYTileCount);

        clearTiles();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("measure", Math.min(getWidth(), getHeight()) / 10 + "");
        setMeasuredDimension(mTileSize * mXTileCount + mXOffset, mTileSize
                * mYTileCount + mYOffset);

    }



    /**
     * Resets all tiles to 0 (empty)
     *
     */
    public void clearTiles() {
        if (mTiles.size() == 0) {
            for (int x = 0; x < mXTileCount; x++) {
                for (int y = 0; y < mYTileCount; y++) {
                    setTile(new MapTile(mTileSize, getContext(),x,y), x, y);
                }
            }
        } else {
            for (MapTile tile : mTiles) {
                while (tile.peekActor() != null) {
                    tile.popActor();
                }
                tile.setTerrain(Terrain.PASSIBLE);
            }
        }
    }

    /**
     * Used to indicate that a particular tile (set with loadTile and referenced
     * by an integer) should be drawn at the given x/y coordinates during the
     * next invalidate/draw cycle.
     *
     * @param tile
     * @param x
     * @param y
     */
    public void setTile(MapTile tile, int x, int y) {
        mTileGrid[x][y] = mTiles.size();
        mTiles.add(tile);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x = 0; x < mXTileCount; x += 1) {
            for (int y = 0; y < mYTileCount; y += 1) {
                if (mTileGrid[x][y] > -1) {
                    canvas.drawBitmap(mTiles.get(mTileGrid[x][y]).getBitmap(),
                            mXOffset + x * mTileSize, mYOffset + y * mTileSize,
                            mPaint);
                }
            }
        }

    }

    public MapTile getMapTile(Point p) {
        try {
            return this.mTiles.get(mTileGrid[p.getX()][p.getY()]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("getMapTile", p.toString() +":"+ p.getX() + ","+p.getY(), e);
            throw e;
        }catch (NullPointerException e) {
            Log.e("getMapTile", p.toString() +":"+ p.getX() + ","+p.getY(), e);
            throw e;
        }
    }



    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            TileView.this.invalidate();
            TileView.this.update();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    public void update() {
        if (this.running)
            mRedrawHandler.sleep(100);
    }

}