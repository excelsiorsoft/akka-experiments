package com.excelsiorsoft.akka.uppercaser.java;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class WorkerUpcaser extends UntypedActor{

	@Override
	public void onReceive(Object msg) throws Throwable {

		if(msg instanceof String) {
			String response = ((String)msg).toUpperCase();
			ActorRef sender = getSender();
			sender.tell(response, getSelf());
			
			sender.tell("X", getSelf());
			sender.tell("Y", getSelf());
		
		}else {
			unhandled(msg);
		}
		
	}

	
	
}
