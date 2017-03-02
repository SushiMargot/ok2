package menu_vues;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.canvas.transitions.*;
import fr.lri.swingstates.sm.transitions.*;

import javax.swing.JFrame;

import boutons.BoutonMenu;

//import divers.BoutonMenu;

public class MenuPrincipal extends Canvas {
	
	
	
	/*Dedans : Un titre
	*
	* Un bouton qui amène vers le tutoriel
	*
	* Un bouton campagne qui amene vers la liste des menus
	*
	* Un bouton creation d'un niveau
	* 
	* Un bouton pour aller jouer à un niveau personnalisé
	* 
	*/
	
	public MenuPrincipal(int w, int h){
		super(w,h);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void addDragger(Canvas canvas) {

		// Add a label explaining what the user can do
		CText label = canvas.newText(20, 20,
				"Draggable buttons can be dragged with the right mouse button", new Font(
						"verdana", Font.PLAIN, 12));
		label.setFillPaint(Color.GRAY);

		// Create the state machine and attach it to the canvas
		CStateMachine dragger = new CStateMachine(canvas) {

			Point2D pressLocation;
			Point2D shapeLocation;
			CShape shape;

			public State idling = new State() {
				// The mouse button "BUTTON3" is the right mouse button
				// ("BUTTON1" is the left mouse button)
				Transition down = new PressOnTag("draggable",
						CStateMachine.BUTTON3, ">> dragging") {
					public void action() {
						pressLocation = getPoint();
						shape = getShape();
						shapeLocation = new Point2D.Double(shape.getCenterX(),
								shape.getCenterY());
					};
				};
			};
			public State dragging = new State() {
				Transition move = new Drag() {
					public void action() {
						// TODO Translate the shape (use shape.translateTo())
						Point2D delta = getPoint();
						delta.setLocation(delta.getX() - pressLocation.getX(),
								delta.getY() - pressLocation.getY());
						shape.translateTo(shapeLocation.getX() + delta.getX(),
								shapeLocation.getY() + delta.getY());
					};
				};
				Transition release = new Release(">> idling") {
				};
			};
		};

		
	}
	
	public void make_display(JFrame frame){
		BoutonMenu button1 = new BoutonMenu(this,"Choix des niveaux", frame);
		button1.getShape().translateBy(30, 50);
		
	button1.showStateMachine();
	addDragger(this);
	}
	
	/*
	 	static public void main(String[] args) {
	 
		System.out.println("ok");
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(200, 200));
		Canvas canvas = new Canvas(200,200);//potpourri.Constantes.fenetre_h, potpourri.Constantes.fenetre_l);
		
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		BoutonMenu button1 = new BoutonMenu(canvas,"Choix des niveaux");
		
		
		button1.getShape().translateBy(30, 50);
			
		button1.showStateMachine();
		System.out.println("ok");
		
	}
	*/
	

}
