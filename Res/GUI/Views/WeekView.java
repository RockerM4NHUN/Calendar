package Res.GUI.Views;

import java.sql.Timestamp;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import Res.Bin.*;

public class WeekView extends CalendarView{
	public WeekView(){
		super();
		
		//*************************\\
		//    setting variables    \\
		//*************************\\
		
		
		finalVerticalGap = 0;
		finalHorizontalGap = 0;
		
		setBackground(new Color(250,232,155));

		entryFont = new Font("Arial",Font.BOLD,13);
		hourFont = new Font("Arial",Font.PLAIN,13);
		dayFont = new Font("Arial",Font.BOLD,16);
		
		rowColors = new Color[]{new Color(255,212,249),new Color(212,255,217)};
		rowLightColors = new Color[]{new Color(255,230,252),new Color(230,255,233)};
		selectionColor = new Color(56,179,179,128);
		currentTimeColor = new Color(255,0,0);
		lineColor = new Color(100,100,100);
		
		groups = new ArrayList<IntersectionGroup>();
		gfxEntries = new ArrayList<EntryGFX>();
		selected = new ArrayList<EntryGFX>();
		
		//***************************\\
		//    setting environment    \\
		//***************************\\
		
		setPreferredSize(new Dimension(getViewWidth(),getViewHeight()));
		
		//repaint when selection changed
		addSelectionChangedListener(new CalendarSelectionChangedListener(){
			public void selectionChanged(CalendarEntry e){
				repaint();
			}
		});
		
		//repaint in every minute, to validate time signes position
		timer = new javax.swing.Timer((int)(HOUR_MILLIS / 60), new ActionListener(){
			public void actionPerformed(ActionEvent event){
				repaint();
			}
		});
		timer.start();
	}
	
		//*************************\\
		//    IntersectionGroup    \\
		//*************************\\ 
	/**
	 * Inner class for storing entries drawing information
	 * 
	 * @author Zombori Dániel
	 */
	class IntersectionGroup{
		public IntersectionGroup(CalendarEntry e){
			entries = new TreeSet<CalendarEntry>();
			rects = new ArrayList<EntryGFX>();
			entries.add(e);
			
			ival = e.getInterval();
			
			layers = new ArrayList<Set<CalendarEntry>>();
			layers.add(new TreeSet<CalendarEntry>());
			layers.get(0).add(e);
			
			changePerformed = true;
		}
		
		private Interval ival;//interval of group
		private Set<CalendarEntry> entries;//unique entry list
		private java.util.List<Set<CalendarEntry>> layers;//layers with well placed entries
		private java.util.List<EntryGFX> rects;//generated graphic elements
		private boolean changePerformed;//refresh needed in graphics
		
		public Interval getInterval(){
			return ival;
		}
		
		public Set<CalendarEntry> getEntries(){
			return entries;
		}
		
		/**
		 * Merges two groups.
		 * 
		 * @author Zombori Dániel
		 * @return True if changes performed correctly
		 */
		public boolean merge(IntersectionGroup g){
			Interval tmp = ival.union(g.ival);
			if (tmp == null) return false;
			
			boolean change = entries.addAll(g.entries);
			if (change){
				ival = ival.union(g.ival);
				for (CalendarEntry e : g.entries){
					addToLayers(e);
				}
			}
			return change;
		}
		
		/**
		 * Adds entry to group.
		 * 
		 * @author Zombori Dániel
		 * @return True if changes performed correctly
		 */
		public boolean addEntry(CalendarEntry e){
			Interval tmp = ival.union(e.getInterval());
			if (tmp == null) return false;
			
			boolean change = entries.add(e);
			if (change){
				ival = tmp;
				addToLayers(e);
			}
			return change;
		}
		
		/**
		 * Removes entry from group.
		 * 
		 * @author Zombori Dániel
		 * @return Intersection group(s) without specific CalendarEntry, the size will be 0 if 
		 */
		public java.util.List<IntersectionGroup> removeEntry(CalendarEntry e){
			java.util.List<IntersectionGroup> ret = new ArrayList<IntersectionGroup>();
			boolean change = entries.remove(e);
			if (change){
				removeFromLayers(e);
				Iterator<CalendarEntry> it = entries.iterator();
				for (CalendarEntry entr : entries){
					addToGroups(ret,new IntersectionGroup(entr));
				}
			}
			if (ret.size() == 0) return null;
			return ret;
		}
		
		/**
		 * Returns recently updated graphics.
		 * 
		 * @author Zombori Dániel
		 * @return Array of drawable rectangles
		 */
		public EntryGFX[] getRectangles(){
			if (changePerformed) gfx();
			return rects.toArray(new EntryGFX[0]);
		}
		
		/**
		 * Adds entry to layers.
		 * 
		 * @author Zombori Dániel
		 * @return True if changes performed correctly
		 */
		private boolean addToLayers(CalendarEntry e){
			
			sets:
			for (Set<CalendarEntry> set : layers){
				for (CalendarEntry entry : set){
					if (e.getInterval().intersect(entry.getInterval())) continue sets;
				}
				set.add(e);
				changePerformed = true;
				return true;
			}
			Set<CalendarEntry> ret = new TreeSet<CalendarEntry>();
			ret.add(e);
			layers.add(ret);
			changePerformed = true;
			return true;
		}
		
		/**
		 * Removes entry from layers.
		 * 
		 * @author Zombori Dániel
		 * @return True if changes performed correctly
		 */
		private boolean removeFromLayers(CalendarEntry e){
			
			sets:
			for (Set<CalendarEntry> set : layers){
				if (set.remove(e)){
					if (set.size() == 0) layers.remove(set);
					changePerformed = true;
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Calculates graphics.
		 * 
		 * @author Zombori Dániel
		 */
		private void gfx(){
			changePerformed = false;
			rects.clear();
			
			if (layers.size() == 0) return;
			int w = xstep / layers.size();
			int wcorr = xstep % layers.size();
			
			Calendar c = Calendar.getInstance();
			
			int layer = 0;
			for (Set<CalendarEntry> set : layers){
				
				for (CalendarEntry e : set){
					Interval o = e.getInterval();
					Interval i = o.clone();
					
					c.setTime(i.getStartTimestamp());
					int start = c.get(Calendar.DAY_OF_MONTH);
					c.setTime(i.getEndTimestamp());
					int end = c.get(Calendar.DAY_OF_MONTH);
					
					//first fragment of shattered
					if (start != end){
						c.setTime(i.getStartTimestamp());
						int x = firstVerticalLine + dayOfWeek(i.getStart()) * xstep + layer * w;
						int y = firstHorizontalLine + (int)((c.get(Calendar.HOUR_OF_DAY) * HOUR_MILLIS + c.get(Calendar.MINUTE) * HOUR_MILLIS / 60) * (double)(ystep / 60.0) / 60000.0);
						int h = firstHorizontalLine + ystep * 24 - y;
						
						
						c.add(Calendar.HOUR, 24);
						c.set(Calendar.HOUR, 0);
						c.set(Calendar.MINUTE, 0);
						c.set(Calendar.SECOND, 0);
						c.set(Calendar.MILLISECOND, 0);
						c.set(Calendar.AM_PM, Calendar.AM);
						
						rects.add(new EntryGFX(e, new Interval(i.getStart(), c.getTimeInMillis()),x + ((layer < wcorr) ? layer : wcorr),y,w + ((layer < wcorr) ? 1 : 0),h));
						
						i = new Interval(c.getTimeInMillis(),i.getEnd());
						start = c.get(Calendar.DAY_OF_MONTH);
						
						c.setTime(new Date(i.getEnd() + HOUR_MILLIS * 24));
						end = c.get(Calendar.DAY_OF_MONTH);
					}
					
					//full days
					while(start + 1 < end){
						
						c.setTime(i.getStartTimestamp());
						int x = firstVerticalLine + dayOfWeek(i.getStart()) * xstep + layer * w;
						int y = firstHorizontalLine + (int)((c.get(Calendar.HOUR_OF_DAY) * HOUR_MILLIS + c.get(Calendar.MINUTE) * HOUR_MILLIS / 60) * (double)(ystep / 60.0) / 60000.0);
						int h = ystep * 24;
						
						c.add(Calendar.HOUR, 24);
						c.set(Calendar.HOUR, 0);
						c.set(Calendar.MINUTE, 0);
						c.set(Calendar.SECOND, 0);
						c.set(Calendar.MILLISECOND, 0);
						c.set(Calendar.AM_PM, Calendar.AM);
						
						rects.add(new EntryGFX(e, new Interval(i.getStart(), c.getTimeInMillis()),x + ((layer < wcorr) ? layer : wcorr),y,w + ((layer < wcorr) ? 1 : 0),h));
						
						i = new Interval(c.getTimeInMillis(),i.getEnd());
						start = c.get(Calendar.DAY_OF_MONTH);
						
						c.setTime(new Date(i.getEnd() + HOUR_MILLIS * 24));
						end = c.get(Calendar.DAY_OF_MONTH);
						
					}
					
					//last fragment or full if not shattered
					if (i.getLength() != 0){
						c.setTime(i.getStartTimestamp());
						int x = firstVerticalLine + dayOfWeek(i.getStart()) * xstep + layer * w;
						int y = firstHorizontalLine + (int)((c.get(Calendar.HOUR_OF_DAY) * HOUR_MILLIS + c.get(Calendar.MINUTE) * HOUR_MILLIS / 60) * (double)(ystep / 60.0) / 60000.0);
						int h = (int)((i.getLength() * ystep) / (double)(HOUR_MILLIS));
						
						rects.add(new EntryGFX(e,i,x + ((layer < wcorr) ? layer : wcorr),y,w + ((layer < wcorr) ? 1 : 0),h));
					}
				}
				
				layer++;
			}
		}
	}


	class EntryGFX{
		public EntryGFX(CalendarEntry entry, Interval time, int x, int y, int width, int height){
			this.entry = entry;
			this.time = time;
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
		}
		CalendarEntry entry;
		Interval time;
		int x,y,height,width;
		
		public boolean intersect(int x, int y){
			return (this.x <= x && x <= this.x + width && this.y <= y && y <= this.y + height);
		}
	}
	
	public static int getViewWidth(){
		return firstVerticalLine + 7 * 130;
	}
	
	public static int getViewHeight(){
		return firstHorizontalLine + 24 * 30;
	}
	
	
	private int width;
	private int height;
	private static int firstVerticalLine = 56;
	private static int firstHorizontalLine = 48;
	private int finalVerticalGap;
	private int finalHorizontalGap;
	private int xstep;
	private int ystep;
	private int finalVerticalLine;
	private int finalHorizontalLine;
	private java.util.List<EntryGFX> gfxEntries;
	private java.util.List<EntryGFX> selected;
	private Interval weekInterval;
	private java.util.List<IntersectionGroup> groups;
	
	private Font hourFont;
	private Font dayFont;
	private Font entryFont;
	private FontMetrics entryMetrics;
	private FontMetrics hourMetrics;
	private FontMetrics dayMetrics;
		
	private Color[] rowColors;
	private Color[] rowLightColors;
	private Color selectionColor;
	private Color currentTimeColor;
	private Color lineColor;
	
	private javax.swing.Timer timer;
		
	public static final String[] days = new String[]{"Monday","Thuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	public static final long HOUR_MILLIS = (long)3600000;
	public static final long WEEK_MILLIS = 7*24*HOUR_MILLIS;
	
	
	
	
	
	public void nextInterval(){
		toInterval(new Date(startTime.getTime() + WEEK_MILLIS + 1));
	}
	public void prevInterval(){
		if (startTime.getTime() < WEEK_MILLIS) return;
		toInterval(new Date(startTime.getTime() - WEEK_MILLIS + 1));
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
		
		width = getWidth();//width of panel
		height = getHeight();//height of panel
		
		xstep = (width - firstVerticalLine - finalVerticalGap) / 7;
		ystep = (height - firstHorizontalLine - finalHorizontalGap) / 24;
		
		entryMetrics = g.getFontMetrics(entryFont);
		hourMetrics = g.getFontMetrics(hourFont);
		dayMetrics = g.getFontMetrics(dayFont);
		
		finalVerticalLine = firstVerticalLine + xstep * 7;
		finalHorizontalLine = firstHorizontalLine + ystep * 24;
		
		//draw grid
		int rowColor = 0;//index of color in rowColors and rowLightColors
		g.setFont(dayFont);
		g.setColor(lineColor);
		
		//******************************\\
		//    drawing static context    \\
		//******************************\\
		
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
		
		gfxEntries.clear();
		g.setFont(entryFont);
		for (IntersectionGroup group : groups){
			EntryGFX[] rects = group.getRectangles();
			for (int i = 0;i < rects.length;i++){
				if (!rects[i].time.intersect(weekInterval)) continue;
				
				gfxEntries.add(rects[i]);
				g.setColor(rects[i].entry.getBackgroundColor());
				g.fillRect(rects[i].x,rects[i].y,rects[i].width,rects[i].height);
				g.setColor(Color.BLACK);
				g.drawRect(rects[i].x,rects[i].y,rects[i].width,rects[i].height);
				
				int height = entryMetrics.getHeight();
				
				String[] str = breakToLines(rects[i].entry.getTitle(),entryMetrics,rects[i].width - 6, rects[i].height / (height + 6));
				g.setColor(rects[i].entry.getForegroundColor());
				for (int j = 0;j < str.length;j++){
					g.drawString(str[j],rects[i].x + 3, rects[i].y + height / 2 + (j == 0 ? 6 : 0) + j * (height + 6));
				}
			}
		}
		
		
		//************************************\\
		//    drawing selection rectangles    \\
		//************************************\\
		
		if (selected.size() != 0 && selected.get(0).entry.getInterval().intersect(weekInterval)){
			int i = -1,size = selected.size();
			for (EntryGFX entry : selected){
				i++;
				if (!weekInterval.intersect(entry.time)) continue;
				
				g.setColor(selectionColor);
				g.fillRect(entry.x,entry.y,entry.width,entry.height);
				g.setColor(new Color(selectionColor.getRGB()));
				if (i == 0) g.fillRoundRect(entry.x,entry.y,entry.width,3,3,3);//above
				if (i == size - 1) g.fillRoundRect(entry.x,entry.y + entry.height - 3 + 1,entry.width,3,3,3);//below
				g.fillRoundRect(entry.x,entry.y,3,entry.height,3,3);//left
				g.fillRoundRect(entry.x + entry.width - 3 + 1,entry.y,3,entry.height,3,3);//right
			}
		}
		
		//**********************************\\
		//    drawing current time signs    \\
		//**********************************\\
		
		long currentTime = System.currentTimeMillis();
		if (weekInterval.contains(currentTime)){
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
	 * Adds group to group list.
	 */
	private boolean addToGroups(java.util.List<IntersectionGroup> glist, IntersectionGroup g){
		
		boolean changePerformed = true;
		while(changePerformed){
			changePerformed = false;
			
			for(Iterator<IntersectionGroup> it = glist.iterator();it.hasNext();){
				IntersectionGroup igrp = it.next();
				if (igrp == g) continue;
				
				if (g.getInterval().intersect(igrp.getInterval())){
					g.merge(igrp);
					it.remove();
					changePerformed = true;
				}
			}
		}
		glist.add(g);
		return true;
	}
	
	protected void clickPerformed(MouseEvent e){
		if (selected.size() != 0){
			//there is a selected entry
			for (EntryGFX g : gfxEntries){
				if (g.intersect(e.getX(),e.getY())){
					for (EntryGFX entry : selected){
						if (entry.entry == g.entry){
							return;
						}
					}
					
					selected.clear();
					for (IntersectionGroup group : groups){
						EntryGFX[] entries = group.getRectangles();
						for (int i = 0;i < entries.length;i++){
							if (entries[i].entry == g.entry) selected.add(entries[i]);
						}
					}
					dispatchSelectionChanged(g.entry);
					return;
				}
			}
			selected.clear();
			dispatchSelectionChanged(null);
		}else{
			//no selected entry
			for (EntryGFX g : gfxEntries){
				if (g.intersect(e.getX(),e.getY())){
					selected.clear();
					for (IntersectionGroup group : groups){
						EntryGFX[] entries = group.getRectangles();
						for (int i = 0;i < entries.length;i++){
							if (entries[i].entry == g.entry) selected.add(entries[i]);
						}
					}
					dispatchSelectionChanged(g.entry);
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
	
	public boolean setCalendarEntries(java.util.List<CalendarEntry> elist){
		if (elist == null) Thrower.Throw(new NullPointerException("Error: Argument can't be null"));
		
		groups.clear();
		
		for(CalendarEntry e : elist){
			addToGroups(groups, new IntersectionGroup(e));
		}
		return true;
	}
	
	public java.util.List<CalendarEntry> getCalendarEntries(){
		java.util.List<CalendarEntry> ret = new ArrayList<CalendarEntry>();
		for (IntersectionGroup g : groups){
			ret.addAll(g.getEntries());
		}
		return ret;
	}
	
	public boolean addCalendarEntry(CalendarEntry e){
		return addToGroups(groups, new IntersectionGroup(e));
	}
	
	public boolean removeCalendarEntry(CalendarEntry e){
		for (IntersectionGroup g : groups){
			java.util.List<IntersectionGroup> grps = g.removeEntry(e);
			if (grps != null){
				groups.remove(g);
				groups.addAll(grps);
				if (selected.size() != 0 && e == selected.get(0).entry){
					selected.clear();
					dispatchSelectionChanged(null);
				}
				return false;
			}
		}
		return true;
	}
	
	private static String[] breakToLines(String str, FontMetrics fm, int maxWidth, int maxLines){
		java.util.List<String> ret = new ArrayList<String>();
		
		int start = 0, end, line = 0;
		while(start != str.length() && line < maxLines){
			end = str.length();
			while(fm.stringWidth(str.substring(start,end)) > maxWidth){
				end--;
			}
			
			ret.add(str.substring(start,end));
			line++;
			start = end;
		}
		
		if (ret.size() == 0) return new String[0];
		
		int size = ret.get(ret.size() - 1).length();
		if (start != str.length() && size >= 3) ret.set(ret.size() - 1,ret.get(ret.size() - 1).substring(0,size - 3) + "...");
		
		return ret.toArray(new String[0]);
	}
	
	private static String createHour(int i){
		if (i < 0 || i >= 100) return "??:??";
		if (i < 10) return ("0" + i + ":00");
		return (i + ":00");
	}
}
