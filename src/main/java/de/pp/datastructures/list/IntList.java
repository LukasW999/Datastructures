package de.pp.datastructures.list;

public interface IntList {

	void add(int value);

	int get(int i);

	int size();

	void delete(int i);
	
	boolean contains(int value);
	
	void add(int i, int value);
	
	void addAll(int... value);
}
