package views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** The game home page view */
public class Welcome extends JPanel {
	/** A class responsible to navigate between page */
	PageManager pageManager;
	
	/** Start button */
	JButton startButton;

	/** Welcome label */
	JLabel welcomeLabel;
	
	public Welcome(PageManager pageManager) {
		super();
		this.pageManager = pageManager;
		
		this.setPreferredSize(new Dimension(FrameSize.HEAD_WIDTH, FrameSize.HEAD_HEIGHT));
		
		// set layout
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		// Create components
	    startButton = new JButton("Commencer");
	    
	     welcomeLabel = new JLabel("BATAILLE NAVALE");
	     welcomeLabel.setVerticalAlignment(JLabel.TOP);
	     welcomeLabel.setFont(new Font("Verdana", Font.PLAIN, 70));
	     
	     manageEvent();
	     
	     // Add components to panel
	     this.add(welcomeLabel, gbc);
	     this.add(startButton, gbc);
	     
		 setVisible(true);
		
	}
	
	/**
	 * Event manager
	 */
	public void manageEvent() {
		startButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {
				  startButtonClicked();
				  } 
				} );
	}
	
	/**
	 * Handler for start button click
	 */
	public void startButtonClicked() {
		pageManager.nextPage();
	}

}
