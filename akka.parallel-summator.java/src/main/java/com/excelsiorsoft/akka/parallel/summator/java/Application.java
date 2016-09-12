/**
 * 
 */
package com.excelsiorsoft.akka.parallel.summator.java;

import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Simeon
 *
 */
public class Application {
	
	public static void main(String[] args) {

		ActorSystem system = ActorSystem.create("demo");
		ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");
		ActorRef summator = system.actorOf(Props.create(Summator.class, listener), "summator");
		
		summator.tell(new int[] {0,  10}, ActorRef.noSender());
		
		
		
	}

}
