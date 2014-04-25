package Res.Bin;

import java.sql.Timestamp;

/**
 * @author Zombori Dániel
 */
public class Interval{
	private long start;
	private long end;
	
	public Interval(){
		start = 0;
		end = 0;
	}
	
	public Interval(long s, long e){
		start = s;
		end = e;
	}
	
	/**
	 * #UNTESTED
	 * 
	 * @author Zombori Dániel
	 * @return True if the two intervals have more than one common point.
	 */
	public boolean intersect(Interval i){
		return intersect(i.start) || intersect(i.end);
	}
	
	/**
	 * #UNTESTED
	 * 
	 * @author Zombori Dániel
	 * @return True if the point is in the interval. (start <= point < end)
	 */
	public boolean intersect(long i){
		return (start <= i && i < end);
	}
	
	/**
	 * #UNTESTED
	 * 
	 * @author Zombori Dániel
	 * @return True if there is no interval between the two intervals.
	 */
	public boolean joinable(Interval i){
		return start == i.end || end == i.start;
	}
	
	/**
	 * @author Zombori Dániel
	 * @return Start of interval
	 */
	public long getStart(){
		return start;
	}
	
	/**
	 * @author Zombori Dániel
	 * @return End of interval
	 */
	public long getEnd(){
		return end;
	}
	
	/**
	 * @author Zombori Dániel
	 * @return Length of interval
	 */
	public long getLength(){
		return end-start;
	}
	
	/**
	 * @author Zombori Dániel
	 * @return Timestamp created from start
	 */
	public Timestamp getStartTimestamp(){
		return new Timestamp(start);
	}
	
	/**
	 * @author Zombori Dániel
	 * @return Timestamp created from end
	 */
	public Timestamp getEndTimestamp(){
		return new Timestamp(end);
	}
}
