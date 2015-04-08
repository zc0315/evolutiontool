package com.AlgoAnalysis.tools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	public void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		//	«Â∆¡
		super.paintComponent(arg0);
		arg0.drawImage(im, 0,0,this.getWidth(),this.getHeight(),this);
	}

	Image im;
	public ImagePanel(Image im){
		this.im=im;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(w, h);
	}
	
}