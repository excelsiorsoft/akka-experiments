/**
 * 
 */
package com.excelsiorsoft.akka.parallel.hashma.java;

import static akka.actor.ActorRef.noSender;

import java.io.IOException;
import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Simeon
 *
 */
public class Application {
	
	public static void main(String[] args) throws Throwable {
		
		ActorSystem system = ActorSystem.create("demo");
		ActorRef container = system.actorOf(Props.create(Container.class), "container");
		ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");
		
		container.tell(msg("put", "keyA", "valueA"),  noSender());
		container.tell(msg("put", "keyB", "valueB"),  noSender());
		container.tell(msg("put", "keyC", "valueC"),  noSender());
		
		container.tell(msg("remove", "keyB"), noSender());
		
		container.tell(msg("get", "keyA", "valueA"), listener);
		container.tell(msg("get", "keyA", "valueA"), listener);
		container.tell(msg("get", "keyA", "valueA"), listener);
		
		System.in.read();
		system.shutdown();
	}

	public static Object[] msg(Object... elements) {
		
		return elements;
	}

	

}
