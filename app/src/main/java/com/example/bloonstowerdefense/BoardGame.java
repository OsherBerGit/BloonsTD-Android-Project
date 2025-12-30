package com.example.bloonstowerdefense;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;



import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BoardGame extends View {
    private Context context;
    private Square[][] squares;
    private Balloon[][] balloons;
    private ArrayList<Tower> towers;
    private ArrayList<Balloon> bloons;
    private ArrayList<TowertoFb> Tfbs;
    private ArrayList<Dart> darts;
    private Tower tower;
    Handler handler;
    ThreadGame threadGame;
    private boolean firstTime;
    private final int NUM_OF_SQUARES_H = 9;
    private final int NUM_OF_SQUARES_W = 10;
    boolean isPlaced = true;
    public int round, coins, life;
    private float w, h;
    PicEnable D, T, S, G;
    boolean isRun = true;
    int counter = 0;
    private boolean isRoundStarted = false;
    public FbModule fb;

    public BoardGame(Context context) {
        super(context);
        this.context = context;

        squares = new Square[NUM_OF_SQUARES_H][NUM_OF_SQUARES_W];
        firstTime = true;
        towers = new ArrayList<>();
        bloons = new ArrayList<>();
        darts = new ArrayList<>();
        balloons = new Balloon[3][3];
        Tfbs = new ArrayList<>();

        fb = new FbModule(context, Tfbs);

        this.coins = 2000;
        this.life = 200;
        this.round = 0;
        this.towers.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstTime) {
            initBoard(canvas);
            firstTime = false;
        }
        drawBoard(canvas);
        drawBitmaps(canvas);
        if (!towers.isEmpty()) {
            for (int i = 0; i < towers.size(); i++)
                towers.get(i).draw(canvas);
        }
        if (!towers.isEmpty()) {
            for (int i = 0; i < towers.size(); i++)
                towers.get(i).draw(canvas);
        }
        if (isRoundStarted && !bloons.isEmpty()) {
            Sendballoons();
            isRoundStarted = false;
        }
        if (!bloons.isEmpty()) {
            for (int i = 0; i < bloons.size(); i++)
                bloons.get(i).draw(canvas);
        }
        if (!darts.isEmpty()) {
            for (int i = 0; i < darts.size(); i++) {
                darts.get(i).draw(canvas);
            }
        }
        Paint p = new Paint();
        p.setTextSize(75);
        p.setColor(Color.BLACK);
        canvas.drawText(String.valueOf(round), canvas.getWidth() - 100, 140, p);
        p.setColor(Color.YELLOW);
        canvas.drawText(String.valueOf(coins), canvas.getWidth() - 300, 140, p);
        p.setColor(Color.RED);
        canvas.drawText(String.valueOf(life), canvas.getWidth() - 500, 140, p);

//        if (life <= 0) {
//            ExitDialog customDialog = new ExitDialog(context);
//            customDialog.show();
//        }
        if (round >= 3) {
            ExitDialog customDialog = new ExitDialog(context);
            customDialog.show();
        }
    }

    private void initBoard(Canvas canvas) {
        canvas.drawRect(800, 100, 900, 1000, new Paint());
        float x = 110;
        float y = 0;
        h = canvas.getHeight() / NUM_OF_SQUARES_H;
        w = h;
        for (int i = 0; i < NUM_OF_SQUARES_H; i++) {
            for (int j = 0; j < NUM_OF_SQUARES_W; j++) {
                squares[i][j] = new Square(x, y, w, h, Color.GREEN);
                x = x + w;
            }
            y = y + h;
            x = 110;
        }
        initMap();
    }

    private void initMap() { // create the route of the balloons
        squares[3][0].toRoute(Color.GRAY, 4);
        squares[3][1].toRoute(Color.GRAY, 4);
        squares[3][2].toRoute(Color.GRAY, 3);
        squares[2][2].toRoute(Color.GRAY, 3);
        squares[1][2].toRoute(Color.GRAY, 4);
        squares[1][3].toRoute(Color.GRAY, 4);
        squares[1][4].toRoute(Color.GRAY, 2);
        squares[2][4].toRoute(Color.GRAY, 2);
        squares[3][4].toRoute(Color.GRAY, 2);
        squares[4][4].toRoute(Color.GRAY, 2);
        squares[5][4].toRoute(Color.GRAY, 0);
        squares[6][4].toRoute(Color.GRAY, 2);
        squares[7][4].toRoute(Color.GRAY, 1);
        squares[7][3].toRoute(Color.GRAY, 1);
        squares[7][2].toRoute(Color.GRAY, 3);
        squares[6][2].toRoute(Color.GRAY, 3);
        squares[5][2].toRoute(Color.GRAY, 4);
        squares[5][3].toRoute(Color.GRAY, 4);
        squares[5][5].toRoute(Color.GRAY, 4);
        squares[5][6].toRoute(Color.GRAY, 4);
        squares[5][7].toRoute(Color.GRAY, 3);
        squares[4][7].toRoute(Color.GRAY, 3);
        squares[3][7].toRoute(Color.GRAY, 4);
        squares[3][8].toRoute(Color.GRAY, 4);
        squares[3][9].toRoute(Color.GRAY, 4);
    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < NUM_OF_SQUARES_H; i++) {
            for (int j = 0; j < NUM_OF_SQUARES_W; j++) {
                squares[i][j].draw(canvas);
            }
        }
    }

    private void drawBitmaps(Canvas canvas) {
        Bitmap d = BitmapFactory.decodeResource(context.getResources(), R.drawable.dart_monkey);
        canvas.drawBitmap(d, canvas.getWidth() - 230, 230, new Paint());
        D = new PicEnable(canvas.getWidth() - 230, 230, d);
        Bitmap t = BitmapFactory.decodeResource(context.getResources(), R.drawable.tack_shooter);
        canvas.drawBitmap(t, canvas.getWidth() - 230 - 24, 430, new Paint());
        T = new PicEnable(canvas.getWidth() - 230 - 24, 430, t);
        Bitmap s = BitmapFactory.decodeResource(context.getResources(), R.drawable.start);
        s = Bitmap.createScaledBitmap(s, (int) (s.getWidth()*1.2), (int) (s.getHeight()*1.2),true);
        canvas.drawBitmap(s, canvas.getWidth() - 230 - s.getWidth() / 2, 830, new Paint());
        S = new PicEnable(canvas.getWidth() - 230 - s.getWidth() / 2, 830, s);
        Bitmap g = BitmapFactory.decodeResource(context.getResources(), R.drawable.gear);
        canvas.drawBitmap(g, canvas.getWidth() - 500, 170, new Paint());
        G = new PicEnable(canvas.getWidth() - 500, 170, g);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isPlaced) {
                if (D.diduserTouchMe(event.getX(), event.getY())) {
                    tower = new Tower(event.getX(), event.getY(), "Dart", context);
                    towers.add(tower);
                    isPlaced = false;
                } else if (T.diduserTouchMe(event.getX(), event.getY())) {
                    tower = new Tower(event.getX(), event.getY(), "Tack", context);
                    towers.add(tower);
                    isPlaced = false;
                }
            }
            if (S.diduserTouchMe(event.getX(), event.getY()) && bloons.isEmpty()) {
                Preparebloons();
                isRoundStarted = true;
                invalidate();
            }
            if (G.diduserTouchMe(event.getX(), event.getY())) {
                ExitDialog customDialog = new ExitDialog(context);
                customDialog.show();
                invalidate();
            }
            if (tower == null) {
                for (int i = 0; i < towers.size(); i++) {
                    if (towers.get(i).didUserTouchMe(event.getX(), event.getY()))
                        towers.get(i).setRangeColor(Color.GRAY);
                    else {
                        towers.get(i).setRangeColor(Color.TRANSPARENT);
                    }
                    invalidate();
                }
            } else {
                for (int i = 0; i < towers.size(); i++) {
                    if (tower.didUserTouchMe(event.getX(), event.getY()))
                        tower.setRangeColor(Color.GRAY);
                    else {
                        tower.setRangeColor(Color.TRANSPARENT);
                    }
                    invalidate();
                }
            }
        }
        if(event.getAction()==MotionEvent.ACTION_MOVE &&!isPlaced)
    {
        if (tower != null)
            tower.setXandY(event.getX(), event.getY());
        if (onSquare(event.getX(), event.getY()) != null) {
            if (onSquare(event.getX(), event.getY()).getDirecetion() == 0 && onSquare(event.getX(), event.getY()).getColor() != Color.GRAY) {
                boolean flag = true;
                for (int i = 0; i < towers.size() - 1; i++) {
                    if (isCollisionDetected(tower.b, (int) tower.x, (int) tower.y, towers.get(i).b, (int) towers.get(i).x, (int) towers.get(i).y))
                        flag = false;
                }
                if (flag)
                    tower.setRangeColor(Color.GREEN);
                else {
                    Toast.makeText(context, "On other Towers", Toast.LENGTH_SHORT).show();
                    tower.setRangeColor(Color.RED);
                }
            } else
                tower.setRangeColor(Color.RED);
        } else
            tower.setRangeColor(Color.RED);
        invalidate();
    }
        if(event.getAction()==MotionEvent.ACTION_UP)

    {
        if (tower != null) {
            if ((tower.getRangeColor() == Color.GREEN)) {
                if (tower.t == "Dart" && coins >= 100) {
                    tower.setRangeColor(Color.TRANSPARENT);
                    tower.setXandY(event.getX(), event.getY());
                    coins = coins - 100;
                    isPlaced = true;
                } else {
                    if (tower.t == "Tack" && coins >= 200) {
                    tower.setRangeColor(Color.TRANSPARENT);
                    tower.setXandY(event.getX(), event.getY());
                    coins = coins - 200;
                    isPlaced = true;
                } }
                invalidate();
            }
        }
    }
        return true;
}

    public void Preparebloons() {
        balloons[0][0] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,1,context);
        balloons[0][1] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,2,context);
        balloons[0][2] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,3,context);
        balloons[1][0] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,3,context);
        balloons[1][1] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,2,context);
        balloons[1][2] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,1,context);
        balloons[1][0] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,3,context);
        balloons[1][1] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,2,context);
        balloons[1][2] = new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,1,context);

        counter=0;
        for (int i=0; i<3 ;i++)
            bloons.add(balloons[round][i]);

        //bloons.add(new Balloon(squares[2][0].getX()+w/2,squares[3][0].getY()+h/2,1,context));
    }

    public void Sendballoons() {
        threadGame = new ThreadGame();
        threadGame.start(); // runs as thread the run() method

        handler = new Handler(new Handler.Callback() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                counter++;
                int i;
                for (i = 0; i < bloons.size(); i++) {
                    if (onSquare(bloons.get(i).x, bloons.get(i).y) != null) {
                        if (onCenter(bloons.get(i).x, bloons.get(i).y))
                            bloons.get(i).moveTo(onSquare(bloons.get(i).x, bloons.get(i).y).getDirecetion());
                    }
                    bloons.get(i).move();
                    if (onSquare(bloons.get(i).x, bloons.get(i).y) == null) {
                            bloons.remove(i);
                            i = bloons.size();
                            life = life - 10;
                    }
                }
                if (!towers.isEmpty()) {
                    for (int c = 0; c < towers.size(); c++) {
                        if (towers.get(c).countdown > 0)
                            towers.get(c).countdown--;
                    }
                }
                CheckDarts();
                if (!bloons.isEmpty() && !towers.isEmpty())
                    for (i = 0; i < towers.size(); i++) {
                        if (towers.get(i).countdown == 0) {
                            for (int h = 0; h < bloons.size(); h++) {
                                if (towers.get(i).inRange(bloons.get(h))) {
                                    if (towers.get(i).t.equals("Dart")) {
                                        towers.get(i).degree = (float) Math.toDegrees(Math.atan(Math.abs(towers.get(i).y-bloons.get(h).y)/Math.abs(towers.get(i).x-bloons.get(h).x)));
                                        if (whichQuadrant(towers.get(i).x,towers.get(i).y,bloons.get(0).x,bloons.get(0).y) == 1)
                                            towers.get(i).degree = (90 - towers.get(i).degree);
                                        if (whichQuadrant(towers.get(i).x,towers.get(i).y,bloons.get(0).x,bloons.get(0).y) == 2)
                                            towers.get(i).degree = towers.get(i).degree - 90;
                                        if (whichQuadrant(towers.get(i).x,towers.get(i).y,bloons.get(0).x,bloons.get(0).y) == 3)
                                            towers.get(i).degree = 270 - towers.get(i).degree;
                                        if (whichQuadrant(towers.get(i).x,towers.get(i).y,bloons.get(0).x,bloons.get(0).y) == 4)
                                            towers.get(i).degree = 90 + towers.get(i).degree;
                                        towers.get(i).setBitmap(rotated(towers.get(i).b,towers.get(i).degree-towers.get(i).lastdegree));

                                        Dart d = towers.get(i).Shoot(bloons.get(h));
                                        d.setBitmap(rotated(d.b,towers.get(i).degree));
                                        if (whichQuadrant(towers.get(i).x,towers.get(i).y,bloons.get(0).x,bloons.get(0).y) == 1) {
                                            d.dx = -(float) (15*Math.sin(d.direct));
                                            d.dy = -(float) (15*Math.cos(d.direct)); }
                                        else if (whichQuadrant(towers.get(i).x,towers.get(i).y,bloons.get(0).x,bloons.get(0).y) == 2){
                                            d.dx = -(float) (15*Math.sin(d.direct));
                                            d.dy = -(float) (15*Math.cos(d.direct)); }
                                         else if (whichQuadrant(towers.get(i).x,towers.get(i).y,bloons.get(0).x,bloons.get(0).y) == 3){
                                            d.dx = -(float) (15*Math.cos(d.direct));
                                            d.dy = (float) (15*Math.sin(d.direct)); }
                                        else if (whichQuadrant(towers.get(i).x,towers.get(i).y,bloons.get(0).x,bloons.get(0).y) == 4) {
                                            d.dx = (float) (15*Math.sin(d.direct));
                                            d.dy = -(float) (15*Math.cos(d.direct)); }
                                        darts.add(d);
                                        towers.get(i).lastdegree = towers.get(i).degree;
                                    }
                                    if (towers.get(i).t.equals("Tack")) {
                                        Dart[] ds = towers.get(i).Explode();
                                        for (int j = 0; j < 8; j++) {
                                            darts.add(ds[j]);
                                        }
                                    }
                                    towers.get(i).countdown = 100;
                                }
                            }
                        }
                        invalidate();
                    }
                for (i = 0; i < darts.size(); i++) {
                    darts.get(i).move();
                }
                CheckPops();
                invalidate();
                return false;
            }
        });
        round++;
    }

//    public void rotate(Tower tower, int degree, Canvas canvas) {
//        canvas.save();
//        canvas.rotate(degree,tower.x, tower.y);
//        Bitmap bitmap = rotated(tower.b, degree);
//        canvas.restore();
//        canvas.drawBitmap(bitmap, tower.x - 250, tower.y - 250, null);
//        tower.setBitmap(bitmap);
//    }

    public Bitmap rotated(Bitmap source, float angle) {
        Matrix matrix = new Matrix(); // Todo Explain what is Matrix
        matrix.postRotate(angle);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),source.getHeight(), matrix, false);
    }

    public int whichQuadrant(float xc, float yc, float xp, float yp) { //  איזה רביע היחס לראשית הצירים
        if (xp>xc && yp<yc)
            return 1;
        if (xp<xc && yp<yc)
            return 2;
        if (xp<xc && yp>yc)
            return 3;
        return 4;
    }

    public Square onSquare(float x, float y) {
        if (x>110 && x<110+w*(NUM_OF_SQUARES_W+1) && y>0 && y<h*NUM_OF_SQUARES_H)
        {
            int col = (int) (x / w);
            int line = (int) (y / h);
            if (line<NUM_OF_SQUARES_H && col < NUM_OF_SQUARES_W)
                return squares[line][col];
        }
        return  null;
    }

    private boolean onCenter(float x, float y) {
        Square square = onSquare(x,y);
        float xc = square.getX() + h/2;
        float yc = square.getY() + h/2;
        if (Math.sqrt((Math.pow(x-xc,2)+Math.pow(y-yc,2))) <= 66)
            return true;
        return false;
    }

    private void CheckDarts() {
    if (!darts.isEmpty()) {
        for (int c = 0; c < darts.size(); c++) {
            darts.get(c).timeleft--;
            if (darts.get(c).timeleft <= 0) {
                darts.remove(c);
                return; } } } }

    private void CheckPops() {
        for (int a=0; a< darts.size();a++){
            if (darts.get(a).timeleft <= 0) {
                darts.remove(a); }
            else {
                for (int j=0; j<bloons.size();j++){
                    if(isCollisionDetected(darts.get(a).b,(int) darts.get(a).x,(int) darts.get(a).y,bloons.get(j).b,(int) bloons.get(j).x,(int) bloons.get(j).y))
                    {
                        darts.remove(a);
                        if (bloons.get(j).l-1==0)
                            bloons.remove(j);
                        else
                            bloons.get(j).Pop();
                        coins=coins+900;
                        return;
                    } }
            }
        }
    }

    public boolean isCollisionDetected(Bitmap bitmap1, int x1, int y1, Bitmap bitmap2, int x2, int y2) {
        Rect bounds1 = new Rect(x1, y1, x1+bitmap1.getWidth(), y1+bitmap1.getHeight());
        Rect bounds2 = new Rect(x2, y2, x2+bitmap2.getWidth(), y2+bitmap2.getHeight());

        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int bitmap1Pixel = bitmap1.getPixel(i-x1, j-y1);
                    int bitmap2Pixel = bitmap2.getPixel(i-x2, j-y2);
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    private boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }

    public void SaveGame() {
        fb.clear();
        fb.setCoins(coins);
        fb.setLife(life);
        fb.setRound(round);
        for(int i =0; i<towers.size();i++)
            fb.setTower(towers.get(i).x,towers.get(i).y,towers.get(i).t);
    }

    public void setNewCoins(int coins) {
        this.coins = coins;
        invalidate();
    }

    public void setNewLife(int life) {
        this.life = life;
        invalidate();
    }

    public void setNewRound(int round) {
        this.round = round;
        invalidate();
    }

    public void setNewTowers(ArrayList<TowertoFb> Tfbs) {
        Tower lastTower;
        for (int i =0; i < Tfbs.size(); i++) {
            lastTower = new Tower(Tfbs.get(i).x,Tfbs.get(i).y,Tfbs.get(i).t,context);
            towers.add(lastTower); }
        invalidate();
    }


    private class ThreadGame extends Thread{
        @Override
        public void run() {
            super.run();
            while (true)
            {
                try {
                    sleep(40);
                    if(isRun)
                        handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}