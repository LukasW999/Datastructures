package de.pp.datastructures.list;

public class ListElement {
	private int value;
	private ListElement nextElement;

	public ListElement(int value) {
		this.value = value;
	}

	public ListElement getNextElement() {
		return nextElement;
	}

	public void setNextElement(ListElement nextElement) {
		this.nextElement = nextElement;
	}

	public int getValue() {
		return value;
	}
}
