/**
 * 
 */
package com.excelsiorsoft.akka.parallel.hashma.java;

import java.util.Arrays;

import akka.actor.UntypedActor;

/**
 * @author Simeon
 *
 */
public class Listener extends UntypedActor{

	@Override
	public void onReceive(Object msg) throws Throwable {
		
		/*if(msg instanceof Object[]) {
			System.out.println("result:"+Arrays.toString((Object[])msg));
		}else {
			System.out.println("result:"+msg);
		}*/
		
		//System.out.println("s - result:"+msg);
		Utils.printMsg(msg);
		
	}

}
