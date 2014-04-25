package Res.GUI.Views;

import java.awt.*;
import java.util.List;

public class WeekView extends CalendarView{
	public WeekView(){
		super();
	}
	
	class EntryGFX{
		public EntryGFX(int x, int y, int height, int sharing){
			this.x = x;
			this.y = y;
			this.height = height;
			this.sharing = sharing;
		}
		int x,y,height,sharing;
	}
	
	
	public static final long WEEK_MILLIS = (long)604800000;
	
	public void nextInterval(){
		startTime.setTime(startTime.getTime() + WEEK_MILLIS);
		repaint();
	}
	public void prevInterval(){
		if (startTime.getTime() < WEEK_MILLIS) return;
		startTime.setTime(startTime.getTime() - WEEK_MILLIS);
		repaint();
	}
	
	/**
	 * Paints the full view.
	 * @override
	 */
	public void paintComponent(Graphics g){
		abstractPaintComponent(g);
		
		setBackground(new Color(250,232,155));

		int width = getWidth();
		int height = getHeight();
		
		int xstep = width / 7;
		int ystep = height / 24;
		int linewidth = 1;
		
		//draw grid
		g.setColor(new Color(0,0,0));
		if (width <= 1){
			for (int x = xstep;x+xstep <= getWidth();x+=xstep){
				g.drawLine(x,0,x,height);
			}
			for (int y = ystep;y+ystep <= getHeight();y+=ystep){
				g.drawLine(0,y,width,y);
			}
		}else{
			int diff = linewidth / 2;
			for (int x = xstep;x+xstep <= getWidth();x+=xstep){
				g.fillRect(x-(diff-linewidth % 2),0,linewidth,height);
			}
			for (int y = ystep;y+ystep <= getHeight();y+=ystep){
				g.fillRect(0,y-(diff-linewidth % 2),width,linewidth);
			}
		}
		
		java.util.List<EntryGFX> rects = new java.util.List<EntryGFX>();
		
mainIter:
		for (CalendarEntry e : entries){
			for (CalendarEntry c : entries){
				if (e != c && e.getInterval().intersect(c.getInterval())){
					
				}
			}
			
			rects.add(new EntryGFX());
		}
	}
}
