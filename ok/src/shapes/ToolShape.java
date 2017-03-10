package shapes;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.ClickOnShape;
import fr.lri.swingstates.canvas.transitions.DragOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Click;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Release;
import utilities.Point;

public class ToolShape extends Shape {


	public boolean movable;
	public Point centreGravite;


	public ToolShape(Canvas c) {
		super(c);
		this.movable=true;
		computeCtrGrav();


	}
	//TODO: DEBUG LE ROTATE DU DEBUT

	public void computeCtrGrav(){
		if(pointList.size()!=0){
			int gx = 0;
			int gy = 0;
			for(int i=0; i<pointList.size();i++) {
				gx+= pointList.get(i).getX();
				gy+= pointList.get(i).getY();
			}
			this.centreGravite = new Point(this.getCenterX(),this.getCenterY());
		}
	}

	//regarde si la shape est à la même position qu'une des shapes grises, 
	
	public boolean samePosition(ArrayList<BoardShape> boardlist){
		for(BoardShape sh : boardlist){
			//si les deux shapes ont le meme nombre de sommets
			if( this.pointList.size()== sh.pointList.size()){
				//si les points sont les memes
				if( rec_equals(this.pointList, sh.pointList)){
					//on translate et on met a jour la shape bougee
					this.translateTo(sh.getCenterX(), sh.getCenterY());
					for(int i = 0; i<pointList.size();i++){
						this.pointList.set(i, sh.pointList.get(i).copy());
						//this.createShape();
					}
					//maj du centre de gravite
					computeCtrGrav();
					return true;
				}
				
			}
		}
		return false;
	}

	//verifie recursivement si les arraylist de points correspondent
	private boolean rec_equals(ArrayList<Point> tl_points, ArrayList<Point> sh_points){
		if(tl_points.size()==0 || sh_points.size()==0){

			return true;
		}

		for(int i = 0; i< tl_points.size(); i++){
			for(int j = 0; j< sh_points.size(); j++){
				if (tl_points.get(i).almstEquals(sh_points.get(j))){

					ArrayList<Point> newtl_points = create_newArray(tl_points, i);
					ArrayList<Point> newsh_points = create_newArray(sh_points, j);

					return rec_equals(newtl_points, newsh_points);

				}

			}
		}
		return false;

	}

	public void setMovable(boolean m){
		this.movable=m;
	}
	public boolean getMovable( ){
		return this.movable;
	}

	public void translate(double tx, double ty){
		this.translateBy(tx, ty);
		for(Point p: pointList){
			p.translate(tx, ty);
		}
		computeCtrGrav();
	}

	public void rotate(double d){
		//this.rotateBy(Math.toRadians(d));
		System.out.println(pointList.get(0).getX());
		for(Point p : pointList){
			p.rotate(Math.toRadians(d), this.centreGravite);
		}
		computeCtrGrav();
	}


	//Cree une arrayList a partir d'une arraylist sans l'objet a la place indice
	private ArrayList<Point> create_newArray(ArrayList<Point> prev_array, int indice){
		ArrayList<Point> new_array = new ArrayList<Point>();
		for(int i=0; i<prev_array.size(); i++){
			if(i!=indice) new_array.add(prev_array.get(i));
		}
		return new_array;
	}
}



