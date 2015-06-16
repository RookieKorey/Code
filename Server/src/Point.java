import java.awt.Color;
import java.io.Serializable;


public class Point implements Serializable {  
	  private int x; 
	  private int y; 
	  private int color; 
	  private int tp;

	    
	  public Point(int x,int y,int color,int tp){  
	      this.x=x;  
	      this.y=y;  
	      this.color=color;  
	      this.tp=tp;
	  }   
	    
	  public int getTp(){
		  return tp;
	  }
	  public int getX(){ 
	      return x;  
	  }  
	  public int getY(){  
	      return y;  
	  }  
	  public int getColor(){ 
	      return color;  
	  }  }