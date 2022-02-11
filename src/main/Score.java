import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle {

    static int Game_Width;
    static int Game_Height;
    int playe1;
    int playe2;

    public Score(int Game_Width, int Game_Height) {

        Score.Game_Width = Game_Width;
        Score.Game_Height = Game_Height;

    }

    public void draw(Graphics g){

        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));
        g.drawLine(Game_Width/2,0,Game_Width/2,Game_Height);
        g.drawString(String.valueOf(playe1/10) + String.valueOf(playe1%10),(Game_Width/2)-85,50);
        g.drawString(String.valueOf(playe2/10) + String.valueOf(playe2%10),(Game_Width/2)+20,50);

    }

}