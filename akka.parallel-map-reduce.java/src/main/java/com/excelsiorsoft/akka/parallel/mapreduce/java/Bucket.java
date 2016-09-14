package com.excelsiorsoft.akka.parallel.mapreduce.java;

import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.GET;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.GET_RESULT;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.MAP_REDUCE;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.PUT;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Container.REMOVE;
import static com.excelsiorsoft.akka.parallel.mapreduce.java.Utils.msg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/*
 * Actor acting as a wrapper over the HashMap.
 * 
 */
public class Bucket extends UntypedActor {

	private final Map data = new HashMap();
	
		
	@Override
	public void onReceive(Object msg) throws Throwable {
		
		Object[] msgArr = (Object[])msg; //this is an agreed upon request shape
		
		String command = (String)msgArr[0];
		String key = null;
		
		//protocol is: {command, key, ...}
		
		switch(command) {
		
			case PUT: 			//msg = {"put", key, value}
				key = (String)msgArr[1];
				Object value = msgArr[2];
				data.put(key, value);
				break;				//we are not returning anything for 'put' request
				
			case REMOVE: 		  //msg = {"remove", key}
				key = (String)msgArr[1];
				data.remove(key);
				break; 			  //we are not returning anything for 'remove' request
			
			case GET: 		 //msg = {"get", key, originalSender} -> {"get/result", key, originalSender}
				key = (String)msgArr[1];
				ActorRef originalSender = (ActorRef) msgArr[2];
				getSender().tell(msg(GET_RESULT, key, data.get(key), originalSender), getSelf());
				break;
				
			case MAP_REDUCE:
				//{"map-reduce", id, mapper, reducer, initElem} -> {"map-reduce/result", id, result}
				Object id = msgArr[1];
				Mapper mapper = (Mapper)msgArr[2];
				Reducer reducer = (Reducer)msgArr[3];
				Object initElem = msgArr[4];
				
				//operation must be associative
				Object result = initElem;
				for(Map.Entry entry : (Set<Map.Entry>) data.entrySet()){
					
					result = reducer.reduce(result, mapper.map(entry));
				}
				
				getSender().tell(msg("map-reduce/result", id, result), getSelf());
		}
		
	}

	

}
