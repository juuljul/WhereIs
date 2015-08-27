package com.abcd.projetcnam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

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
    Bitmap bmp;
    //private int x = 0;
    private int xSpeed = 1;
    float departX, departY, arriveeX, arriveeY, deltaX, deltaY, x, y = 0;
    boolean arrowOnStart = true;
    ArrayList <Boolean> arrowsOnStart = new ArrayList<>();
    ArrayList <Float> xArrow = new ArrayList<>();
    ArrayList <Float> yArrow = new ArrayList<>();
    float speed = 10;


    public MySurfaceView(Context context, String startRoom, String stopRoom) {
        super(context);
        this.startRoom = startRoom;
        this.stopRoom = stopRoom;
        graph = new Graph();



        findRoomIndex();
        graph.findMinimumDistance(startIndex,stopIndex);

        for (int i=graph.getFinalPath().size()-1; i>=0 ;i--){
            arrowsOnStart.add(true);
            xArrow.add((float) 0);
            yArrow.add((float) 0);

        }

        for (int i=graph.getFinalPath().size()-1; i>=0 ;i--){
            st = st + " >> " + graph.getFinalPath().get(i);
        }
        Toast.makeText(context,st,Toast.LENGTH_LONG).show();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        drawingThread = new DrawingThread();
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.fleches10x30);
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
        //paint.setColor(Color.WHITE);
        paint.setColor(Color.parseColor("#ff3a3a3a"));
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

        paint.setColor(Color.WHITE);
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

        /*departX = graph.getNodes()[3].getX()*getWidth()/48;
        departY = graph.getNodes()[3].getY()*getHeight()/26;
        arriveeX = graph.getNodes()[1].getX()*getWidth()/48;
        arriveeY= graph.getNodes()[1].getY()*getHeight()/26;
        deltaX = arriveeX - departX;
        deltaY= arriveeY - departY;


        if (arrowOnStart) {
            x = departX;
            y = departY;
            arrowOnStart = false;
        }


        if (x < arriveeX){
            x=x+2;
        }
        else {
            arrowOnStart=true;
        }
        if ( y < arriveeY){
            y = y + 2*deltaY/deltaX;
        }
        else {
            arrowOnStart=true;
        }
        canvas.drawBitmap(bmp,x,y,null);*/





        for (int i=graph.getFinalPath().size()-1; i>0 ;i--){
            departX= graph.getNodes()[graph.getFinalPath().get(i)].getX()*getWidth()/48;
            arriveeX = graph.getNodes()[graph.getFinalPath().get(i-1)].getX()*getWidth()/48;
            deltaX = arriveeX - departX;
            departY = graph.getNodes()[graph.getFinalPath().get(i)].getY()*getHeight()/26;
            arriveeY = graph.getNodes()[graph.getFinalPath().get(i-1)].getY()*getHeight()/26;
            deltaY = arriveeY - departY;


            if (arrowsOnStart.get(i)){
                xArrow.set(i, departX);
                yArrow.set(i,departY);
                arrowsOnStart.set(i,false);
            }

            if (arriveeX < departX){
                if (xArrow.get(i) - bmp.getWidth()/2 < arriveeX){
                    arrowsOnStart.set(i,true);
                }
                else{
                    xArrow.set(i,xArrow.get(i)-speed);
                }
                if (arriveeY < departY){
                    if (yArrow.get(i) - bmp.getHeight()/2 < arriveeY){
                        arrowsOnStart.set(i,true);
                    }
                    else {
                        yArrow.set(i,yArrow.get(i)-speed*deltaY/deltaX);
                    }
                }
                else if (arriveeY > departY) {
                    if (yArrow.get(i) + bmp.getHeight()/2 > arriveeY) {
                        arrowsOnStart.set(i,true);
                    } else {
                        yArrow.set(i,yArrow.get(i)-speed*deltaY/deltaX);
                    }
                }
            }
            else if (arriveeX > departX){
                if (xArrow.get(i) + bmp.getWidth()/2 > arriveeX){
                    arrowsOnStart.set(i,true);
                }
                else{
                    xArrow.set(i,xArrow.get(i)+speed);
                }
                if (arriveeY < departY){
                    if (yArrow.get(i) - bmp.getHeight()/2 < arriveeY){
                        arrowsOnStart.set(i,true);
                    }
                    else {
                        yArrow.set(i,yArrow.get(i)+speed*deltaY/deltaX);
                    }
                }
                else if (arriveeY > departY) {
                    if (yArrow.get(i)+bmp.getHeight()/2 > arriveeY) {
                        arrowsOnStart.set(i,true);
                    } else {
                        yArrow.set(i,yArrow.get(i)+speed*deltaY/deltaX);
                    }
                }
            }
            else if (arriveeX == departX){
                if (arriveeY < departY){
                    if (yArrow.get(i) - bmp.getWidth()/2 < arriveeY){
                        arrowsOnStart.set(i,true);
                    }
                    else {
                        yArrow.set(i,yArrow.get(i)-speed);
                    }
                }
                else if (arriveeY > departY){
                    if (yArrow.get(i) + bmp.getWidth()/2 > arriveeY){
                        arrowsOnStart.set(i,true);
                    }
                    else {
                        yArrow.set(i,yArrow.get(i)+speed);
                    }
                }
            }

            float h = (float) Math.sqrt(deltaX*deltaX+deltaY*deltaY);

            /*Matrix matrix = new Matrix();
            matrix.setSinCos(deltaY/h,deltaX/h,xArrow.get(i)-bmp.getWidth()/2,yArrow.get(i)-bmp.getHeight()/2);
            canvas.drawBitmap(bmp, matrix, null);*/



            /*Matrix matrix2 = new Matrix();
            matrix2.setRotate((float) Math.toDegrees(Math.atan(deltaY/deltaX)),
                    xArrow.get(i)-bmp.getWidth()/2,yArrow.get(i)-bmp.getHeight()/2);
            canvas.drawBitmap(bmp, matrix2, null);*/


            double angle = Math.atan(deltaY/deltaX);
            if (deltaX<0){
                angle = angle + Math.PI;
            }

            canvas.save();
            canvas.rotate((float) Math.toDegrees(angle),
                    xArrow.get(i),yArrow.get(i));
            canvas.drawBitmap(bmp,xArrow.get(i)-bmp.getWidth()/2,yArrow.get(i)-bmp.getHeight()/2,null);
            canvas.restore();

            //canvas.drawBitmap(bmp,xArrow.get(i)-bmp.getWidth()/2,yArrow.get(i)-bmp.getHeight()/2,null);
        }




        /*
        for (int i=graph.getFinalPath().size()-1; i>0 ;i--){
            departX= graph.getNodes()[graph.getFinalPath().get(i)].getX();
            arriveeX = graph.getNodes()[graph.getFinalPath().get(i-1)].getX();
            deltaX = arriveeX - departX;
            departY = graph.getNodes()[graph.getFinalPath().get(i)].getY();
            arriveeY = graph.getNodes()[graph.getFinalPath().get(i-1)].getY();
            deltaY = arriveeY - departY;
            x = departX;
            y = departY;
            //float speedX = (float) 0.1;
            //float speedY = (float) 0.1;
            if (arriveeX < departX){
                // speedX = - speedX;
                if (x < arriveeX){
                       x = departX;
                   }
                   else{
                       x--;
                   }
                if (arriveeY < departY){
                    if (y < arriveeY){
                        y = departY;
                    }
                    else {
                        y=y-deltaY/deltaX;
                    }
                }
                else if (arriveeY > departY) {
                    if (y > arriveeY) {
                        y = departY;
                    } else {
                        y =y-deltaY/deltaX;
                    }
                }
                else if (arriveeY == departY){
                    x--;
                }
            }
            else if (arriveeX > departX){
                if (x > arriveeX){
                    x = departX;
                }
                else{
                    x++;
                }
                if (arriveeY < departY){
                    if (y < arriveeY){
                        y = departY;
                    }
                    else {
                        y = y+deltaY/deltaX;
                    }
                }
                else if (arriveeY > departY) {
                    if (y > arriveeY) {
                        y = departY;
                    } else {
                        y =y+deltaY/deltaX;
                    }
                }
                else if (arriveeY == departY){
                    x++;
                }
            }
            else if (arriveeX == departX){
                if (arriveeY < departY){
                    if (y < arriveeY){
                        y = departY;
                    }
                    else {
                        y--;
                    }
                }
                else if (arriveeY > departY){
                    if (y > arriveeY){
                        y = departY;
                    }
                    else {
                        y++;
                    }
                }
            }
        canvas.drawBitmap(bmp,x,y,null);
        }*/

        /*if (x == getWidth() - bmp.getWidth()) {
            xSpeed = -1;
        }
        if (x == 0) {
            xSpeed = 1;
        }
        x = x + xSpeed;
        canvas.drawBitmap(bmp, x , 10, null);*/


        // Les noeuds de départ et d'arrivée sont peint en bleu
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(getWidth()*graph.getNodes()[startIndex].getX()/48,
                getHeight()*graph.getNodes()[startIndex].getY()/26, getWidth()/48, paint);
        canvas.drawCircle(getWidth()*graph.getNodes()[stopIndex].getX()/48,
                getHeight()*graph.getNodes()[stopIndex].getY()/26, getWidth()/48, paint);
        graph.getNodes()[startIndex].setBlue(true);
        graph.getNodes()[stopIndex].setBlue(true);

        // Les noeuds de trajet intermédiaires sont peints en blanc
        paint.setColor(Color.WHITE);
        for (int i :graph.getFinalPath()){
            if (!graph.getNodes()[i].isBlue()){
                canvas.drawCircle(getWidth()*graph.getNodes()[i].getX()/48,
                        getHeight()*graph.getNodes()[i].getY()/26, getWidth()/48, paint);
                graph.getNodes()[i].setGreen(true);
            }
        }

        // Si les noeuds n'appartiennent pas au trajet final, ils sont peints en noir
        //paint.setColor(Color.parseColor("#CD5C5C"));
        paint.setColor(Color.BLACK);
        for (Node node :graph.getNodes()){
            if (!node.isBlue() && !node.isGreen()){
                canvas.drawCircle(getWidth()*node.getX()/48,
                        getHeight()*node.getY()/26,getWidth()/48, paint);
            }
        }

        // Affichage des numéros des différents accès
        paint.setColor(Color.GRAY);
        paint.setTextSize(20);
        for (Node node :graph.getNodes()){
            canvas.drawText(node.getRoomName(),getWidth()*node.getX()/48,
                    getHeight()*node.getY()/26,paint);
        }

        //invalidate();
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
