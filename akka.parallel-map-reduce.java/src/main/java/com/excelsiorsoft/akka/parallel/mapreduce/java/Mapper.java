package com.excelsiorsoft.akka.parallel.mapreduce.java;

public interface Mapper<T, R> {

	R map(T arg);
}
