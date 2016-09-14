package com.excelsiorsoft.akka.supervisors.all.java.copy;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import static akka.pattern.Patterns.ask;

public class TestForOne {

	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("demo");
		ActorRef supervisor = system.actorOf(
				Props.create(SupervisorForOne.class), "supervisor");


		//we are asking supervisor to create a child
		ActorRef child = (ActorRef) blockingAsk(supervisor,
				Props.create(Child.class));

		{
			child.tell(42, ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state = " + state);
		}
		{
			child.tell(new ArithmeticException(), ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state on ArithmeticEx= " + state);
		}
		{
			child.tell(new NullPointerException(), ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state on NullPointerEx= " + state);
		}

		{
			//child.tell(new Exception(), ActorRef.noSender());
			child.tell(new IllegalArgumentException(), ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state on IllegalArgumentEx=" + state);
		}
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
