package Res.Bin;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * An evented list that can dispatch event if item added or removed.
 * 
 * @author ZODVAAT.SZE
 */
public class EventedList<ListItem> implements Iterable<ListItem>{
	private List<ListItem> list;
	private List<EventedListListener<ListItem>> addListeners;
	private List<EventedListListener<ListItem>> removeListeners;
	
	public EventedList(){
		list = new LinkedList<ListItem>();
		addListeners = new LinkedList<EventedListListener<ListItem>>();
		removeListeners = new LinkedList<EventedListListener<ListItem>>();
	}
	
	/**
	 * Add's item to list.
	 * 
	 * @author ZODVAAT.SZE
	 * @return Boolean as specified in List.
	 */
	public boolean add(ListItem item){
		boolean ret = list.add(item);
		dispatchItemAddedEvent(item);
		return ret;
	}
	
	/**
	 * Remove's item from list.
	 * 
	 * @author ZODVAAT.SZE
	 * @return Boolean as specified in List.
	 */
	public boolean remove(ListItem item){
		boolean ret = list.remove(item);
		dispatchItemRemovedEvent(item);
		return ret;
	}
	
	/**
	 * Remove's item from list.
	 * 
	 * @author ZODVAAT.SZE
	 * @return Removed list item.
	 */
	public ListItem remove(int index){
		ListItem ret = list.remove(index);
		dispatchItemRemovedEvent(ret);
		return ret;
	}
	
	/**
	 * Add's listener for list item added.
	 * 
	 * @author ZODVAAT.SZE
	 */
	public void addItemAddedListener(EventedListListener<ListItem> l){
		addListeners.add(l);
	}
	
	/**
	 * Add's listener for list item removed.
	 * 
	 * @author ZODVAAT.SZE
	 */
	public void addItemRemovedListener(EventedListListener<ListItem> l){
		removeListeners.add(l);
	}
	
	/**
	 * Removes's item added listener.
	 * 
	 * @author ZODVAAT.SZE
	 * @return Boolean as specified in List.
	 */
	public boolean removeItemAddedListener(EventedListListener<ListItem> l){
		return addListeners.remove(l);
	}
	
	/**
	 * Removes's item removed listener.
	 * 
	 * @author ZODVAAT.SZE
	 * @return Boolean as specified in List.
	 */
	public boolean removeItemRemovedListener(EventedListListener<ListItem> l){
		return removeListeners.remove(l);
	}
	
	/**
	 * Return's iterator for list.
	 * 
	 * @author ZODVAAT.SZE
	 * @return Iterator of list.
	 */
	public Iterator<ListItem> iterator(){
		return new Iterator<ListItem>(){
			private Iterator<ListItem> it = list.iterator();
			private ListItem current = null;
			
			public boolean hasNext(){
				return it.hasNext();
			}
			
			public ListItem next(){
				current = it.next();
				return current;
			}
			
			public void remove(){
				it.remove();
				dispatchItemRemovedEvent(current);
			}
		};
	}
	
	/**
	 * Return's lists size.
	 * 
	 * @author ZODVAAT.SZE
	 * @return Size of list.
	 */
	public int size(){
		return list.size();
	}
	
	private void dispatchItemAddedEvent(ListItem item){
		for(EventedListListener<ListItem> l : addListeners){
			l.listModified(item);
		}
	}
	
	private void dispatchItemRemovedEvent(ListItem item){
		for(EventedListListener<ListItem> l : removeListeners){
			l.listModified(item);
		}
	}
}
