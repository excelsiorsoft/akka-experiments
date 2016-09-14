/**
 * 
 */
package com.excelsiorsoft.akka.parallel.mapreduce.java;

import static akka.actor.ActorRef.noSender;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.GET;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.PUT;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.REMOVE;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Utils.msg;

import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Simeon
 *
 */
public class ApplicationTask1 {
	
	public static void main(String[] args) throws Throwable {
		
		ActorSystem system = ActorSystem.create("demo");
		ActorRef container = system.actorOf(Props.create(Container.class), "container");
		ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");
		
		container.tell(msg(PUT, "key-A", "A"),  noSender());
		container.tell(msg(PUT, "key-B", "val-B"),  noSender());
		container.tell(msg(PUT, "key-C", "C"),  noSender());
		container.tell(msg(PUT, "key-D", "val-D"),  noSender());
		
		
		Mapper<Map.Entry<String, String>, Integer> mapper = (Map.Entry<String, String> entry) -> {
			return entry.getKey().length()==entry.getValue().length()? 1 : 0;
		};
		
		Reducer<Integer> reducer = (Integer x, Integer y) -> {
			return x + y;
		};
		
		Integer accum = 0;
		container.tell(msg("map-reduce", mapper, reducer, accum), listener);
		
				
		System.in.read();
		system.shutdown();
	}

	

	

}
