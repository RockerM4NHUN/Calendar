package Res.GUI.Views;

import java.sql.Timestamp;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import Res.Bin.*;

public class WeekView extends CalendarView{
	public WeekView(){
		super();
		setPreferredSize(new Dimension(56 + 7 * 130,48 + 24 * 30));
		gfxEntries = null;
		selected = null;
		
		addSelectionChangedListener(new CalendarSelectionChangedListener(){
			public void selectionChanged(CalendarEntry e){
				repaint();
			}
		});
	}
	
	class EntryGFX{
		public EntryGFX(CalendarEntry entry, int x, int y, int width, int height, int sharing, int placing, Color foregroundColor, Color backgroundColor){
			this.entry = entry;
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.sharing = sharing;
			this.placing = placing;
			this.foregroundColor = foregroundColor;
			this.backgroundColor = backgroundColor;
		}
		CalendarEntry entry;
		int x,y,height,sharing,placing,width;
		Color backgroundColor,foregroundColor;
		
		public boolean intersect(int x, int y){
			return (this.x <= x && x <= this.x + width && this.y <= y && y <= this.y + height);
		}
	}
	
	class Intersection{
		public Intersection(Interval interval, int count){
			this.interval = interval;
			this.count = count;
		}
		Interval interval; //interval int time
		int count;
	}
	
	
	private int width;
	private int height;
	private int firstVerticalLine;
	private int firstHorizontalLine;
	private int finalVerticalGap;
	private int finalHorizontalGap;
	private int xstep;
	private int ystep;
	private int finalVerticalLine;
	private int finalHorizontalLine;
	private java.util.List<WeekView.EntryGFX> gfxEntries;
	private WeekView.EntryGFX selected;
	private Interval weekInterval;
	
	public static final long HOUR_MILLIS = (long)3600000;
	public static final long WEEK_MILLIS = 7*24*HOUR_MILLIS;
	
	public void nextInterval(){
		toInterval(new Date(startTime.getTime() + WEEK_MILLIS * 3 / 2 + 1));
	}
	public void prevInterval(){
		if (startTime.getTime() < WEEK_MILLIS) return;
		toInterval(new Date(startTime.getTime() - WEEK_MILLIS / 2 - 1));
	}
	
	/**
	 * Paints the full view.
	 * @override
	 */
	public void paintComponent(Graphics g){
		abstractPaintComponent(g);
		
		
		//***************************\\
		//    setting environment    \\
		//***************************\\
		
		setBackground(new Color(250,232,155));

		width = getWidth();//width of 
		height = getHeight();
		
		firstVerticalLine = 56;
		firstHorizontalLine = 48;
		finalVerticalGap = 0;
		finalHorizontalGap = 0;
		
		xstep = (width - firstVerticalLine - finalVerticalGap) / 7;
		ystep = (height - firstHorizontalLine - finalHorizontalGap) / 24;
		
		finalVerticalLine = firstVerticalLine + xstep * 7;
		finalHorizontalLine = firstHorizontalLine + ystep * 24;
		
		Font hourFont = new Font("Arial",Font.PLAIN,13);
		Font dayFont = new Font("Arial",Font.BOLD,16);
		FontMetrics hourMetrics = g.getFontMetrics(hourFont);
		FontMetrics dayMetrics = g.getFontMetrics(dayFont);
		
		//draw grid
		int rowColor = 0;
		Color lineColor = new Color(100,100,100);
		g.setFont(dayFont);
		g.setColor(lineColor);
		
		Color[] rowColors = new Color[]{new Color(255,212,249),new Color(212,255,217)};
		Color[] rowLightColors = new Color[]{new Color(255,230,252),new Color(230,255,233)};
		//Color selectionColor = new Color(80,255,255,128);
		Color selectionColor = new Color(56,179,179,128);
		Color currentTimeColor = new Color(255,0,0);
		
		//******************************\\
		//    drawing static context    \\
		//******************************\\
		
		String[] days = new String[]{"Monday","Thuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		for(int i = 0,x = firstVerticalLine + xstep / 2;i < days.length;i++,x+=xstep){
			g.drawString(days[i],x-dayMetrics.stringWidth(days[i]) / 2,(firstHorizontalLine + dayMetrics.getHeight()) / 2);
		}
		
		g.setFont(hourFont);
		
		for (int y = firstHorizontalLine, i = 0;y <= getHeight();y+=ystep, i++){
			if (i < 24){
				g.drawString(createHour(i),firstVerticalLine / 2 - hourMetrics.stringWidth(createHour(i)) / 2,(int)(y + ystep / 2.0 + (hourMetrics.getHeight() - 3) / 2.0));
				g.setColor(rowColors[rowColor]);
				
				g.fillRect(firstVerticalLine,y,finalVerticalLine - firstVerticalLine,ystep);
				
				g.setColor(rowLightColors[rowColor]);
				for (int j = firstVerticalLine + xstep;j + xstep < width;j+=xstep*2){
					g.fillRect(j,y,xstep,ystep);
				}
				
				rowColor++;
				if (rowColor == rowColors.length) rowColor = 0;
				g.setColor(lineColor);
			}
			g.drawLine(0,y,finalVerticalLine,y);
		}
		
		for (int x = firstVerticalLine;x <= getWidth();x+=xstep){
			g.drawLine(x,0,x,finalHorizontalLine);
		}
		
		g.drawString("Hour",6,firstHorizontalLine - 3);
		g.drawString("Day",firstVerticalLine - hourMetrics.stringWidth("Day") - 3, hourMetrics.getHeight() + 3);
		
		g.drawLine(0,0,firstVerticalLine,firstHorizontalLine);
		
		//***********************\\
		//    drawing entries    \\
		//***********************\\
		
		gfxEntries = generateGFXEntries(entries);
		for(WeekView.EntryGFX e : gfxEntries){
			g.setColor(e.backgroundColor);
			g.fillRect(e.x,e.y,e.width,e.height);
			g.setColor(Color.BLACK);
			g.drawRect(e.x,e.y,e.width,e.height);
		}
		
		//draw selection
		if (selected != null && selected.entry.getInterval().intersect(weekInterval)){
			g.setColor(selectionColor);
			g.fillRect(selected.x,selected.y,selected.width,selected.height);
			g.setColor(new Color(selectionColor.getRGB()));
			g.fillRoundRect(selected.x,selected.y,selected.width,3,3,3);//above
			g.fillRoundRect(selected.x,selected.y + selected.height - 3,selected.width,3,3,3);//below
			g.fillRoundRect(selected.x,selected.y,3,selected.height,3,3);//left
			g.fillRoundRect(selected.x + selected.width - 3,selected.y,3,selected.height,3,3);//right
		}
		
		//**********************************\\
		//    drawing current time signs    \\
		//**********************************\\
		
		long currentTime = System.currentTimeMillis();
		if (weekInterval.intersect(currentTime)){
			g.setColor(currentTimeColor);
			Calendar c = Calendar.getInstance();
			c.setTime(new Date(currentTime));
			int x = firstVerticalLine + dayOfWeek(currentTime) * xstep;
			int y = firstHorizontalLine + (int)((c.get(Calendar.HOUR_OF_DAY) * HOUR_MILLIS + c.get(Calendar.MINUTE) * HOUR_MILLIS / 60) * (double)(ystep / 60.0) / 60000.0);
			
			y -= 4;
			
			//at field
			g.fillPolygon(
				new int[]{x,x,x+2,x+8,x+2},
				new int[]{y,y+8,y+8,y+4,y},
				5
				);
			x += xstep;
			g.fillPolygon(
				new int[]{x,x,x-2,x-8,x-2},
				new int[]{y,y+8,y+8,y+4,y},
				5
				);
			
			//at side
			x = 0;
			g.fillPolygon(
				new int[]{x,x,x+2,x+8,x+2},
				new int[]{y,y+8,y+8,y+4,y},
				5
				);
			x = firstVerticalLine;
			g.fillPolygon(
				new int[]{x,x,x-2,x-8,x-2},
				new int[]{y,y+8,y+8,y+4,y},
				5
				);
			
		}
	}
	
	/**
	 * Generate the entries rectangles.
	 */
	private java.util.List<EntryGFX> generateGFXEntries(java.util.List<CalendarEntry> l){
		java.util.List<EntryGFX> rects = new ArrayList<EntryGFX>();
		java.util.List<Interval> intersections = new ArrayList<Interval>();
		java.util.List<Intersection> isecs = new ArrayList<Intersection>();
		Calendar c = Calendar.getInstance();
		
		
		int i = 0;
		for (CalendarEntry e : l){
			
			//if not in currently displayed interval skip
			if (!e.getInterval().intersect(weekInterval)){
				continue;
			}
			
			c.setTime(new Date(e.getInterval().getStart()));
			
			int x = firstVerticalLine + dayOfWeek(e.getInterval().getStart()) * xstep;
			int y = firstHorizontalLine + (int)((c.get(Calendar.HOUR_OF_DAY) * HOUR_MILLIS + c.get(Calendar.MINUTE) * HOUR_MILLIS / 60) * (double)(ystep / 60.0) / 60000.0);
			int h = (int)((e.getInterval().getLength() * ystep) / (double)(HOUR_MILLIS));
			int w = xstep;
			
			EntryGFX entry = new EntryGFX(e,x,y,w,h,1,0,e.getForegroundColor(),e.getBackgroundColor());
			
			
			//detect collisions, MUST USE ZONES TO DETERMINE THE NECESSERY WIDTH
			/*int j = 0;
			for (CalendarEntry ce : l){
				if (e != ce && e.getInterval().intersect(ce.getInterval())){
					entry.sharing++;
					if (i > j) entry.placing++;
				}
				j++;
			}
			
			entry.width = (int)(xstep / (double)entry.sharing);
			entry.x += (int)((xstep / (double)entry.sharing) * entry.placing);/* */
			
			
			rects.add(entry);
			i++;
		}
		return rects;
	}
	
	protected void clickPerformed(MouseEvent e){
		if (selected != null){
			//there is a selected entry
			for (EntryGFX g : gfxEntries){
				if (g.intersect(e.getX(),e.getY())){
					if (selected.entry == g.entry){
						return;
					}
					
					selected = g;
					dispatchSelectionChanged(selected.entry);
					return;
				}
			}
			selected = null;
			dispatchSelectionChanged(null);
		}else{
			//no selected entry
			for (EntryGFX g : gfxEntries){
				if (g.intersect(e.getX(),e.getY())){
					selected = g;
					dispatchSelectionChanged(selected.entry);
					return;
				}
			}
		}
	}
	
	public void toInterval(Date start){
		if (start == null) Thrower.Throw(new NullPointerException("Error: Argument can't be null"));
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		startTime = new Timestamp(c.getTime().getTime());
		
		c.setTime(new Date(startTime.getTime() + WEEK_MILLIS + HOUR_MILLIS * 2));
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		
		weekInterval = new Interval(startTime.getTime(),c.getTime().getTime());
		
		repaint();
	}
	
	private static String createHour(int i){
		if (i < 0 || i >= 100) return "??:??";
		if (i < 10) return ("0" + i + ":00");
		return (i + ":00");
	}
}
