package com.excelsiorsoft.akka.supervisors.java;

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

		ActorRef child = (ActorRef) blockingAsk(supervisor,
				Props.create(Child.class));

		{
			child.tell(42, ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state =" + state);
		}
		{
			child.tell(new ArithmeticException(), ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state =" + state);
		}
		{
			child.tell(new NullPointerException(), ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state =" + state);
		}

/*		{
			child.tell(new Exception(), ActorRef.noSender());
			child.tell(new IllegalArgumentException(), ActorRef.noSender());
			Integer state = (Integer) blockingAsk(child, "get");
			System.out.println("state =" + state);
		}*/
	}

	public static Object blockingAsk(ActorRef actor, Object msg)
			throws Exception {
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = ask(actor, msg, timeout);
		return Await.result(future, timeout.duration());

	}

}
