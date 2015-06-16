import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ChessBoard extends  JPanel implements MouseListener {
	Image bg;
	Image white;
	Image black;
	int color;
	
	private int state;
	private int x;
	private int y;
	private int SueForPeace;
	private Socket sc;
	private static int Count=225;

	ObjectOutputStream out;
	ObjectInputStream in;
	Point[][]p=new Point[15][15];; 


	public ChessBoard(String ip) throws UnknownHostException, IOException, ClassNotFoundException{
	
		
		sc = new Socket(ip,9444);
		
	
		out=new ObjectOutputStream(sc.getOutputStream());
		in=new ObjectInputStream(sc.getInputStream());
	
		SueForPeace=0;
		
		color= (int)in.readObject();
	
		
		white=new ImageIcon("F:\\JAVA\\Client\\src\\white.jpg").getImage();
		black=new ImageIcon("F:\\JAVA\\Client\\src\\black.jpg").getImage();
		bg=new ImageIcon("F:\\JAVA\\Client\\src\\qipan.jpg").getImage();
		
		addMouseListener(this);
		state=0;
		x=-1;
		y=-1;
	}
	
	public int getSFP()
	{
		return SueForPeace;
	}
	public void setSFP(int n)
	{
		SueForPeace=n;;
	}
	public void setstate(int x)
	{state=x;}

	public void ConnectionClose()
	{
		try {
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void paintComponent(Graphics g) {
    
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, this);
		g.drawString("我方执子：                              对方执子：", 130, 17);
		if(color==1)
		{
			g.drawImage(black, 200, 8,16,16,this);
			g.drawImage(white, 350, 8,16,16,this);
		}
		else{
			g.drawImage(white, 200, 8,16,16,this);
			g.drawImage(black, 350, 8,16,16,this);
		}
		for(int i=0;i<15;i++)
		{g.drawLine(30, 30+i*30, 450, 30+i*30);
		g.drawLine(30+i*30, 30, 30+i*30, 450);}
	
	    g.fillOval(117, 117, 6, 6);
	    g.fillOval(237, 237, 6, 6);
	    g.fillOval(357, 357, 6, 6);
	    g.fillOval(117, 237, 6, 6);
	    g.fillOval(117, 357, 6, 6);
	    g.fillOval(237, 117, 6, 6);
	    g.fillOval(237, 357, 6, 6);
	    g.fillOval(357, 117, 6, 6);
	    g.fillOval(357, 237, 6, 6);

	  
	    for(int i=0;i<15;i++)
	    	for(int n=0;n<15;n++)
	    	if(p[i][n]!=null && p[i][n].getColor()==1)
	    	g.drawImage(black,i*30+30-10, n*30+30-10,20,20,this);
	    	else if(p[i][n]!=null && p[i][n].getColor()==2)
	    		g.drawImage(white,i*30+30-10, n*30+30-10,20,20,this);
    

	if(x!=-1 && y!=-1)
	{g.setColor(Color.gray);
	g.drawRect(x*30+19, y*30+19, 22, 22);
	g.setColor(Color.black);
	}	
	}

	
public void restart() throws ClassNotFoundException, IOException
{ for(int i=0;i<15;i++)
	for(int n=0;n<15;n++)
		p[i][n]=null;
        x=-1;
        y=-1;
        color=3-color;
        repaint();
        if(color==1)
        {state=1;
        }
        else if(color==2)
        {state=0;
        }
	}
	public void mouseClicked(MouseEvent e) {
	
		int Tp=0;

		if(state==1)
	{
			if(p[(e.getX()-15)/30][(e.getY()-15)/30]==null && e.getX()>=20 && e.getX()<=460 && e.getY()>=20 && e.getY()<=460)
			{
	p[(e.getX()-15)/30][(e.getY()-15)/30]=new Point((e.getX()-15)/30,(e.getY()-15)/30,color,0);
		
		x=(e.getX()-15)/30;
		y=(e.getY()-15)/30;
	
		repaint();
		state=0;
		if(Check(x,y)==1)
		{	Tp=4;
		try {
			out.writeObject(new Point(x,y,color,Tp));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(this,"你赢了！","提示",JOptionPane.INFORMATION_MESSAGE);
		try {
			restart();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		}
		else if(Count==0)
		{	Tp=5;
       	try {
			out.writeObject(new Point(x,y,color,Tp));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
       	JOptionPane.showMessageDialog(this,"棋盘已没有多余位置，和棋！","提示",JOptionPane.INFORMATION_MESSAGE);
       	try {
			restart();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		}
		else
		try {
			out.writeObject(new Point(x,y,color,Tp));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	}
	
	}
	public int getx()
	{return x;}
	public int gety()
	{return y;}
	public int getcolor()
	{return color;}
	public void setPoint(Point pt)
	{
		p[pt.getX()][pt.getY()]=pt;
		this.x=pt.getX();
		this.y=pt.getY();
		repaint();
		state=1;
		Count--;
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public  int Check(int x,int y)
	{
		int c=p[x][y].getColor();
		int continueCount=1;
		for(int l=x;l>0;l--)
		{
			if(p[l-1][y]!=null && p[l-1][y].getColor()==c)
				continueCount++;
			else break;
		}
		for(int r=x;r<15;r++)
		{
			if(p[r+1][y]!=null && p[r+1][y].getColor()==c)
				continueCount++;
			else break;
		}
		if(continueCount>=5)
			return 1;
		
		
		continueCount=1;
		for(int t=y;t>0;t--)
		{
			if(p[x][t-1]!=null && p[x][t-1].getColor()==c)
				continueCount++;
			else break;
		}
		for(int b=y;b<15;b++)
		{
			if(p[x][b+1]!=null && p[x][b+1].getColor()==c)
				continueCount++;
			else break;
		}
		if(continueCount>=5)
			return 1;
		
		
		continueCount=1;
		for(int l=x,t=y; t>0 && l>0 ; t--,l--)
		{
			if(p[l-1][t-1]!=null && p[l-1][t-1].getColor()==c)
				continueCount++;
			else break;
		}
		for(int r=x,b=y;r<15 && b<15;b++,r++)
		{
			if(p[r+1][b+1]!=null && p[r+1][b+1].getColor()==c)
				continueCount++;
			else break;
		}
		if(continueCount>=5)
			return 1;
		
		
		
		continueCount=1;
		for(int r=x,t=y; t>0 && r<15 ; t--,r++)
		{
			if(p[r+1][t-1]!=null && p[r+1][t-1].getColor()==c)
				continueCount++;
			else break;
		}
		for(int l=x,b=y;l>0 && b<15;b++,l--)
		{
			if(p[l-1][b+1]!=null && p[l-1][b+1].getColor()==c)
				continueCount++;
			else break;
		}
		if(continueCount>=5)
			return 1;
		

		return 0;
	}
	

	 }

