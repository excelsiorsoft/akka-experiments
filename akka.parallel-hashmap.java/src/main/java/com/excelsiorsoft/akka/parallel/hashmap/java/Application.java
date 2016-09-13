/**
 * 
 */
package com.excelsiorsoft.akka.parallel.hashmap.java;

import static akka.actor.ActorRef.noSender;
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
		
		container.tell(msg("put", "key-A", "A"),  noSender());
		container.tell(msg("put", "key-B", "B"),  noSender());
		container.tell(msg("put", "key-C", "C"),  noSender());
		
		container.tell(msg("remove", "keyB"), noSender());
		
		container.tell(msg("get", "key-A"), listener);
		container.tell(msg("get", "key-B"), listener);
		container.tell(msg("get", "key-C"), listener);
		
		System.in.read();
		system.shutdown();
	}

	public static Object[] msg(Object... elements) {
		
		return elements;
	}

	

}
