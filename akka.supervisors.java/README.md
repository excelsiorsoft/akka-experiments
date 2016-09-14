# akka.supervisors.java

This project highlights the exception handling way of Akka via supervisors hierarchy

- To start - execute Application.main() in IDE (or console).

Here's an expected output:

	state = 42
	state on ArithmeticEx= 42
	state on NullPointerEx= 0
	[WARN] [09/14/2016 11:43:38.955] [demo-akka.actor.default-dispatcher-2] [akka://demo/user/supervisor/child] null
	[ERROR] [09/14/2016 11:43:38.958] [demo-akka.actor.default-dispatcher-2] [akka://demo/user/supervisor/child] null
	java.lang.NullPointerException
		at com.excelsiorsoft.akka.supervisors.java.TestForOne.main(TestForOne.java:35)
	
	[ERROR] [09/14/2016 11:43:38.962] [demo-akka.actor.default-dispatcher-2] [akka://demo/user/supervisor/child] null
	java.lang.IllegalArgumentException
		at com.excelsiorsoft.akka.supervisors.java.TestForOne.main(TestForOne.java:42)
	
	[INFO] [09/14/2016 11:43:38.970] [demo-akka.actor.default-dispatcher-3] [akka://demo/user/supervisor/child] Message [java.lang.String] from Actor[akka://demo/temp/$a] to Actor[akka://demo/user/supervisor/child#436881809] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
	Exception in thread "main" java.util.concurrent.TimeoutException: Futures timed out after [5 seconds]
		at scala.concurrent.impl.Promise$DefaultPromise.ready(Promise.scala:219)
		at scala.concurrent.impl.Promise$DefaultPromise.result(Promise.scala:223)
		at scala.concurrent.Await$$anonfun$result$1.apply(package.scala:190)
		at scala.concurrent.BlockContext$DefaultBlockContext$.blockOn(BlockContext.scala:53)
		at scala.concurrent.Await$.result(package.scala:190)
		at scala.concurrent.Await.result(package.scala)
		at com.excelsiorsoft.akka.supervisors.java.TestForOne.blockingAsk(TestForOne.java:62)
		at com.excelsiorsoft.akka.supervisors.java.TestForOne.main(TestForOne.java:43)
	     
	
	
