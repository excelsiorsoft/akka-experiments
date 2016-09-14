package com.excelsiorsoft.akka.monitors.java;



import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import static akka.pattern.Patterns.ask;
import static akka.actor.ActorRef.noSender;
import static akka.actor.Props.create;

public class TestMonitoring {

	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("demo");
		ActorRef supervisor = system.actorOf(create(SupervisorForOne.class), "supervisor");
		ActorRef monitor = system.actorOf(create(Monitor.class), "monitor");

		ActorRef child = (ActorRef)blockingAsk(supervisor, create(Child.class));
		//Turn ON monitoring
		monitor.tell(child, noSender());//let's monitor the child now

		
		{
			//set state to 42
			child.tell(42, noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state = " + state);
		}
		
		
		{//provoking restart() behavior
			child.tell(new NullPointerException(), noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state on NullPointerEx= " + state);
			
		}
		
 
		
		{//provoking  escalate() behavior
			child.tell(new Exception(), ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state on Exception= " + state);
			
		}
		
		system.shutdown();
	}

	
	/**
	 * Java synchronous call emulation
	 * 
	 * @param actor
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static Object blockingAsk(ActorRef actor, Object msg)
			throws Exception {
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = ask(actor, msg, timeout); //scala's future

		return Await.result(future, timeout.duration());

	}

}
