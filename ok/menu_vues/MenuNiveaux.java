package menu_vues;

import java.awt.Container;

import javax.swing.JFrame;

import boutons.BoutonMenu;
import fr.lri.swingstates.canvas.Canvas;

public class MenuNiveaux extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void make_display(JFrame frame){
		BoutonMenu button1 = new BoutonMenu(this,"Bien joue on dirait que t'as reussi", frame);
		button1.getShape().translateBy(30, 50);
		
	button1.showStateMachine();
	//addDragger(this);
	}
	
}
/*frame.setContentPane(panel); //panel = panel you want to change too.
frame.repaint();             //Ensures that the frame swaps to the next panel and doesn't get stuck.
frame.revalidate();  
*/