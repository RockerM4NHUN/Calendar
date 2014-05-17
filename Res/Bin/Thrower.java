package Res.Bin;

import java.util.*;

/**
 * This class is throws easily custom parametered exceptions with stack trace pointing to the place where Throw called.
 * 
 * @author ZODVAAT.SZE
 */
public class Thrower{
	/**
	 * Throws the exception.
	 * 
	 * @param e Custom RuntimeException parametered by the user.
	 * @author ZODVAAT.SZE
	 */
	public static void Throw(RuntimeException e){
		e.setStackTrace(getStackTrace());
		throw e;
	}
	
	private static StackTraceElement[] getStackTrace(){
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return new ArrayList<StackTraceElement>(Arrays.asList(stack)).subList(3,stack.length).toArray(new StackTraceElement[0]);
	}
}
