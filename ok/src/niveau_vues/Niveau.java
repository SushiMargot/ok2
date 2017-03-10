package niveau_vues;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.ClickOnShape;
import fr.lri.swingstates.canvas.transitions.DragOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import shapes.BoardShape;
import shapes.Shape;
import shapes.ToolShape;
import utilities.Constantes;
import utilities.Point;

public class Niveau extends Canvas{
	private String file;
	private ArrayList<ToolShape> tlShapes;
	private ArrayList<BoardShape> bdShapes;
	private double h_entete;
	private double h_tools;
	private double h_board;
	private Point2D prevPoint;
	private CShape shape;

	public Niveau(String f, int w, int h){
		super(w,h);
		
		//Initialisation des positions des zones
		this.h_entete =  0;
		this.h_tools = this.h_entete+ Constantes.fenetre_h * 0.15;
		this.h_board = this.h_tools + Constantes.fenetre_h * 0.35;
		System.out.println(" htruc" + h_tools + " " + h_board );
		this.file=f;
		tlShapes= new ArrayList<ToolShape>();
		bdShapes= new ArrayList<BoardShape>();
		try {
			openFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//prevPoint = new Point2D();
		//du caca

		CStateMachine shapeMachine = new CStateMachine() {
			public State start = new State() {
				PressOnShape click = new PressOnShape(BUTTON1, NOMODIFIER, ">> move") {
					public void action() {
						shape=getShape();
						prevPoint = getPoint();
						if(shape instanceof ToolShape ){
							Paint initColor = shape.getFillPaint();
							
							
							shape.setFillPaint(Color.PINK);
						}
					}
				};
				PressOnShape rightClick = new PressOnShape(BUTTON3, NOMODIFIER) {
					public void action() {
						shape=getShape();
						prevPoint = getPoint();
						if(getShape() instanceof ToolShape){
							Paint initColor = shape.getFillPaint();
							
							
							shape.setFillPaint(Color.PINK);
							((ToolShape) shape).rotate(45);
							shape.rotateBy(Math.toRadians(45));
						}
					}
				};

			};
			State move = new State() {
				DragOnShape drag = new DragOnShape(BUTTON1, NOMODIFIER) {
					public void action() {
						shape=getShape();
						if(shape instanceof ToolShape){

							Point2D new_point = getPoint();	
							((ToolShape)shape).translate(new_point.getX()-prevPoint.getX(), new_point.getY()-prevPoint.getY());
							prevPoint=new_point;
						}
					}
				};	
				Release release = new Release(BUTTON1, NOMODIFIER, ">> start") {
					public void action() {
						//TODO: parcour des bdshapes
						if(shape instanceof ToolShape && ((ToolShape) shape).samePosition(bdShapes)){
							((ToolShape) shape).setMovable(false);

								shape.setFillPaint(Color.BLUE);
						}
					}
				};
			};
		};
		shapeMachine.attachTo(this);



	}






	public void displayEntete(){

	}

	public void displayTools(){


	}




	private void openFile(String filename) throws IOException{

		String level="";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			level = sb.toString();

		} finally {
			br.close();
		}

		String[] parts=level.split("\n");
		int nbShapes = Integer.parseInt(parts[0]);
		for(int i=1; i<parts.length; i++){
			System.out.println(parts[i]);
			BoardShape boardshape= new BoardShape(this);
			ToolShape toolshape= new ToolShape(this);

			
			
			
			//On separe les def de couleurs des def des points
			String[] color_and_points = parts[i].split("/");
			//traitement des couleurs
			String[] colors = color_and_points[0].split(" ");
			toolshape.setColor(colors);
			//traitement des points
			String[] def_shape= color_and_points[1].split(" ");

			for(int j=0; j<def_shape.length; j+=2){
				Point p_tools = new Point (Integer.parseInt(def_shape[j]), Integer.parseInt(def_shape[j+1]));
				Point p_board = new Point (Integer.parseInt(def_shape[j]), Integer.parseInt(def_shape[j+1]));

				p_board.translate(0,h_board);

				toolshape.addPoint(p_tools);
				System.out.println(p_board.x + " "+p_board.y);
				System.out.println(p_tools.x + " "+p_tools.y);

				boardshape.addPoint(p_board);
			}
			toolshape.createShape();
			boardshape.createShape();
			tlShapes.add(toolshape);
			bdShapes.add(boardshape);
		}

	}



}
