package menu_vues;


import java.awt.Dimension;

import javax.swing.JFrame;

import fr.lri.swingstates.canvas.Canvas;

public class FramePrincipale {
	static public void main(String[] args) {
		System.out.println("ok");
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(divers.Constantes.fenetre_h, divers.Constantes.fenetre_l));
		Canvas canvas = new MenuPrincipal(divers.Constantes.fenetre_h, divers.Constantes.fenetre_l);//potpourri.Constantes.fenetre_h, potpourri.Constantes.fenetre_l);
		((MenuPrincipal) canvas).make_display(frame);
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
}
