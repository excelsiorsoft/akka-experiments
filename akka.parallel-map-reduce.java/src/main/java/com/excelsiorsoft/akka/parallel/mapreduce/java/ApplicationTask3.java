/**
 * 
 */
package com.excelsiorsoft.akka.parallel.mapreduce.java;

import static akka.actor.ActorRef.noSender;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.PUT;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Utils.msg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Simeon
 *
 */
public class ApplicationTask3 {
	
	public static void main(String[] args) throws Throwable {
		
		ActorSystem system = ActorSystem.create("demo");
		ActorRef container = system.actorOf(Props.create(Container.class), "container");
		ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");
		
		container.tell(msg(PUT, "key-A", "A"),  noSender());
		container.tell(msg(PUT, "key-B", "val-B"),  noSender());
		container.tell(msg(PUT, "key-C", "C"),  noSender());
		container.tell(msg(PUT, "key-D", "val-D"),  noSender());
		
		
		Mapper<Map.Entry, Map> mapper = (Map.Entry entry) -> {
			return Collections.singletonMap(entry.getKey(), entry.getValue());
		};
		
		Reducer<Map> reducer = (Map x, Map y) -> {
			Map result = new HashMap(x);
			result.putAll(y);
			return result;
		};
		
		Map<String, String> accum = Collections.emptyMap();
		container.tell(msg("map-reduce", mapper, reducer, accum), listener);
		
				
		System.in.read();
		system.shutdown();
	}

	

	

}
