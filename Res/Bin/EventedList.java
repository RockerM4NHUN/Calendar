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
	public EventedList(List<ListItem> l){
		list = l;
		addListeners = new LinkedList<EventedListListener<ListItem>>();
		removeListeners = new LinkedList<EventedListListener<ListItem>>();
		resetListeners = new LinkedList<EventedListListener<ListItem>>();
	}
	
	private List<ListItem> list;
	private List<EventedListListener<ListItem>> addListeners;
	private List<EventedListListener<ListItem>> removeListeners;
	private List<EventedListListener<ListItem>> resetListeners;
	
	
	//**********************\\
	//    List modifiers    \\
	//**********************\\
	
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
	 * Remove's all item's of list.
	 * 
	 * @author ZODVAAT.SZE
	 */
	public void resetList(List<ListItem> l){
		if (l == null) Thrower.Throw(new NullPointerException("Argument can't be null"));
		List<ListItem> old = list;
		list = l;
		dispatchListResetEvent(old,list);
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
	
	
	//**************\\
	//    Adders    \\
	//**************\\
	
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
	 * Add's listener for list reseted.
	 * 
	 * @author ZODVAAT.SZE
	 */
	public void addListResetListener(EventedListListener<ListItem> l){
		resetListeners.add(l);
	}
	
	
	//****************\\
	//    Removers    \\
	//****************\\
	
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
	 * Removes's list reseted listener.
	 * 
	 * @author ZODVAAT.SZE
	 * @return Boolean as specified in List.
	 */
	public boolean removeListResetListener(EventedListListener<ListItem> l){
		return resetListeners.remove(l);
	}
	
	
	//*******************\\
	//    Dispatchers    \\
	//*******************\\
	
	private void dispatchItemAddedEvent(ListItem item){
		for(EventedListListener<ListItem> l : addListeners){
			l.itemModified(item);
		}
	}
	
	private void dispatchItemRemovedEvent(ListItem item){
		for(EventedListListener<ListItem> l : removeListeners){
			l.itemModified(item);
		}
	}

	private void dispatchListResetEvent(List<ListItem> oldVersion, List<ListItem> newVersion){
		for(EventedListListener<ListItem> l : resetListeners){
			l.listModified(oldVersion,newVersion);
		}
	}
	
}
