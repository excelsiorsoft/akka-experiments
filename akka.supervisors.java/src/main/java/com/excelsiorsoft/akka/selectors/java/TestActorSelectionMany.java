/**
 * 
 */
package com.excelsiorsoft.akka.selectors.java;

import javax.security.auth.callback.Callback;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Simeon
 *
 */
public class TestActorSelectionMany {

	public static void main(String[]args) {
	ActorSystem system = ActorSystem.create();
	system.actorOf(Props.create(Listener.class), "Listener-0");
	system.actorOf(Props.create(Listener.class), "Listener-1");
	system.actorOf(Props.create(Listener.class), "Listener-2");
	
	ActorSelection selection = system.actorSelection("user/*");
	selection.tell("Hello! ", ActorRef.noSender());
	
	system.shutdown();
	}
}
