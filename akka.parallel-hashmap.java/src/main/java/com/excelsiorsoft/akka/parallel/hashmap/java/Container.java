/**
 * 
 */
package com.excelsiorsoft.akka.parallel.hashmap.java;

import static akka.actor.Props.create;
import static com.excelsiorsoft.akka.parallel.hashmap.java.Utils.printMsg;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * @author Simeon
 *
 */
public class Container extends UntypedActor {
	
	private static final int NUM_OF_BUCKETS = 16;
	ActorRef[] buckets = new ActorRef[NUM_OF_BUCKETS];
	
	public Container() {
		
		for(int k = 0; k < buckets.length; k++) {
			buckets[k] = getContext().actorOf(create(Bucket.class), "bucket-"+k);
		}
	}
	
	
	@Override
	public void onReceive(Object msg) throws Throwable {

	Object[] msgArr = (Object[])msg; //this is an agreed upon request shape
	//printMsg("container: "+msgArr);
		
		String command = (String)msgArr[0];
		String key = (String)msgArr[1];
		
		//protocol is: {command, key, ...}
		
		switch(command) {
									//just proxy to the bucket
		case "put": 				//{"put", key, value} -> {"put", key, value}
		case "remove": 				//{"remove", key} -> {"remove", key}
			
			   												//getSelf() - not strictly necessary as no response is expected
			buckets[key.hashCode() % NUM_OF_BUCKETS].tell(msg, getSelf()); 
			break;
		case "get": 				//{"get", key} -> {"get", key, originalSender}
			Object[] nextGet = {"get", key, getSender()};
			buckets[key.hashCode() % NUM_OF_BUCKETS].tell(nextGet, getSelf());
			break;
		case "get/result": 			//{"get/result", key, value, originalSender} -> {"get/result", key, value}
			String value = (String)msgArr[2];
			ActorRef originalSender = (ActorRef)msgArr[3];
			Object[] responseGet = {"get/result", key, value};
			//System.out.println("container, about to respond to `get/result`");
			originalSender.tell(responseGet, getSelf());
			break;
		}
		
	}

}
