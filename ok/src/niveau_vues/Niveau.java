package niveau_vues;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;
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
		for(int i=1; i<nbShapes+1; i++){
			ToolShape toolshape= new ToolShape(this);
			BoardShape boardshape= new BoardShape(this);
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
			boardshape.createShape();
			toolshape.createShape();
		}
		
	}



}
