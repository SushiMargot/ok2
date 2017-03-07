package shapes;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.ClickOnShape;
import fr.lri.swingstates.canvas.transitions.DragOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Click;
import utilities.Point;

public abstract class Shape {
	ArrayList<Point> pointList;
	CPolyLine shape;
	Canvas cnvs;
	Color clr;


	public Shape(Canvas c){	
		pointList=new ArrayList<Point>();
		this.shape=new CPolyLine();
		this.cnvs=c;
		shape =cnvs.newPolyLine();


		

	}

	public void addPoint(int x, int y){
		Point p = new Point((double)x,(double)y);
		pointList.add(p);
	}

	public void addPoint(Point p) {
		pointList.add(p);
	}

	public void setColor(String[] colors){
		clr=new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]),Integer.parseInt(colors[2]));
	}

	//TODO: faire classe Point
	public void createShape(){
		Point frstPoint = pointList.get(0);
		this.shape.reset(frstPoint.x, frstPoint.y);
		for(int i = 1; i<pointList.size();i++){
			this.shape.lineTo(pointList.get(i).x, pointList.get(i).y);
		}
		//on referme
		this.shape.lineTo(frstPoint.x,frstPoint.y);
		this.shape.setFillPaint(clr);
	}



}
