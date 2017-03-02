package boutons;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import menu_vues.MenuNiveaux;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.canvas.transitions.*;
import fr.lri.swingstates.sm.transitions.*;

public class BoutonMenu {


	private CRectangle rectangle;
	private CText label;
	private CStateMachine sm;
	private String text;
	private int button;
	private int modifier;
	// Padding around the label
	private int padding = 5;
	// Border widths for the different states
	private int[] borderwidth = { 1, 2 };
	// Background colors for the different states
	private Color[] color = { Color.WHITE, Color.YELLOW };

	public BoutonMenu(Canvas canvas, String text, final JFrame frame) {
		this.button = CStateMachine.ANYBUTTON;
		this.modifier= CStateMachine.NOMODIFIER;
		// Create the label
		this.text = text;
		label = canvas.newText(0, 0, text, new Font("verdana", Font.PLAIN, 12));

		// The label should not generate mouse events
		label.setPickable(false);

		// TODO Build a rectangle around the label (use canvas.newRectangle()
		// with label.getMinX/Y(), label.getWidth/Height() and padding)
		rectangle = canvas.newRectangle(label.getMinX() - padding, 
				label.getMinY() - padding, 
				label.getWidth() + 2 * padding, 
				label.getHeight() + 2 * padding);

		// TODO Set the rectangle border (use rectangle.setStroke() with a BasicStroke)
		// and the background color (use setFillPaint() with color defined as an attribute)
		rectangle.setStroke(new BasicStroke(borderwidth[0]));
		rectangle.setFillPaint(Color.WHITE);

		// TODO Set the rectangle as the parent of the label, so that any
		// translation of the rectangle also translates the label
		label.setParent(rectangle);

		// TODO Put it below the label (last created shapes always appear on top
		// of previous shapes)
		rectangle.below(label);

		// Set the button behavior with this state machine
		sm = new CStateMachine() {
			// TODO Create the state machine for the button

			public State idling = new State() {
				Transition toOver = new EnterOnShape(">> over") {};
			};

			public State over = new State() {
				public void enter() {
					rectangle.setStroke(new BasicStroke(borderwidth[1]));
				}

				Transition leave = new LeaveOnShape(">> idling") {};
				Transition arm = new Press(button, modifier, ">> armed") {};

				public void leave() {
					rectangle.setStroke(new BasicStroke(borderwidth[0]));
				}
			};

			public State armed = new State() {
				public void enter() {
					rectangle.setFillPaint(color[1]);
					rectangle.setStroke(new BasicStroke(borderwidth[1]));
				}

				Transition disarm = new LeaveOnShape(">> disarmed") {};
				Transition act = new Release(button, modifier, ">> over") {
					public void action() {
						BoutonMenu.this.doAction(frame);
					}
				};

				public void leave() {
					rectangle.setFillPaint(color[0]);
					rectangle.setStroke(new BasicStroke(borderwidth[0]));
				}
			};

			public State disarmed = new State() {
				public void enter() {
					rectangle.setFillPaint(color[1]);
				}

				Transition rearm = new EnterOnShape(">> armed") {};
				Transition cancel = new Release(button, modifier, ">> idling") {};

				public void leave() {
					rectangle.setFillPaint(color[0]);
				}
			};
		};

		// TODO Attach the state machine to the button
		sm.attachTo(rectangle);
		
	}




	public CShape getShape() {
		return rectangle;
	}

	// Sets the draggable state of the button by adding or removing the
	// "draggable" tag
	public void setDraggable(boolean draggable) {
		if (draggable && !rectangle.hasTag("draggable")) {
			rectangle.addTag("draggable");
			rectangle.setOutlinePaint(Color.LIGHT_GRAY);
		} else if (rectangle.hasTag("draggable")) {
			rectangle.removeTag("draggable");
			rectangle.setOutlinePaint(Color.BLACK);
		}
	}

	// Gets the draggable state of a button
	public boolean getDraggable() {
		return rectangle.hasTag("draggable");
	}

	// Shows a graphical representation of the state machine in a separate
	// window
	public static void showStateMachine(CStateMachine sm) {
		JFrame viz = new JFrame();
		viz.getContentPane().add(new StateMachineVisualization(sm));
		viz.pack();
		viz.setVisible(true);
	}

	// Shows the state machine of the button
	public void showStateMachine() {
		showStateMachine(sm);
	}

	// Creates a state machine that drags any shape with the "draggable" tag
	// when the right mouse button is pressed
	@SuppressWarnings("unused")
	public void addDragger(Canvas canvas) {

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

		// Show the state machine
		showStateMachine(dragger);
	}
	public void doAction(JFrame frame){
		MenuNiveaux panel = new MenuNiveaux();
		frame.setContentPane(panel); //panel = panel you want to change too.
		panel.make_display(frame);
		frame.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
		frame.revalidate();  
		//backtomenu
	}
}

				