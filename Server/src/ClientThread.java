import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientThread extends Thread {

	static int Count=225;
	static Point qz[][]=new Point[15][15];
	Socket s;
	Socket c;
	Point p;
	ObjectInputStream in;
	ObjectOutputStream out;
	int SueForPeace =0;
	int color;
	int i;
	public ClientThread(Socket s,Socket c,int n,int i) throws IOException
	{
		this.i=i;
		this.s=s;
		this.c=c;
		in=new ObjectInputStream(s.getInputStream());
		out=new ObjectOutputStream(c.getOutputStream());
		//out.writeObject(n);
		color=n;
	}
	public void run()
	{
		try {
			out.writeObject(color);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true)
		{
			try {
				p=(Point)in.readObject();
				process(p);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("客户端"+ i +"链接断开，正在退出");
			
				try {
					c.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			
			
		}
	}
	public void process(Point p) throws IOException, ClassNotFoundException
	{
		
		switch(p.getTp())
		{
		case 0:

			qz[p.getX()][p.getY()]=p;
			Count--;
			out.writeObject(p);
				
			break;
		case 1:
			out.writeObject(p);
			if(SueForPeace==0)
				SueForPeace++;
			else
				restart();
			break;
		case 2:
			out.writeObject(p);
			SueForPeace--;
			break;
		case 3:
		case 4:
		case 5:
		case 6:
			out.writeObject(p);
			restart();
			break;
		}
		
	}
	public void restart() {
		// TODO Auto-generated method stub
		for(int i=0;i<15;i++)
			for(int n=0;n<15;n++)
				qz[i][n]=null;
		SueForPeace=0;
		Count=225;
		
	}

}
