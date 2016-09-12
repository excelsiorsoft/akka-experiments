package com.excelsiorsoft.akka.uppercaser.routed.java;

import java.util.Scanner;



import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.SmallestMailboxPool;

public class Application {
	
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("demo");
		//ActorRef worker = system.actorOf(Props.create(WorkerUpcaser.class),"worker");
		
		//N.B.: each individual thread hangs itself, so the whole system will hang after the 5th request is issued - be careful!!!!
		ActorRef router = system.actorOf(new SmallestMailboxPool(5).props(Props.create(WorkerUpcaser.class)),"workers");
		ActorRef callback = system.actorOf(Props.create(Listener.class),"listener");
		
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			String line = sc.nextLine();
			if("exit".equals(line)) {
				system.shutdown();
				return;
			}
			
			router.tell(line, callback);
			//worker.tell(line, callback);
			//worker.tell(line, ActorRef.noSender()); //"null object" pattern
		}
		
	}

}
