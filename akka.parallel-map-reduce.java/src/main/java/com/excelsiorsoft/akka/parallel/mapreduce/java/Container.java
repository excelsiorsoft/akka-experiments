/**
 * 
 */
package com.excelsiorsoft.akka.parallel.mapreduce.java;

import static akka.actor.Props.create;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * @author Simeon
 *
 */
public class Container extends UntypedActor {
	
	public static final String GET_RESULT = "get/result";
	public static final String GET = "get";
	public static final String REMOVE = "remove";
	public static final String PUT = "put";
	
	public static final String MAP_REDUCE = "map-reduce";
	private static final String MAP_REDUCE_RESULT = "map-reduce/result";
	
	private static final int NUM_OF_BUCKETS = 16;
	
	ActorRef[] buckets = new ActorRef[NUM_OF_BUCKETS];
	
	private final Map<Long, Object> results = new HashMap<>();
	private final Map<Long, Reducer> reducers = new HashMap<>();
	private final Map<Long, Integer> counts = new HashMap<>();
	private final Map<Long, ActorRef> listeners = new HashMap<>();
	
	private long lastId = 0;
	
	public Container() {
		
		for(int k = 0; k < buckets.length; k++) {
			buckets[k] = getContext().actorOf(create(Bucket.class), "bucket-"+k);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onReceive(Object msg) throws Throwable {

	Object[] msgArr = (Object[])msg; //this is an agreed upon request shape
		
									//protocol is: {command, key, ...}
		String command = (String)msgArr[0];
		String key = null;
		
		
		if(Arrays.asList(PUT, GET, REMOVE, GET_RESULT).contains(command)) {//regular hashtable ops
			key = (String) msgArr[1];
		}
		
		switch(command) {
									//just proxy to the bucket
		case PUT: 					//{"put", key, value} -> {"put", key, value}

		case REMOVE: 				//{"remove", key} -> {"remove", key}
			
			   												//getSelf() - not strictly necessary as no response is expected
			buckets[key.hashCode() % NUM_OF_BUCKETS].tell(msg, getSelf()); 
			break;

		
		case GET: 				//{"get", key} -> {"get", key, originalSender}
			
			buckets[key.hashCode() % NUM_OF_BUCKETS].tell(new Object[]{GET, key, getSender()}, getSelf());
			break;

		
		case GET_RESULT: 			//{"get/result", key, value, originalSender} -> {"get/result", key, value}
			
			String value = (String)msgArr[2];

			ActorRef originalSender = (ActorRef)msgArr[3]; //listener
			Object[] responseGet = {GET_RESULT, key, value};
			originalSender.tell(responseGet, getSelf());
			break;
			
		case MAP_REDUCE: 
			//{"map-reduce", mapper, reducer, neutral} -> {"map-reduce", id, mapper, reducer, neutral}
			Object[] newMsg = {msgArr[0], ++lastId, msgArr[1], msgArr[2], msgArr[3] };
			
			for(ActorRef bucket : buckets) {
				bucket.tell(newMsg, getSelf());
			}
			
			results.put(lastId, msgArr[3]);
			reducers.put(lastId, (Reducer)msgArr[2]);
			counts.put(lastId,0);
			listeners.put(lastId, getSender());
			break;
			
		case MAP_REDUCE_RESULT:
			//{"map-reduce/result", id, result} -> {}
			Long id = (Long)msgArr[1];
			counts.put(lastId, counts.get(id)+1);
			
			Object oldResult = results.get(id);
			Object newResult = msgArr[2];
			results.put(id, reducers.get(id).reduce(oldResult, newResult));
			
			if(counts.get(id) == NUM_OF_BUCKETS) {
				
				ActorRef originalAsker = listeners.get(id);
				originalAsker.tell(results.get(id), getSelf());
				counts.remove(id);
				reducers.remove(id);
				results.remove(id);
				listeners.remove(id);
			}
		}
		
	}

}
