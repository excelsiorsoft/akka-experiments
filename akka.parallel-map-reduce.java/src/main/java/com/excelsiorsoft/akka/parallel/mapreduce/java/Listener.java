/**
 * 
 */
package com.excelsiorsoft.akka.parallel.mapreduce.java;

import static com.excelsiorsoft.akka.parallel.mapreduce.java.Utils.printMsg;
import akka.actor.UntypedActor;

/**
 * @author Simeon
 *
 */
public class Listener extends UntypedActor{

	@Override
	public void onReceive(Object msg) throws Throwable {

		printMsg(msg);
		
	}

}
