package utilities;

import java.awt.geom.Point2D;

public class Point extends Point2D{
	
	public static final int seuil = 30;
	
	public double x;
	public double y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void translate(double tx, double ty){
		this.x=x+tx;
		this.y=y+ty;
	}
	public Point copy(){
		return new Point(this.x, this.y);
	}
	
	
	public boolean almstEquals(Point p){
	//	System.out.println(this.x);
		//System.out.println(this.y);
		return (Math.abs(this.x-p.x)<seuil && Math.abs(this.y-p.y)<seuil);
	}
	public void rotate(double d, Point g){
		double c=Math.cos(d);
		double s = Math.sin(d);
		this.x-=g.getX();
		this.y-=g.getY();
	
		double xnew = x * c - y * s;
		double ynew = x * s + y * c;

		  // translate point back:
		  this.x = (double)(int)xnew + g.getX();
		  this.y = (double)(int)ynew + g.getY();
	/*	 
		=x+x*Math.cos(d)-y*Math.sin(d);
		this.y=y+y*Math.cos(d)+x*Math.sin(d);
		*/
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	@Override
	public void setLocation(double x, double y) {
		this.x=x;
		this.y=y;
		// TODO Auto-generated method stub
		
	}

}
