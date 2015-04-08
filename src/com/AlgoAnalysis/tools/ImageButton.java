package com.AlgoAnalysis.tools;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class ImageButton extends JButton{

	Image im;
	
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(im, 0,0,this.getWidth(),this.getHeight(),this);
	}
	public ImageButton(String impath){
		try {
			this.im=ImageIO.read(new File(impath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setSize(50, 65);
	}
}
