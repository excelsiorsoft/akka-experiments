/**
 * 
 */
package com.excelsiorsoft.akka.monitors.java;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

/**
 * @author Simeon
 *
 */
public class Monitor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Throwable {
		
		if(msg instanceof ActorRef) {
			
			ActorRef actor = (ActorRef)msg;
			getContext().watch(actor);
		}else if (msg instanceof Terminated) {
			
			System.out.println("TERMINATED: "+((Terminated)msg).getActor());
		}else {
			unhandled(msg);
		}
		
	}

}
