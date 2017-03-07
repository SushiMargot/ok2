package shapes;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.ClickOnShape;
import fr.lri.swingstates.canvas.transitions.DragOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Click;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Release;
import utilities.Point;

public class ToolShape extends Shape {

	private boolean isPlaced;
	private Point2D prevPoint;
	
	public ToolShape(Canvas c) {
		super(c);
		this.isPlaced=false;
		//du caca
				CStateMachine shapeMachine = new CStateMachine() {
					public State start = new State() {
						ClickOnShape click = new ClickOnShape(BUTTON1, NOMODIFIER, ">> move") {
							public void action() {
								//IL FAUT ABSOLUMENT VIRER CETTE CONDITION
								if(getShape().equals(shape)){
								Paint initColor = shape.getFillPaint();
								prevPoint = getPoint();
								shape.setFillPaint(Color.PINK);
								}
							}
						};
						
					};
					State move = new State() {
						DragOnShape drag = new DragOnShape(BUTTON1, NOMODIFIER) {
							public void action() {
								if(getShape().equals(shape)){
								Point2D new_point = getPoint();	
								shape.translateBy(new_point.getX()-prevPoint.getX(), new_point.getY()-prevPoint.getY());
								prevPoint=new_point;
							}
							}
						};	
						Release release = new Release(BUTTON1, NOMODIFIER, ">> start") {
							public void action() {
								
							}
						};
					};
				};
				shapeMachine.attachTo(cnvs);

				//fin du caca
	}

	public boolean equals(BoardShape sh){
		if(this.pointList.size()!= sh.pointList.size())
			return false;
		else{
			this.isPlaced= rec_equals(this.pointList, sh.pointList);
			return this.isPlaced;
		}
	}
	
	//verifie recursivement si les arraylist de points correspondent
	private boolean rec_equals(ArrayList<Point> tl_points, ArrayList<Point> sh_points){
		if(tl_points.size()==0 && sh_points.size()==0){
			return true;
		}
	
		for(int i = 0; i< tl_points.size(); i++){
			for(int j = 0; j< sh_points.size(); j++){
				if (tl_points.get(i).equals(sh_points.get(j))){
					ArrayList<Point> newtl_points = create_newArray(tl_points, i);
					ArrayList<Point> newsh_points = create_newArray(sh_points, j);
					rec_equals(newtl_points, newsh_points);
					
				}
			}
		}
		return false;
		
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



