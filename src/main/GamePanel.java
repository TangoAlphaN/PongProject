import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    //attributes
    static final int Game_Width = 1000;
    static final int Game_Height = (int)(Game_Width*(0.5555));
    static final Dimension Screen_Size = new Dimension(Game_Width, Game_Height);
    static final int Ball_Diameter = 20;
    static final int Paddle_Wight = 25;
    static final int Paddle_Height = 100;

    //instances
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score Score;

    public GamePanel() {

        newPaddles();
        newBall();
        Score = new Score(Game_Width, Game_Height);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(Screen_Size);

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void newBall(){

        random = new Random();
        ball = new Ball((Game_Width/2)-(Ball_Diameter/2),random.nextInt(Game_Height-Ball_Diameter),Ball_Diameter,Ball_Diameter);

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
        ball.draw(g);
        Score.draw(g);
        Toolkit.getDefaultToolkit().sync();

    }

    public void move(){

        paddle1.move();
        paddle2.move();
        ball.move();

    }

    public void checkCollision() {

        //bounce ball off top & bottom window edges
        if (ball.y <= 0)
            ball.setYDirection(-ball.yVelocity);
        if (ball.y >= Game_Height - Ball_Diameter)
            ball.setYDirection(-ball.yVelocity);

        //bounce ball off paddles
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //stop paddles at window edges
        if (paddle1.y <= 0)
            paddle1.y = 0;

        if (paddle1.y >= (Game_Height - Game_Height))
            paddle1.y = Game_Height - Game_Height;

        if (paddle2.y <= 0)
            paddle2.y = 0;

        if (paddle2.y >= (Game_Height - Game_Height))
            paddle2.y = Game_Height - Game_Height;

        //give a player 1 point and creates new paddles & ball
        if (ball.x <= 0) {
            Score.playe2++;
            newPaddles();
            newBall();
            System.out.println("Player 2 : " + Score.playe2);
        }

        if (ball.x >= Game_Width - Ball_Diameter) {
            Score.playe1++;
            newPaddles();
            newBall();
            System.out.println("Player 1 : " + Score.playe1);


        }

    }

    public void run() {

        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;

        while(true){
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;

            if(delta >= 1){
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