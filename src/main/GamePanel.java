import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    //attributes
    static final int Game_Width = 1000;
    static final int Game_Height = (int)(Game_Width*(0.5555));
    static final Dimension Scree_Size = new Dimension(Game_Width, Game_Height);
    static final int Ball_Diameter = 20;
    static final int Paddle_Wight = 25;
    static final int Paddle_Height = 100;

    //instances
    Thread gameThread;
    Image image;
    Graphics graphics;
    Ramdom ramdom;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    public GamePanel() {

        newPaddles();
        newBall();
        score = new Score(Game_Width, Game_Height);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(Scree_Size);

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void newBall(){

    }

    public void newPaddles(){

        paddle1 = new Paddle(0,(Game_Height/2)-(Paddle_Height/2), Paddle_Wight, Paddle_Height,1);
        paddle2 = new Paddle(Game_Width-Paddle_Wight,(Game_Height/2)-(Paddle_Height/2), Paddle_Wight, Paddle_Height,2);


    }

    public void paint(Graphics g){

        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }

    public void draw(Graphics g){

        paddle1.draw(g);
        paddle2.draw(g);

    }

    public void move(){

        paddle1.move();
        paddle2.move();
        ball.move();

    }

    public void checkCollision(){

        //stop paddles at window edges
        if(paddle1.y <= 0)
            paddle1.y = 0;
        if(paddle1.y >= (Game_Height-Game_Height))
            paddle1.y = Game_Height-Game_Height;
        if(paddle2.y <= 0)
            paddle2.y = 0;
        if(paddle2.y >= (Game_Height-Game_Height))
            paddle2.y = Game_Height-Game_Height;

    }

    public void run(){

        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;

        while(true) {
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;

            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }

        }

    }

    public class AL extends KeyAdapter {

        public void KeyPressed(KeyEvent e) {

            paddle1.KeyPressed(e);
            paddle2.KeyPressed(e);

        }

        public void KeyReleased(KeyEvent e) {

            paddle1.KeyReleased(e);
            paddle2.KeyReleased(e);

        }

    }

}