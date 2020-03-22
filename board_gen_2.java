package applet_programs;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class board_gen_2 extends Applet implements MouseListener 
{
    int xpos=0;
    int ypos=0;
    int m=0,n=0;
    int a=0,k=1;
    int board[][] = new int[10][10];
    int check[] = new int[100];
    int attacked[] = new int[100];
    int torpedos=0,targets=0,destroyed=0,prev_targets=0;
    int p=1,q=2;
    int score1=0,score2=0;
    int i=0,j=0;

    public void init()  
    {addMouseListener(this);} 

    public void paint(Graphics g)
    {
        coordinatize();
        Color c = new Color(80,200,220); 
        Color d = new Color(0,0,220);
        Color e = new Color(187,187,187);
        Color f = new Color(121,121,121);

        if(xpos==0 && ypos==0)
        {
            m=-1;
            n=-1;
        }

        if(p==1)
            q=2;
        if(p==2)
            q=1;

        if(k==1)
        {
            g.drawString("Player "+q+" Click on the map grid squares to position targets",600,150);
            g.drawString("Player "+q+" Click once outside the grid to accept the positions",600,165);
            g.drawString("and begin the attack",650,180);
            if((m>10 || n>10))
            {

                if(p==1 || a==1)
                {
                    k++;
                    g.clearRect(500,0,1000,1000);
                    g.drawString("Player "+p+" Take control and click the grid squares to launch torpedo at it",600,150);
                    g.drawString("Player "+p+" Remember that you have 100 torpedos and any square can be",600,165);
                    g.drawString("attacked multiple times",650,180);
                }
                else
                {
                    a++;
                    board(g,c,e);
                }

            }
            else
                board(g,c,e);

        }

        if(k==2)
        {
            for(i=0;i<10;i++)
            {
                for(j=0;j<10;j++)
                {
                    if(board[j][i]==1)
                        targets++;
                }
            }

            if(prev_targets!=targets && prev_targets!=0)
            {
                k--;
                targets=0;
                for(i=0;i<10;i++)
                {
                    for(j=0;j<10;j++)
                    {
                        board[j][i]=0;
                    }
                }
                board(g,c,e);
                g.clearRect(500,0,1000,1000);
                g.drawString("Position the targets again, same number as player 2 did",600,400);
            }

            else 
                k++;

        }

        if(k==3)
        {
            g.setColor(Color.black);
            g.drawString("Shots fired "+torpedos,600,250);

            for(i=0;i<10;i++)
            {
                for(j=0;j<10;j++)
                {
                    if(((board[j][i]==1) && i==m && j==n) || check[i*10+j]==1)
                    {
                        if(check[i*10+j]!=1)
                            check[i*10+j]=1;
                        g.setColor(f);
                        g.fillRect(i*50,j*50,50,50);}

                    else if(((board[j][i]!=1) && i==m && j==n) || attacked[i*10+j]==1)
                    {
                        if(attacked[i*10+j]!=1)
                            attacked[i*10+j]=1;
                        g.setColor(d);
                        g.fillRect(i*50,j*50,50,50);
                    }

                    else
                    {
                        g.setColor(c);
                        g.fillRect(i*50,j*50,50,50);
                    }
                    g.setColor(Color.black);
                    g.drawRect(i*50,j*50,50,50);
                }
            }

            for(i=0;i<10;i++)
            {
                for(j=0;j<10;j++)
                {
                    if(check[i*10+j]==1)
                        destroyed++;
                }
            }

            if(targets==destroyed && targets!=0)
            {
                g.drawString("Round over! Player "+p+" scored "+(100-torpedos)*50+" points!",600,300);
                if(p==1)
                {
                    g.drawString("Click once outside for Player 2's turn",600,315);
                    score1=(100-torpedos)*50;
                    torpedos=-1;
                    prev_targets=targets;
                    targets=0;
                    destroyed=0;
                    p=2;
                    k=1;

                    for(i=0;i<10;i++)
                    {
                        for(j=0;j<10;j++)
                        {
                            check[i*10+j]=0;
                            attacked[i*10+j]=0;
                            board[j][i]=0;
                        }
                    }
                }

                if(p==2 && q!=2)
                {
                    score2=(100-torpedos)*50;
                    if(score1>score2)
                        g.drawString("Player 1 wins by a difference of "+(score1-score2)+" points",600,350);
                    if(score2>score1)
                        g.drawString("Player 2 wins by a difference of "+(score2-score1)+" points",600,350);
                    if(score1==score2)
                        g.drawString("It's a tie!",600,350);

                    g.drawString("GAME OVER! Close the applet window to avoid further unnecessary computations",200,600);
                }
            }
            torpedos++;
            destroyed=0;
        }
    }

    public void coordinatize()
    {
        m=xpos/50;
        n=ypos/50;
    }

    public void board(Graphics g, Color c, Color e)
    {
        for(i=0;i<10;i++)
        {
            for(j=0;j<10;j++)
            {
                if((i==m && j==n) || board[j][i]==1)
                {
                    board[j][i]=1;
                    g.setColor(e);
                    g.fillRect(i*50,j*50,50,50);}
                else
                {
                    g.setColor(c);
                    g.fillRect(i*50,j*50,50,50);
                }
                g.setColor(Color.black);
                g.drawRect(i*50,j*50,50,50);
            }
        }
    }

    public void mouseClicked (MouseEvent me) 
    {
        xpos = me.getX(); 
        ypos = me.getY(); 
        repaint();
    } 

    public void mouseEntered (MouseEvent me) {} 
    public void mousePressed (MouseEvent me) {} 
    public void mouseReleased (MouseEvent me) {}  
    public void mouseExited (MouseEvent me) {}  
}