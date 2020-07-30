
import java.awt.*;
import java.awt.event.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harshit
 */
public class Solver extends Frame {
    int grid[][]={{0,0,1,0,6,0,0,5,9},
                  {0,0,0,0,0,3,0,2,0},
                  {0,6,0,0,8,0,0,0,0},
                  {4,0,0,0,0,0,5,0,0},
                  {0,2,0,0,0,0,0,0,0},
                  {0,7,0,2,0,0,4,8,0},
                  {8,0,0,0,0,0,9,0,5},
                  {7,0,0,6,0,9,0,3,0},
                  {0,0,5,0,0,0,0,4,0}    
    };
    int color[][] = new int[9][9];
    Solver()
    {
        this.setVisible(true);
        this.setFont(new Font("Serif", Font.PLAIN, 25));
        this.setSize(550, 550);
        addWindowListener(new WindowAdapter(){
                           @Override
   			public void windowClosing(WindowEvent e) { dispose(); System.exit(0); }
  	});
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                if(grid[i][j]==0)
                    color[i][j] = 0;
                else
                    color[i][j] = 1;
        solve();
        
        
    }
    boolean checkRow(Pair p,int n)
    {
        int y=p.getY();
        for(int i=0;i<9;i++)
        {
            if(grid[i][y]==n)
                return false;
        }
        return true;
    }
    boolean checkCol(Pair p,int n)
    {
        int x=p.getX();
        for(int i=0;i<9;i++)
        {
            if(grid[x][i]==n)
               return false;            
        }
        return true;
    }
    boolean checkBox(Pair p,int n)
    {
        int x=p.getX()/3;
        int y=p.getY()/3;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(grid[(x*3)+i][(y*3)+j]==n)
                    return false;       
        return true;
    }
    boolean isSafe(Pair p,int n)
    {
        return checkRow(p, n) & checkCol(p, n) & checkBox(p, n);
    }
    Pair find()
    {
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                if(grid[i][j]==0)
                    return new Pair(i,j);
        return null;
    }
    
    boolean solve()
    {
        Pair p = find();
        if(p==null)
            return true;
        for(int i=1;i<=9;i++)
        {
            color[p.getX()][p.getY()]=3;
            grid[p.getX()][p.getY()]=i;
            place(p,i);
            sleep();
            grid[p.getX()][p.getY()]=0;
            if(isSafe(p,i))
            {
                grid[p.getX()][p.getY()]=i;
                color[p.getX()][p.getY()]=2;
                place(p,i);
                sleep();
                if(solve())
                    return true;                
            }
        }
        grid[p.getX()][p.getY()]=0;
        clear(p,this.getGraphics());
        return false;
    }
    void fill(Graphics g)
    {
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                
                if(grid[i][j]!=0)
                    g.drawString(grid[i][j]+" ", 50 + j*20, 50 + i*20);
                else
                    g.drawString("  ", 50 + j*20, 50 + i*20);
                
            }    
        }
    }
    public void sleep()
    {
        try
        {
            Thread.sleep(0);
        } catch(Exception e){}
    }
    void place(Pair p, int n)
    {
        Graphics g=this.getGraphics();
        clear(p,g);
        int i = p.getX();
        int j = p.getY();
        if(color[i][j]==1)
            g.setColor(Color.black);
        else if(color[i][j]==2)
            g.setColor(Color.green);
        else
            g.setColor(Color.red);
        g.drawString(""+n, p.getY()*50 + 70, p.getX()*50 + 82);
        g.fillOval(100, 100, 10, 10);
    }
    void clear(Pair p, Graphics g)
    {
        if(g == null) {
          g = this.getGraphics();
        }
        g.clearRect(p.getY()*50 + 52, p.getX()*50+52, 47, 47);
        
    }
    @Override
    public void paint(Graphics g)
    {
        for(int i=0;i<=9;i++)
        {
            for(int j=0;j<=9;j++)
            {
                g.drawRect(50, 50, 50*j, 50*i);
            }
        }
        g.drawLine(199, 50, 199, 500);
        g.drawLine(201, 50, 201, 500);
        g.drawLine(199+150, 50, 199+150, 500);
        g.drawLine(201+150, 50, 201+150, 500);
        
        g.drawLine(50, 199, 500, 199);
        g.drawLine(50, 201, 500, 201);
        g.drawLine(50,199+150, 500, 199+150);
        g.drawLine(50, 201+150, 500, 201+150);
        
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                if(grid[i][j]!=0)
                    place(new Pair(i,j),grid[i][j]);
        
    }
}
