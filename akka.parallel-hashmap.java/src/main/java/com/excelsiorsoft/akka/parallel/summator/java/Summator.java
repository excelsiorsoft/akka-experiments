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
	public void onReceive(Object msg) throws Throwable {}

	private int calc(int from, int to) {

		int result = 0;

		for (int k = from; k <= to; k++) {

			result += k;
		}

		return result;
	}

}
