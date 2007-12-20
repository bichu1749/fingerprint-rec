/*-----------------------------------------------------------------------------+

			Filename			: PanelFingerprint.java
			Creation date		: 17 d�c. 07
		
			Project				: fingerprint-recog
			Package				: application.gui.panels

			Developed by		: Thomas DEVAUX & Estelle SENAY
			                      (2007) Concordia University

							-------------------------

	This program is free software. You can redistribute it and/or modify it 
 	under the terms of the GNU Lesser General Public License as published by 
	the Free Software Foundation. Either version 2.1 of the License, or (at your 
    option) any later version.

	This program is distributed in the hope that it will be useful, but WITHOUT 
	ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
	FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
    more details.

+-----------------------------------------------------------------------------*/

package application.gui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PanelFingerprint extends JPanel
{
	//---------------------------------------------------------- CONSTANTS --//

	//---------------------------------------------------------- VARIABLES --//	
	BufferedImage buffer;
	Point core;
	boolean isWorking;
	Image loadingIcon;
	Image coreIcon;
	int coreRadius;
	
	//------------------------------------------------------- CONSTRUCTORS --//	
	public PanelFingerprint() 
	{
		isWorking = false;
		loadingIcon = Toolkit.getDefaultToolkit().getImage("./ressources/loading.gif");
		coreIcon = Toolkit.getDefaultToolkit().getImage("./ressources/core.png");
	}
	//------------------------------------------------------------ METHODS --//	
	public void setBufferedImage (BufferedImage buffer)
	{
		this.buffer = buffer;
		repaint();
	}
	
	public void setCore (Point core, int coreRadius)
	{
		this.core = core;
		this.coreRadius = coreRadius;
		repaint();
	}
	
	public void setIsWorking(boolean isWorking)
	{
		this.isWorking = isWorking;
		repaint();
	}
	
	public void init()
	{
		core = null;
		buffer = null;
		repaint();
	}
	
	//---------------------------------------------------- PRIVATE METHODS --//
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		Graphics2D g2d=(Graphics2D) g;
		
		if (isWorking)
		{
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
							     RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			
			g2d.drawImage(	loadingIcon,
							Math.round(getWidth()*.5f) - 16,
							Math.round(getHeight()*.5f) - 16,
							32,
							32,
							this);
			return;
		}
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			     RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		if (core != null)
		{
			buffer.getGraphics().drawImage(	coreIcon,
											core.x-16,
											core.y-16,
											32,
											32,
											this);
			
			buffer.getGraphics().setColor(Color.white);
			buffer.getGraphics().drawOval(core.x-coreRadius, core.y-coreRadius, 2*coreRadius, 2*coreRadius);
		}
		
		if (buffer != null)
		{
			g.drawImage(buffer,0,0,getWidth(), getHeight(), this);
		}		
	}
}