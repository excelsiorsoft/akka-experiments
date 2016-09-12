package com.excelsiorsoft.akka.parallel.summator.java;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

/*
 */
public class Summator extends UntypedActor {

	private final ActorRef listener;
	private int NUMBER_OF_SLAVES = 2;
	
	private final List<Integer> state = new ArrayList<>(NUMBER_OF_SLAVES);
	private ActorRef slave1;
	private ActorRef slave2;

	public Summator(ActorRef master) {
		this.listener = master;
	}

	@Override
	public void onReceive(Object msg) throws Throwable {

		if (msg instanceof int[]) {//message came from the master

			int from = ((int[]) msg)[0];
			int to = ((int[]) msg)[1];

			if (to - from > 3) {
				//structural recursion, 
				//too much work, give the work out to spawned slaves
				(slave1 = getContext().actorOf(
						Props.create(Summator.class, getSelf()))).tell(
						new int[] { from, (from + to) >>> 1 }, getSelf());
				
				(slave2 = getContext().actorOf(
						Props.create(Summator.class, getSelf()))).tell(
						new int[] { ((from + to) >>> 1)+1,to }, getSelf());
			}else {//can do work myself
				listener.tell(calc(from,to), getSelf());
			}
		}else if(msg instanceof Integer) {//result comes fromo slaves through here
			
			state.add((Integer) msg);
			if(state.size() == NUMBER_OF_SLAVES) {
				
				listener.tell(state.get(0)+state.get(1), getSelf());
				
				//actor finished theri tasks, kill 'em now
				slave1.tell(PoisonPill.getInstance(), getSelf());
				slave2.tell(PoisonPill.getInstance(), getSelf());
			}
		}

	}

	private int calc(int from, int to) {

		int result = 0;

		for (int k = from; k <= to; k++) {

			result += k;
		}

		return result;
	}

}
