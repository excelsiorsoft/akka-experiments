package com.excelsiorsoft.akka.supervisors.all.java.copy;

import akka.actor.UntypedActor;

public class Child extends UntypedActor{

	private int state = 0;

	@Override
	public void onReceive(Object o) throws Throwable {
		
		if (o instanceof Exception) {
			throw (Exception)o;
		}else if(o instanceof Integer){
			state = (Integer)o;
		}else if(o.equals("get")) {
			getSender().tell(state, getSelf());
		}else {
			unhandled(o);
		}
		
	}
	
	
}
