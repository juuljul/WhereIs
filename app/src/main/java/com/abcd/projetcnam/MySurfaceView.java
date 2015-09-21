package com.abcd.projetcnam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by julien on 11/08/2015.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    SurfaceHolder surfaceHolder;
    DrawingThread drawingThread;

    public void setStartRoom(String startRoom) {
        this.startRoom = startRoom;
    }

    public void setStopRoom(String stopRoom) {
        this.stopRoom = stopRoom;
    }

    public void setPlanDynamic(boolean isPlanDynamic) {
        this.isPlanDynamic = isPlanDynamic;
    }

    String startRoom, stopRoom;

    Graph graph;
    int startIndex, stopIndex;
    Bitmap bmp;
    float departX, departY, arriveeX, arriveeY, deltaX, deltaY = 0;

    // Booléens à true si le bitmap est en position de départ, false sinon
    ArrayList <Boolean> arrowsOnStart = new ArrayList<>();

    // Coordonnées du bitmap en déplacement
    ArrayList <Float> xArrow = new ArrayList<>();
    ArrayList <Float> yArrow = new ArrayList<>();

    float speed = 10;
    double longueurTrajet =0;

    boolean isPlanDynamic = true;

    //GButton ttsButton, backButton;
    //Bitmap bitmapButton;
    Context context;

    String cheminSpeech = "";
    String chemin="";
    private TextToSpeech textToSpeech;
    private Locale currentSpokenLang = Locale.FRENCH;



    public MySurfaceView(Context context, String startRoom, String stopRoom, boolean isPlanDynamic) {
        super(context);
        this.context=context;
        this.startRoom = startRoom;
        this.stopRoom = stopRoom;
        this.isPlanDynamic = isPlanDynamic;
        graph = new Graph();

        //setOnTouchListener(MySurfaceView.this);

        if (isPlanDynamic) {
            //findRoomIndex();
            startIndex = graph.findStartIndex(startRoom);
            stopIndex = graph.findStopIndex(stopRoom);
            longueurTrajet = graph.findMinimumDistance(startIndex,stopIndex);

            for (int i=graph.getFinalPath().size()-1; i>=0 ;i--){
                arrowsOnStart.add(true);
                xArrow.add((float) 0);
                yArrow.add((float) 0);
            }
            bmp = BitmapFactory.decodeResource(getResources(),R.drawable.fleches10x30);

            /*textToSpeech = new TextToSpeech(context,this);

            for (int i=graph.getFinalPath().size()-1; i>=0 ;i--){
                chemin = chemin + graph.getNodes()[graph.getFinalPath().get(i)].getRoomName()
                        +", ";
            }

            cheminSpeech = "Pour atteindre l'accès numéro" + graph.getFinalPath().get(0) +
                    "Vous devez successivement passer par les accès numéros" + chemin +
                    "le chemin total se fait à la marche en " + longueurTrajet + "pas";*/
        }




        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        drawingThread = new DrawingThread();

        //bitmapButton = BitmapFactory.decodeResource(getResources(),R.drawable.triang30vert);
        /*ttsButton = new GButton(30,30,bitmapButton);
        backButton = new GButton(30,30,bitmapButton);*/

    }


    /*public void findRoomIndex(){
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
    }*/

    // Marquage du boolean de trajet à true pour les noeuds et aretes du parcours
    public void markPath(){
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
    }

    // Affichage des arêtes hors parcours
    public void colorEdgesNonPath (Path path, Paint paint, int color, Canvas canvas){
        for (Edge edge : graph.getEdges()){
            if (!edge.isGreen()){
                path.moveTo(graph.getNodes()[edge.getFromNodeIndex()].getX()*getWidth()/48,
                        graph.getNodes()[edge.getFromNodeIndex()].getY()*getHeight()/26);
                path.lineTo(graph.getNodes()[edge.getToNodeIndex()].getX()*getWidth()/48,
                        graph.getNodes()[edge.getToNodeIndex()].getY()*getHeight()/26);
            }
        }
        paint.setColor(color);
        canvas.drawPath(path,paint);
    }

    // Affichage des arêtes du parcours
    public void colorEdgesPath (Path path, Paint paint, int color, Canvas canvas){
        for (Edge edge : graph.getEdges()){
            if (edge.isGreen()){
                path.moveTo(graph.getNodes()[edge.getFromNodeIndex()].getX()*getWidth()/48,
                        graph.getNodes()[edge.getFromNodeIndex()].getY()*getHeight()/26);
                path.lineTo(graph.getNodes()[edge.getToNodeIndex()].getX()*getWidth()/48,
                        graph.getNodes()[edge.getToNodeIndex()].getY()*getHeight()/26);
            }
        }
        paint.setColor(color);
        canvas.drawPath(path,paint);
    }

    public void updateBitmapMove(Canvas canvas) {
        // Calcul des extrémités pour le parcours du bitmap de flèches ainsi que de la distance
        // à effectuer en abcisse et en ordonnée

        for (int i = graph.getFinalPath().size() - 1; i > 0; i--) {
            departX = graph.getNodes()[graph.getFinalPath().get(i)].getX() * getWidth() / 48;
            arriveeX = graph.getNodes()[graph.getFinalPath().get(i - 1)].getX() * getWidth() / 48;
            deltaX = arriveeX - departX;
            departY = graph.getNodes()[graph.getFinalPath().get(i)].getY() * getHeight() / 26;
            arriveeY = graph.getNodes()[graph.getFinalPath().get(i - 1)].getY() * getHeight() / 26;
            deltaY = arriveeY - departY;

            // Initialisation aux extrémités de départ
            if (arrowsOnStart.get(i)) {
                xArrow.set(i, departX);
                yArrow.set(i, departY);
                arrowsOnStart.set(i, false);
            }

            // Calcul des stops du bitmap et retour à l'extrémité de départ suivant les différents
            // cas de figure... Si le trajet se fait de gauche à droite et/ou du haut vers le bas etc

            // Trajet de droite à gauche
            if (arriveeX < departX) {
                if (xArrow.get(i) - bmp.getWidth() / 2 < arriveeX) {
                    arrowsOnStart.set(i, true);
                } else {
                    xArrow.set(i, xArrow.get(i) - speed);
                }
                // Trajet du bas vers le haut
                if (arriveeY < departY) {
                    if (yArrow.get(i) - bmp.getHeight() / 2 < arriveeY) {
                        arrowsOnStart.set(i, true);
                    } else {
                        yArrow.set(i, yArrow.get(i) - speed * deltaY / deltaX);
                    }
                }
                // Trajet du haut vers le bas
                else if (arriveeY > departY) {
                    if (yArrow.get(i) + bmp.getHeight() / 2 > arriveeY) {
                        arrowsOnStart.set(i, true);
                    } else {
                        yArrow.set(i, yArrow.get(i) - speed * deltaY / deltaX);
                    }
                }
            }
            // Trajet de gauche à droite
            else if (arriveeX > departX) {
                if (xArrow.get(i) + bmp.getWidth() / 2 > arriveeX) {
                    arrowsOnStart.set(i, true);
                } else {
                    xArrow.set(i, xArrow.get(i) + speed);
                }
                if (arriveeY < departY) {
                    if (yArrow.get(i) - bmp.getHeight() / 2 < arriveeY) {
                        arrowsOnStart.set(i, true);
                    } else {
                        yArrow.set(i, yArrow.get(i) + speed * deltaY / deltaX);
                    }
                } else if (arriveeY > departY) {
                    if (yArrow.get(i) + bmp.getHeight() / 2 > arriveeY) {
                        arrowsOnStart.set(i, true);
                    } else {
                        yArrow.set(i, yArrow.get(i) + speed * deltaY / deltaX);
                    }
                }
            }
            // Trajet uniquement vertical
            else if (arriveeX == departX) {
                if (arriveeY < departY) {
                    if (yArrow.get(i) - bmp.getWidth() / 2 < arriveeY) {
                        arrowsOnStart.set(i, true);
                    } else {
                        yArrow.set(i, yArrow.get(i) - speed);
                    }
                } else if (arriveeY > departY) {
                    if (yArrow.get(i) + bmp.getWidth() / 2 > arriveeY) {
                        arrowsOnStart.set(i, true);
                    } else {
                        yArrow.set(i, yArrow.get(i) + speed);
                    }
                }
            }


            // Rotation du bitmap des flèches suivant l'orientation du parcours
            // à l'aide de l'arctan du rapport des deltas
            double angle = Math.atan(deltaY / deltaX);
            if (deltaX < 0) {
                angle = angle + Math.PI;
            }
            canvas.save();
            canvas.rotate((float) Math.toDegrees(angle),
                    xArrow.get(i), yArrow.get(i));
            canvas.drawBitmap(bmp, xArrow.get(i) - bmp.getWidth() / 2, yArrow.get(i) - bmp.getHeight() / 2, null);
            canvas.restore();


        }
    }


    public void colorNodesPath(Paint paint, Canvas canvas){
        // Les noeuds de départ et d'arrivée sont peints en bleu
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(getWidth()*graph.getNodes()[startIndex].getX()/48,
                getHeight()*graph.getNodes()[startIndex].getY()/26, getWidth()/36, paint);
        canvas.drawCircle(getWidth()*graph.getNodes()[stopIndex].getX()/48,
                getHeight()*graph.getNodes()[stopIndex].getY()/26, getWidth()/36, paint);
        //graph.getNodes()[startIndex].setBlue(true);
        //graph.getNodes()[stopIndex].setBlue(true);

        // Les noeuds de trajet intermédiaires sont peints en blanc
        paint.setColor(Color.WHITE);
        for (int i :graph.getFinalPath()){
            if (graph.getNodes()[i].isGreen()){
                canvas.drawCircle(getWidth()*graph.getNodes()[i].getX()/48,
                        getHeight()*graph.getNodes()[i].getY()/26, getWidth()/48, paint);
                graph.getNodes()[i].setGreen(true);
            }
        }
    }

    // Si les noeuds n'appartiennent pas au trajet final, ils sont peints en argument color
    public void colorNodesNonPath(Paint paint, int color, Canvas canvas){
        paint.setColor(color);
        for (Node node :graph.getNodes()){
            //if (!node.isBlue() && !node.isGreen()){
            if (!node.isGreen()){
                canvas.drawCircle(getWidth()*node.getX()/48,
                        getHeight()*node.getY()/26,getWidth()/48, paint);
            }
        }
    }

    // Affichage en noir des numéros des accès du trajet
    public void writeNumbersPath(Paint paint, Canvas canvas){
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        for (Node node :graph.getNodes()){
            if (node.isGreen()){
                canvas.drawText(node.getRoomName(), getWidth() * node.getX() / 48,
                        getHeight() * node.getY() / 26, paint);
            }
        }
    }

    // Affichage des numéros des accès hors tajet
    public void writeNumbersNonPath(Paint paint, Canvas canvas, int color){
        paint.setColor(color);
        paint.setTextSize(20);
        for (Node node :graph.getNodes()){
            if (!node.isGreen()){
                canvas.drawText(node.getRoomName(), getWidth() * node.getX() / 48,
                        getHeight() * node.getY() / 26, paint);
            }
        }
    }


    /*@Override
    public boolean onTouchEvent(MotionEvent event) {

        synchronized (surfaceHolder){
            float x = event.getX();
            float y = event.getY();
            //if (backButton.btn_rect.contains(x, y))
            if (x>=40*getWidth()/48 && x<=30+40*getWidth()/48 && y>=7*getHeight()/26 && y<=30+7*getHeight()/26)
            {
                Intent intent = new Intent(context,LocationActivity.class);
                context.startActivity(intent);
            }
            //if (ttsButton.btn_rect.contains(x, y))
            if (x>=25*getWidth()/48 && x<=30+25*getWidth()/48 && y>=7*getHeight()/26 && y<=30+7*getHeight()/26)
            {
                if (isPlanDynamic){
                    textToSpeech.setLanguage(currentSpokenLang);
                    textToSpeech.speak(cheminSpeech,TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            if (x>getWidth()/2 && y>getHeight()/2)
            {
                textToSpeech.setLanguage(currentSpokenLang);
                textToSpeech.speak(cheminSpeech,TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        // handle on touch here
        return true;
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        //paint.setColor(Color.parseColor("#ff3a3a3a"));
        paint.setColor(Color.parseColor("#C0F4FF"));
        canvas.drawPaint(paint);

        /*ttsButton.setPosition(25*getWidth()/48,7*getHeight()/26);
        ttsButton.draw(canvas);
        backButton.setPosition(40*getWidth()/48,7*getHeight()/26);
        backButton.draw(canvas);*/
        //canvas.drawBitmap(bitmapButton,25*getWidth()/48,7*getHeight()/26,paint);
        //canvas.drawBitmap(bitmapButton,40*getWidth()/48,7*getHeight()/26,paint);

        // Les arêtes qui n'appartiennent pas au trajet sont peintes en noir
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
        Path blackPath = new Path();
        if (isPlanDynamic){
            markPath();
            colorEdgesNonPath(blackPath, paint, Color.BLACK, canvas);
            Path whitePath = new Path();
            colorEdgesPath(whitePath,paint,Color.WHITE,canvas);
            updateBitmapMove(canvas);
            paint.setStyle(Paint.Style.FILL);
            colorNodesPath(paint, canvas);
            colorNodesNonPath(paint,Color.BLACK,canvas);
            writeNumbersPath(paint,canvas);
            writeNumbersNonPath(paint,canvas,Color.WHITE);
        }
        else {
            colorEdgesNonPath(blackPath,paint,Color.WHITE,canvas);
            paint.setStyle(Paint.Style.FILL);
            colorNodesNonPath(paint, Color.WHITE, canvas);
            writeNumbersNonPath(paint,canvas,Color.BLACK);
        }

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
        /*if (isPlanDynamic){
            if (textToSpeech != null) {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
        }*/
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


    public Graph getGraph() {
        return graph;
    }


    public double getLongueurTrajet() {
        return longueurTrajet;
    }

    /*@Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(currentSpokenLang);

            // If language data or a specific language isn't available error
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Language Not Supported", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, "Text To Speech Failed", Toast.LENGTH_SHORT).show();
        }

    }*/

    /*@Override
    public boolean onTouch(View v, MotionEvent event) {
        //switch (event.getAction()){
            //case MotionEvent.ACTION_DOWN:
        synchronized (getHolder()){
                float x = event.getX();
                float y = event.getY();
                if (backButton.btn_rect.contains(x, y))
                {
                    Intent intent = new Intent(context,DestinationActivity.class);
                    context.startActivity(intent);
                }
                if (ttsButton.btn_rect.contains(x, y))
                {
                    if (isPlanDynamic){
                        textToSpeech.setLanguage(currentSpokenLang);
                        textToSpeech.speak(cheminSpeech,TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
        }
            // handle on touch here
        return true;
    }*/


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
