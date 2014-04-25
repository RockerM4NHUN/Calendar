package Res.Bin;

import java.util.*;

/**
 * This class is throws easily custom parametered exceptions with stack trace.
 * @author Zombori Dániel
 */
public class Thrower{
	public static void Throw(RuntimeException e){
		e.setStackTrace(getStackTrace());
		throw e;
	}
	
	private static StackTraceElement[] getStackTrace(){
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return new ArrayList<StackTraceElement>(Arrays.asList(stack)).subList(3,stack.length).toArray(new StackTraceElement[0]);
	}
}
