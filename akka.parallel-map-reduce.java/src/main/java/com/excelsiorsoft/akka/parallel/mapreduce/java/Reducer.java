/**
 * 
 */
package com.excelsiorsoft.akka.parallel.mapreduce.java;

/**
 * @author Simeon
 *
 */
public interface Reducer<T> {

	T reduce(T left, T right);
}
