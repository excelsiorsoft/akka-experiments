package com.excelsiorsoft.akka.uppercaser.routed.java;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class WorkerUpcaser extends UntypedActor{

	@Override
	public void onReceive(Object msg) throws Throwable {

		if(msg instanceof String) {
			String response = ((String)msg).toUpperCase();
			ActorRef sender = getSender();
			sender.tell(response, getSelf());
			System.out.println(">> "+getSelf());
			while(true); //"прибиваем" актёра
		
		}else {
			unhandled(msg);
		}
		
	}

	
	
}
