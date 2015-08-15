package tracking;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {

        private static boolean REPAINT_SHADOW = true;
 	    private  Color startColor = new Color(49, 120, 206);
	    private  Color endColor = new Color(35, 84, 146);
	    private int roundRectSize = 15;

	  public TitlePanel(int w,int h) {
	        super();
	        setForeground(Color.WHITE);
	        setPreferredSize(new Dimension(w,h));
	       
	       
	    }
	  
	  
	  /**
		 * Override the default paintComponent method to paint the gradient in the panel.
		 *
		 * @param g
		 */
	 
	  public void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D)g.create();
	        int h = getHeight();
	        int w = getWidth();
	        GradientPaint GP = new GradientPaint(0, 0, startColor, 0, h, endColor, true);
	        g2d.setPaint(GP);
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        GradientPaint p1;
	        GradientPaint p2;
	        p1 = new GradientPaint(0, 0,startColor, 0, h - 1, endColor);
            p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3, new Color(40,150,255));
            g2d.setPaint(Color.BLACK);
	        g2d.drawRoundRect(0, 0, w - 1, h - 1, roundRectSize, roundRectSize);
	        g2d.setPaint(p1);
	        g2d.fillRoundRect(1,1, w - 2, h - 2, roundRectSize, roundRectSize);
	        _drawRect(g2d,w);
	        super.paintComponents(g2d);
	    }
	 
	    
	    /**
	     * This method is used for drawing Rectangle Shape
	     * @param g2
	     * @param w
	     * @return void
	     */
	    
	    private void _drawRect(Graphics2D g2,int w)
		{	
	    	Dimension d = getSize();
         	int h=(int)d.getHeight();
				GradientPaint p1;
	        GradientPaint p2;
	        p1 = new GradientPaint(0,0,Color.WHITE , 0,11,endColor);
	        p2 = new GradientPaint(0,0,Color.WHITE , 0,10,endColor);
            g2.setPaint(Color.WHITE);
            g2.fillRoundRect(4,1,w-8,8,20,20);
    		g2.setPaint(p1);
    		g2.fillRoundRect(4,2,w-8,7,20,20);
    		g2.setPaint(p2);
    		g2.fillRoundRect(7,9,w-13,h-15,15,15);
		
	}
	   
		
}