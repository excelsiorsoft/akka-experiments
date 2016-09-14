/**
 * 
 */
package com.excelsiorsoft.akka.selectors.java;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.AllForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.japi.Function;

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
			
			System.out.println(">>Monitor:TERMINATED: "+((Terminated)msg).getActor());
		}else {
			unhandled(msg);
		}
		
	}
	

}
