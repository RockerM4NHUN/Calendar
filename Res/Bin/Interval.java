package Res.Bin;

import java.sql.Timestamp;

/**
 * Holds data for a positively directed interval.
 * 
 * @author ZODVAAT.SZE
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
	 * @author ZODVAAT.SZE
	 * @return True if the two intervals have more than one common point.
	 */
	public boolean intersect(Interval i){
		return (contains(i.start) || contains(i.end - 1)) || (i.contains(start) || i.contains(end - 1));
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return Returns new Interval or null if no intersection.
	 */
	public Interval intersection(Interval i){
		if (!intersect(i)) return null;
		return new Interval(((start > i.start) ? start : i.start),((end > i.end) ? i.end : end));
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return True if the point is in the interval. (start <= point < end)
	 */
	public boolean contains(long i){
		return (start <= i && i < end);
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return True if the parameter is in the interval. (start <= i.start,i.end < end)
	 */
	public boolean contains(Interval i){
		return (contains(i.start) && contains(i.end));
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return Union of the intervals or null if not possible
	 */
	public Interval union(Interval i){
		if (contains(i)) return clone();
		if (i.contains(this)) return i.clone();
		
		if (intersect(i)){
			if (start <= i.start){
				return new Interval(start,(end < i.end ? i.end : end));
			}
			return new Interval(i.start,end);
		}
		if (joinable(i)){
			if (start < i.start) return new Interval(start,i.end);
			return new Interval(i.start,end);
		}
		return null;
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return True if the point is in the interval. (start <= point < end)
	 */
	public Interval clone(){
		return new Interval(start,end);
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return True if there is no interval between the two intervals.
	 */
	public boolean joinable(Interval i){
		return (start == i.end || end == i.start);
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return Start of interval
	 */
	public long getStart(){
		return start;
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return End of interval
	 */
	public long getEnd(){
		return end;
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return Length of interval
	 */
	public long getLength(){
		return end-start;
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return Timestamp created from start
	 */
	public Timestamp getStartTimestamp(){
		return new Timestamp(start);
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return Timestamp created from end
	 */
	public Timestamp getEndTimestamp(){
		return new Timestamp(end);
	}
	/**
	 * @author ZODVAAT.SZE
	 * @return String that represents the interval
	 */
	public String toString(){
		return "[ " + start + " - " + end + " )";
	}
}
