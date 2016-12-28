package io.app.core.util;

public class Pair<S,T> {
	
	private S first;
	
	private T second;

	public S getFirst() {
		return first;
	}
	
	public Pair(S s,T t){
		this.first=s;
		this.second=t;
	}

	public void setFirst(S first) {
		this.first = first;
	}

	public T getSecond() {
		return second;
	}

	public void setSecond(T second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}
	
	

}
