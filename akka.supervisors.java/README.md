# akka.supervisors.java

###This project highlights the exception handling way of Akka via supervisors hierarchy

####Testing different behaviors of com.excelsiorsoft.akka.supervisors.one.java.OneForOneStrategy supervisor strategy

- To start - execute Application.main() in IDE (or console).

Here's an expected output:

		state = 42
	state on ArithmeticEx= 42
	state on NullPointerEx= 0
	[WARN] [09/14/2016 11:51:36.116] [demo-akka.actor.default-dispatcher-3] [akka://demo/user/supervisor/child] null
	[ERROR] [09/14/2016 11:51:36.120] [demo-akka.actor.default-dispatcher-3] [akka://demo/user/supervisor/child] null
	java.lang.NullPointerException
		at com.excelsiorsoft.akka.supervisors.one.java.TestForOne.main(TestForOne.java:35)
	
	[ERROR] [09/14/2016 11:51:36.124] [demo-akka.actor.default-dispatcher-5] [akka://demo/user/supervisor/child] null
	java.lang.IllegalArgumentException
		at com.excelsiorsoft.akka.supervisors.one.java.TestForOne.main(TestForOne.java:42)
	
	[INFO] [09/14/2016 11:51:36.132] [demo-akka.actor.default-dispatcher-3] [akka://demo/user/supervisor/child] Message [java.lang.String] from Actor[akka://demo/temp/$a] to Actor[akka://demo/user/supervisor/child#260588983] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
	Exception in thread "main" java.util.concurrent.TimeoutException: Futures timed out after [5 seconds]
		at scala.concurrent.impl.Promise$DefaultPromise.ready(Promise.scala:219)
		at scala.concurrent.impl.Promise$DefaultPromise.result(Promise.scala:223)
		at scala.concurrent.Await$$anonfun$result$1.apply(package.scala:190)
		at scala.concurrent.BlockContext$DefaultBlockContext$.blockOn(BlockContext.scala:53)
		at scala.concurrent.Await$.result(package.scala:190)
		at scala.concurrent.Await.result(package.scala)
		at com.excelsiorsoft.akka.supervisors.one.java.TestForOne.blockingAsk(TestForOne.java:62)
		at com.excelsiorsoft.akka.supervisors.one.java.TestForOne.main(TestForOne.java:43)

####Testing different behaviors of com.excelsiorsoft.akka.supervisors.all.java.AllForOneStrategy supervisor strategies:

- **resume - just continuing all actors upon one of them failing:**

	stateA = 42
	stateB = 43
	stateA on ArithmeticEx= 42
	stateB on ArithmeticEx= 43
	[WARN] [09/14/2016 12:07:43.947] [demo-akka.actor.default-dispatcher-3] [akka://demo/user/supervisor/child0.964487845983337] null

- **restart: - all actors are restarted (their state is flushed) upon one of them failing**

	stateA = 42
	stateB = 43
	[ERROR] [09/14/2016 12:16:18.729] [demo-akka.actor.default-dispatcher-5] [akka://demo/user/supervisor/child0.6836412615103038] null
	java.lang.NullPointerException
		at com.excelsiorsoft.akka.supervisors.all.java.copy.TestForAll.main(TestForAll.java:49)
	
	stateA on NullPointerEx= 0
	stateB on NullPointerEx= 0

- **stop - all actors are stopped upon one of them failing:**

	stateA = 42
	stateB = 43
	[ERROR] [09/14/2016 12:23:20.818] [demo-akka.actor.default-dispatcher-3] [akka://demo/user/supervisor/child0.8623912667024851] null
	java.lang.IllegalArgumentException
		at com.excelsiorsoft.akka.supervisors.all.java.copy.TestForAll.main(TestForAll.java:57)
	
	[INFO] [09/14/2016 12:23:20.832] [demo-akka.actor.default-dispatcher-5] [akka://demo/user/supervisor/child0.8623912667024851] Message [java.lang.String] from Actor[akka://demo/temp/$a] to Actor[akka://demo/user/supervisor/child0.8623912667024851#-2142012589] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
	Exception in thread "main" java.util.concurrent.TimeoutException: Futures timed out after [5 seconds]
		at scala.concurrent.impl.Promise$DefaultPromise.ready(Promise.scala:219)
		at scala.concurrent.impl.Promise$DefaultPromise.result(Promise.scala:223)
		at scala.concurrent.Await$$anonfun$result$1.apply(package.scala:190)
		at scala.concurrent.BlockContext$DefaultBlockContext$.blockOn(BlockContext.scala:53)
		at scala.concurrent.Await$.result(package.scala:190)
		at scala.concurrent.Await.result(package.scala)
		at com.excelsiorsoft.akka.supervisors.all.java.copy.TestForAll.blockingAsk(TestForAll.java:89)
		at com.excelsiorsoft.akka.supervisors.all.java.copy.TestForAll.main(TestForAll.java:58)

- **escalate - behaves similar to stop:**

	stateA = 42
	stateB = 43
	[ERROR] [09/14/2016 12:27:18.110] [demo-akka.actor.default-dispatcher-2] [akka://demo/user/supervisor] null
	java.lang.Exception
		at com.excelsiorsoft.akka.supervisors.all.java.copy.TestForAll.main(TestForAll.java:65)
	
	[INFO] [09/14/2016 12:27:18.120] [demo-akka.actor.default-dispatcher-4] [akka://demo/user/supervisor/child0.8771455942903627] Message [java.lang.String] from Actor[akka://demo/temp/$a] to Actor[akka://demo/user/supervisor/child0.8771455942903627#-1432719735] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
	Exception in thread "main" java.util.concurrent.TimeoutException: Futures timed out after [5 seconds]
		at scala.concurrent.impl.Promise$DefaultPromise.ready(Promise.scala:219)
		at scala.concurrent.impl.Promise$DefaultPromise.result(Promise.scala:223)
		at scala.concurrent.Await$$anonfun$result$1.apply(package.scala:190)
		at scala.concurrent.BlockContext$DefaultBlockContext$.blockOn(BlockContext.scala:53)
		at scala.concurrent.Await$.result(package.scala:190)
		at scala.concurrent.Await.result(package.scala)
		at com.excelsiorsoft.akka.supervisors.all.java.copy.TestForAll.blockingAsk(TestForAll.java:89)
		at com.excelsiorsoft.akka.supervisors.all.java.copy.TestForAll.main(TestForAll.java:66)
