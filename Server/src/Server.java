import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {
	
//	static Point[][]qz=new Point[15][15];
//	static int Count=225;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		  
		ServerSocket ss = new ServerSocket(9444);
		System.out.println("服务器创建成功");
		int n=Selection();
		Socket c1;
		
	
		
		
		c1=ss.accept();
		System.out.println("客户端1连接成功");
		
		
		Socket c2;	
	    c2=ss.accept();
		System.out.println("客户端2连接成功");
		
		
		ClientThread t1=new ClientThread(c1,c2,n,1);
		t1.start();
		ClientThread t2=new ClientThread(c2,c1,3-n,2);
		t2.start();
		while(true)
		{
			if(!t1.isAlive() && !t2.isAlive())
				break;
		}
		
		System.out.println("服务端已退出");
	
		System.exit(0); 
	
	
		
}
 
	public static int Selection()
	{
		Random random = new Random();
		if(random.nextInt()%2==1)
		return 1;
		else return 2;
		
	}
}
	
	