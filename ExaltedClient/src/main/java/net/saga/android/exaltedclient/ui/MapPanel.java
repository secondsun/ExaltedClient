package net.saga.android.exaltedclient.ui;

import java.math.BigInteger;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MapPanel extends TileView {

//    private CommandHandle commandHandle;
//
//    public CommandHandle getCommandHandle() {
//        return commandHandle;
//    }
//
//    public void setCommandHandle(CommandHandle commandHandle) {
//        this.commandHandle = commandHandle;
//    }

    private ListView commandPanel;

    public ListView getCommandPanel() {
        return commandPanel;
    }

    public void setCommandPanel(ListView commandPanel) {
        this.commandPanel = commandPanel;
//        if (commandPanel.getAdapter() == null) {
//            commandPanel.setAdapter(new ArrayAdapter<CommandButton>(this
//                    .getContext(), R.layout.message));
//        }
    }

    /**
     * Constructs a MapPanel based on inflation from XML
     *
     * @param context
     * @param attrs
     */
    public MapPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        update();
    }

    public MapPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                float touchX = event.getX();
                float touchY = event.getY();
                int x = Math.round(touchX);
                int y = Math.round(touchY);
//                List<ClientActor> actors = super.getMapTile(
//                        new Point(x / mTileSize, y / mTileSize)).getActors();
//                if (commandHandle != null && commandHandle.getCommand() != null) {
//                    commandHandle.getCommand().execute(
//                            super.getMapTile(new Point(x / mTileSize, y
//                                    / mTileSize)));
//
//                } else {
//                    boolean clear = true;
//                    for (ClientActor actor : actors) {
//                        showActions(actor.getId(), actor, clear);
//                        clear = false;
//                    }
//                }
            }
        } catch (NullPointerException npe) {
            // swallow, something probably changed commandHandle when we weren't looking.
        }
        return super.onTouchEvent(event);
    }

//    @SuppressWarnings("unchecked")
//    public void showActions(BigInteger unitId, ClientActor component,
//                            boolean clear) {
//
//        commandHandle = new CommandHandle(unitId, component);
//
//        ArrayAdapter<CommandButton> actorAdapter = (ArrayAdapter<CommandButton>) commandPanel
//                .getAdapter();
//        if (clear)
//            actorAdapter.clear();
//        if (component instanceof Unit) {
//            actorAdapter.add(new MoveButton());
//            actorAdapter.add(new GroupButton());
//        } else if (component instanceof CampActor) {
//            actorAdapter.add(new BuildButton());
//        }
//        commandPanel.postInvalidate();
//    }

}