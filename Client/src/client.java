import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.io.ObjectOutputStream;

public  class client  {


	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		String ip = JOptionPane.showInputDialog("请输入Ip地址：");  
		Point p;
		
        CFrame a=new CFrame(ip);
       if(a.cb.getcolor()==1)
    	   a.cb.setstate(1);
          while(true)
          {try{
        	  p = (Point)a.cb.in.readObject();
          
		  switch(p.getTp()){
		    case 0:
		    	a.cb.setPoint(p);  
		    	break;
		    case 1:
		    	if(a.cb.getSFP()==0)
		    		{int j=JOptionPane.showConfirmDialog(null, "对方向您求和，您是否同意？", "提示", JOptionPane.YES_NO_OPTION); 
		    		if(j==JOptionPane.OK_OPTION)
		    			{a.cb.out.writeObject(new Point(-1,-1,-1,1));
				    	JOptionPane.showMessageDialog(a,"已和棋，游戏重新开始","提示",JOptionPane.INFORMATION_MESSAGE);

		    			a.cb.restart();
		    			}
		    		else
		    			a.cb.out.writeObject(new Point(-1,-1,-1,2));}
		    		else 
		    		{JOptionPane.showMessageDialog(a,"已和棋，游戏重新开始","提示",JOptionPane.INFORMATION_MESSAGE);

		    		a.cb.restart();}
		    	break;
		    case 2:
		    	a.cb.setSFP(a.cb.getSFP()-1);
		    	JOptionPane.showMessageDialog(a,"对方拒绝了你的请求，你等死吧哈哈！！","提示",JOptionPane.INFORMATION_MESSAGE);
		    	break;
		    case 3:
		    	
		    	JOptionPane.showMessageDialog(a,"对方已认输","提示",JOptionPane.INFORMATION_MESSAGE);
		    	a.cb.restart();
		    	break;
	        case 4:
	        	
	        	JOptionPane.showMessageDialog(a,"你输了！","提示",JOptionPane.INFORMATION_MESSAGE);
	        	a.cb.restart();
	       	    break;
	       case 5:
	    	   
	         	JOptionPane.showMessageDialog(a,"棋盘已没有多余位置，！","提示",JOptionPane.INFORMATION_MESSAGE);
	       	a.cb.restart();
	        	break;
	       case 6:
	        	JOptionPane.showMessageDialog(a,"游戏重新开始","提示",JOptionPane.INFORMATION_MESSAGE);
	        	a.cb.restart();
	       	    break;

	    	   
	      

	        	
	        
	  		  default:break;
	        }
          }
          catch(IOException e1){
        	  if(a.exit!=1)
				JOptionPane.showMessageDialog(a,"对方已退出游戏，游戏结束！","提示",JOptionPane.INFORMATION_MESSAGE);
        	  else JOptionPane.showMessageDialog(a,"游戏结束！","提示",JOptionPane.INFORMATION_MESSAGE);
        	  a.cb.ConnectionClose();
  			System.exit(0);
        	  
          }}
    
        	
        
      
	//}
	}

}
class CFrame extends JFrame implements ActionListener{
	public ChessBoard cb;
	 JMenuItem item1;
     JMenuItem item2;
     JMenuItem item3;
     JMenuItem item4;
     JLabel lb;
     int exit;
	public CFrame(String ip) throws UnknownHostException, IOException, ClassNotFoundException
	{
		
		Dimension screen = getToolkit().getScreenSize();
		this.setTitle("五子棋");
	    this.setBounds(screen.width/2-250, screen.height/2-270, 500, 540);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menubar = new JMenuBar();
        
       
        JMenu xx =new JMenu("选项");
        item1=new JMenuItem("和棋");
        item2=new JMenuItem("认输");
        item3=new JMenuItem("重新开始");
        item4=new JMenuItem("退出");
        
        
        xx.add(item1);
        xx.add(item2);
        xx.add(item3);
        xx.add(item4);
        
        menubar.add(xx);
        
        
        
        
        
        cb=new ChessBoard(ip);
        this.add(cb);
        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        item4.addActionListener(this);
        
        
        this.setJMenuBar(menubar);
        this.setVisible(true);   
	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==item1)
		{
			
			try {
				cb.out.writeObject(new Point(-1,-1,-1,1));
				cb.setSFP(1);
				JOptionPane.showMessageDialog(this,"已发出和棋请求！","提示",JOptionPane.INFORMATION_MESSAGE);
			    
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			}
		}
		else if(e.getSource()==item2)
		{
			try {
				cb.out.writeObject(new Point(-1,-1,-1,3));
				JOptionPane.showMessageDialog(this,"你输了！","提示",JOptionPane.INFORMATION_MESSAGE);
				try {
					cb.restart();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		else if(e.getSource()==item3)
		{
			try {
				cb.out.writeObject(new Point(-1,-1,-1,6));
				JOptionPane.showMessageDialog(this,"游戏重新开始","提示",JOptionPane.INFORMATION_MESSAGE);
				try {
					cb.restart();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==item4)
		{
			exit=1;
			cb.ConnectionClose();
			
			
			
			
		}
		
	}
	
	

}