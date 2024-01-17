package views;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import model.Game;

/** Main GUI */
public class MainGUI extends JFrame {
	private static final long serialVersionUID = 7376825297884956163L;
	java.awt.Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWith = (tailleEcran.width*2/3);
    int screenheight = (tailleEcran.height*2/3);

	/** The class responsible to navigate between pages */
	PageManager pageManager;

	/** The game model */
	public Game game;

	public MainGUI(Game game) {
		this.game = game;
		this.pageManager = new PageManager(game);
		
		this.setLayout(new BorderLayout());
		this.add(pageManager,BorderLayout.CENTER);

		this.setSize(screenWith,screenheight);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);

	}


}
