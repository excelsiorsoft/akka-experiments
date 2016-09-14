/**
 * 
 */
package com.excelsiorsoft.akka.selectors.java;

import static akka.actor.ActorRef.noSender;
import static akka.actor.Props.create;

import javax.security.auth.callback.Callback;

import com.excelsiorsoft.akka.monitors.java.Monitor;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;

/**
 * @author Simeon
 *
 */
public class TestActorSelectionAlive {

	public static void main(String[]args) {
	ActorSystem system = ActorSystem.create("demo");
	ActorRef monitor = system.actorOf(create(Monitor.class), "monitor");
	
	ActorRef list0 = system.actorOf(Props.create(Listener.class), "Listener-0");
	System.out.println("Created "+list0);
	ActorRef list1 = system.actorOf(Props.create(Listener.class), "Listener-1");
	System.out.println("Created "+list1);
	ActorRef list2 = system.actorOf(Props.create(Listener.class), "Listener-2");
	System.out.println("Created "+list2);
	
	/*monitor.tell(list0, noSender());
	monitor.tell(list1, noSender());
	monitor.tell(list2, noSender());*/
	
	ActorSelection selection = system.actorSelection("user/Listener-*");
	list0.tell(PoisonPill.getInstance(), ActorRef.noSender());
	selection.tell("Hello! ", ActorRef.noSender());
	
	system.shutdown();
	}
}
