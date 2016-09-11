/**
 * 
 */
package com.excelsiorsoft.akka.uppercaser.java;

import akka.actor.UntypedActor;

/**
 * @author Simeon
 *
 */
public class Listener extends UntypedActor{

	@Override
	public void onReceive(Object msg) throws Throwable {
		
		System.out.println("result:"+msg);
		
	}

}
