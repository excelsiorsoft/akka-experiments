package com.excelsiorsoft.akka.supervisors.all.java;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import static akka.pattern.Patterns.ask;

public class TestForAll {

	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("demo");
		ActorRef supervisor = system.actorOf(
				Props.create(SupervisorForAll.class), "supervisor");


		//we are asking supervisor to create a child
		ActorRef childA = (ActorRef) blockingAsk(supervisor,
				Props.create(Child.class));
		
		//we are asking supervisor to create yet another child
				ActorRef childB = (ActorRef) blockingAsk(supervisor,
						Props.create(Child.class));

		{
			childA.tell(42, ActorRef.noSender());
			Integer state = (Integer) blockingAsk(childA, "get");
			System.out.println("stateA = " + state);
		}
		
		{
			childB.tell(43, ActorRef.noSender());
			Integer state = (Integer) blockingAsk(childB, "get");
			System.out.println("stateB = " + state);
		}
		
		{//provoking resume() behavior
			childA.tell(new ArithmeticException(), ActorRef.noSender());
			Integer stateA = (Integer) blockingAsk(childA, "get");
			System.out.println("stateA on ArithmeticEx= " + stateA);
			Integer stateB = (Integer) blockingAsk(childB, "get");
			System.out.println("stateB on ArithmeticEx= " + stateB);
		}
		
		{//provoking restart() behavior
			childA.tell(new NullPointerException(), ActorRef.noSender());
			Integer stateA = (Integer) blockingAsk(childA, "get");
			System.out.println("stateA on NullPointerEx= " + stateA);
			Integer stateB = (Integer) blockingAsk(childB, "get");
			System.out.println("stateB on NullPointerEx= " + stateB);
		}
		
		{//provoking stop() or behavior
			childA.tell(new IllegalArgumentException(), ActorRef.noSender());
			Integer stateA = (Integer) blockingAsk(childA, "get");
			System.out.println("stateA on IllegalArgumentEx= " + stateA);
			Integer stateB = (Integer) blockingAsk(childB, "get");
			System.out.println("stateB on IllegalArgumentEx= " + stateB);
		}
		
		/*{//provoking  escalate() behavior
			childA.tell(new Exception(), ActorRef.noSender());
			Integer stateA = (Integer) blockingAsk(childA, "get");
			System.out.println("stateA on Exception= " + stateA);
			Integer stateB = (Integer) blockingAsk(childB, "get");
			System.out.println("stateB on Exception= " + stateB);
		}*/
		
		
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
