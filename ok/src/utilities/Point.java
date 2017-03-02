package utilities;

public class Point {
	
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
	

}
