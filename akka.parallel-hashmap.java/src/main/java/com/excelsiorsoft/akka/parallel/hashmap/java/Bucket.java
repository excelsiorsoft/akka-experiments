package com.excelsiorsoft.akka.parallel.hashmap.java;

import static com.excelsiorsoft.akka.parallel.hashmap.java.Application.msg;
import static com.excelsiorsoft.akka.parallel.hashmap.java.Container.*;
import java.util.HashMap;
import java.util.Map;

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
		String key = (String)msgArr[1];
		
		//protocol is: {command, key, ...}
		
		switch(command) {
		
			case PUT: 			//msg = {"put", key, value}
				Object value = msgArr[2];
				data.put(key, value);
				break;				//we are not returning anything for 'put' request
				
			case REMOVE: 		  //msg = {"remove", key}
				data.remove(key);
				break; 			  //we are not returning anything for 'remove' request
			
			case GET: 		 //msg = {"get", key, originalSender} -> {"get/result", key, originalSender}
				ActorRef originalSender = (ActorRef) msgArr[2];
				Object[] response = msg(GET_RESULT, key, data.get(key), originalSender);
				getSender().tell(response, getSelf());
				break;
		}
		
	}

	

}
