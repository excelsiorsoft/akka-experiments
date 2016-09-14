/**
 * 
 */
package com.excelsiorsoft.akka.parallel.mapreduce.java;

import static akka.actor.ActorRef.noSender;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.*;
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
		
		container.tell(msg(PUT, "key-A", "A"),  noSender());
		container.tell(msg(PUT, "key-B", "B"),  noSender());
		container.tell(msg(PUT, "key-C", "C"),  noSender());
		
		container.tell(msg(REMOVE, "keyB"), noSender());
		
		container.tell(msg(GET, "key-A"), listener);
		container.tell(msg(GET, "key-B"), listener);
		container.tell(msg(GET, "key-C"), listener);
		
		System.in.read();
		system.shutdown();
	}

	public static Object[] msg(Object... elements) {
		
		return elements;
	}

	

}
