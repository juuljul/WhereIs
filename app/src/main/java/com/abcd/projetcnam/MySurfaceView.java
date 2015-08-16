package com.abcd.projetcnam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * Created by julien on 11/08/2015.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    DrawingThread drawingThread;
    String startRoom, stopRoom;
    Graph graph;
    int startIndex, stopIndex;
    String st = "";


    public MySurfaceView(Context context, String startRoom, String stopRoom) {
        super(context);
        this.startRoom = startRoom;
        this.stopRoom = stopRoom;
        graph = new Graph();
        findRoomIndex();
        graph.findMinimumDistance(startIndex,stopIndex);

        for (int i=graph.getFinalPath().size()-1; i>=0 ;i--){
            st = st + " >> " + graph.getFinalPath().get(i);
        }
        Toast.makeText(context,st,Toast.LENGTH_LONG).show();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        drawingThread = new DrawingThread();
    }


    public void findRoomIndex(){
        for (Node node :graph.getNodes()){
            if(node.getRoomName().equals(startRoom)){
                startIndex = node.getIndex();
                break;
            }
        }
        for (Node node :graph.getNodes()) {
            if (node.getRoomName().equals(stopRoom)) {
                stopIndex = node.getIndex();
                break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        /*paint.setColor(Color.parseColor("#CD5C5C"));
        //paint.setColor(0xCD5C5C);
        //canvas.drawCircle((float)(getWidth()/48),(float)(getHeight()/26),(float)(getWidth()/48), paint);
        //canvas.drawCircle((float)(3*getWidth()/48),(float)(23*getHeight()/26),(float)(getWidth()/48), paint);
        canvas.drawCircle(3*getWidth()/48, 9*getHeight()/26, getWidth()/48, paint);
        canvas.drawCircle(3*getWidth()/48, 23*getHeight()/26, getWidth()/48, paint);
        canvas.drawCircle(33*getWidth()/48,  23*getHeight()/26, getWidth()/48, paint);
        canvas.drawCircle(39*getWidth()/48,  19*getHeight()/26, getWidth()/48, paint);
        canvas.drawCircle(45*getWidth()/48, 19*getHeight()/26, getWidth()/48, paint);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
        Path path = new Path();
        path.moveTo(3*getWidth()/48, 9*getHeight()/26);
        path.lineTo(3 * getWidth() / 48, 23 * getHeight() / 26);
        path.lineTo(33*getWidth()/48,  23*getHeight()/26);
        path.lineTo(39*getWidth()/48,  19*getHeight()/26);
        path.lineTo(45*getWidth()/48, 19*getHeight()/26);
        //path.close();
        canvas.drawPath(path,paint);
        paint.setColor(Color.parseColor("#CD5C5C"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(10*getWidth()/48, 23*getHeight()/26, getWidth()/48, paint);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
        path.moveTo(10*getWidth()/48, 9*getHeight()/26);
        path.lineTo(10*getWidth()/48, 16*getHeight()/26);
        canvas.drawPath(path,paint);*/

        // Les arêtes qui n'appartiennent pas au trajet sont peintes en noir
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
        Path blackPath = new Path();


        for (int i :graph.getFinalPath()){
            graph.getNodes()[i].setGreen(true);
        }
        for (Node node : graph.getNodes()){
            if (node.isGreen()){
                for (Edge edge :node.getEdges()){
                    if (graph.getNodes()[edge.getNeighbourIndex(node.getIndex())].isGreen()){
                        edge.setGreen(true);
                    }
                }
            }
        }

        for (Edge edge : graph.getEdges()){
            if (!edge.isGreen()){
                blackPath.moveTo(graph.getNodes()[edge.getFromNodeIndex()].getX()*getWidth()/48,
                        graph.getNodes()[edge.getFromNodeIndex()].getY()*getHeight()/26);
                blackPath.lineTo(graph.getNodes()[edge.getToNodeIndex()].getX()*getWidth()/48,
                        graph.getNodes()[edge.getToNodeIndex()].getY()*getHeight()/26);
            }
        }
        canvas.drawPath(blackPath,paint);

        paint.setColor(Color.GREEN);
        Path greenPath = new Path();

        for (Edge edge : graph.getEdges()){
            if (edge.isGreen()){
                greenPath.moveTo(graph.getNodes()[edge.getFromNodeIndex()].getX()*getWidth()/48,
                        graph.getNodes()[edge.getFromNodeIndex()].getY()*getHeight()/26);
                greenPath.lineTo(graph.getNodes()[edge.getToNodeIndex()].getX()*getWidth()/48,
                        graph.getNodes()[edge.getToNodeIndex()].getY()*getHeight()/26);
            }
        }

        canvas.drawPath(greenPath,paint);


        // Les noeuds de départ et d'arrivée sont peint en bleu
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(getWidth()*graph.getNodes()[startIndex].getX()/48,
                getHeight()*graph.getNodes()[startIndex].getY()/26, getWidth()/48, paint);
        canvas.drawCircle(getWidth()*graph.getNodes()[stopIndex].getX()/48,
                getHeight()*graph.getNodes()[stopIndex].getY()/26, getWidth()/48, paint);
        graph.getNodes()[startIndex].setBlue(true);
        graph.getNodes()[stopIndex].setBlue(true);

        // Les noeuds de trajet intermédiaires sont peints en vert
        paint.setColor(Color.GREEN);
        for (int i :graph.getFinalPath()){
            if (!graph.getNodes()[i].isBlue()){
                canvas.drawCircle(getWidth()*graph.getNodes()[i].getX()/48,
                        getHeight()*graph.getNodes()[i].getY()/26, getWidth()/48, paint);
                graph.getNodes()[i].setGreen(true);
            }
        }

        // Si les noeuds n'appartiennent pas au trajet final, ils sont peints en rouge
        paint.setColor(Color.parseColor("#CD5C5C"));
        for (Node node :graph.getNodes()){
            if (!node.isBlue() && !node.isGreen()){
                canvas.drawCircle(getWidth()*node.getX()/48,
                        getHeight()*node.getY()/26,getWidth()/48, paint);
            }
        }


        /*Rect ourRect = new Rect();
        ourRect.set(0,0,canvas.getWidth(),canvas.getHeight()/2);
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL);
        canvas.drawRect(ourRect,blue);*/
    }





    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawingThread.keepDrawing = true;
        drawingThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawingThread.keepDrawing = false;
        boolean joined = false;
        while (!joined){
            try {
                drawingThread.join();
                joined = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class DrawingThread extends Thread {
        boolean keepDrawing = true;


        @Override
        public void run() {
            while (keepDrawing){
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder){
                        onDraw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
