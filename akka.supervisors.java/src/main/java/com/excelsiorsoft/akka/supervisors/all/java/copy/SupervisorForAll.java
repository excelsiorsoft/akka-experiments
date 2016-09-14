package com.excelsiorsoft.akka.supervisors.all.java.copy;



import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;

public class SupervisorForAll extends UntypedActor{

	private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"),
			new Function<Throwable, SupervisorStrategy.Directive>() {
		@Override
		public SupervisorStrategy.Directive apply(Throwable t){
			
			//actor is suspended upon exception and awaits a directive from its supervisor
			//on how to proceed
			
			if(t instanceof ArithmeticException) {
				
				//all is fine, reusing the same actor instance
				return SupervisorStrategy.resume();
				
			}else if(t instanceof NullPointerException) {
				
				//state of actor is flushed, but its mailbox remains
				return SupervisorStrategy.restart();
				
			}else if (t instanceof IllegalArgumentException) {
				return SupervisorStrategy.stop();
			}else {
				return SupervisorStrategy.escalate();
			}
		}
	});
	
	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
	
	@Override
	public void onReceive(Object msg) throws Throwable {
		if(msg instanceof Props) {
			ActorRef response = getContext().actorOf((Props)msg, "child");
			getSender().tell(response, getSelf());//send back reference to the created child
		}else {
			unhandled(msg);
		}
	}

}
