package de.pp.datastructures.list;

public class IntListImpl implements IntList {

	private ListElement first;

	@Override
	public void add(int value) {
		if (first == null) {
			first = new ListElement(value);
		} else {
			ListElement element = first;
			while (true) {
				if (element.getNextElement() == null) {
					element.setNextElement(new ListElement(value));
					return;
				} else {
					element = element.getNextElement();
				}
			}
		}
	}

	@Override
	public int get(int i) {
		if (i < 0) {
			return -1;
		}
		int counter = 0;
		ListElement element = first;
		while (counter < i) {
			if (element.getNextElement() == null) {
				return -2;
			}
			element = element.getNextElement();
			counter++;
		}
		return element.getValue();
	}

	@Override
	public int size() {
		ListElement element = first;
		int counter = 0;
		while (element != null) {
			element = element.getNextElement();
			counter++;
		}
		return counter;
	}

	@Override
	public void delete(int i) {
		if (i == 0) {
			first = first.getNextElement();
			return;
		}
		if (i >= size()) {
			return;
		}

		ListElement element = first;
		int counter = 0;
		while (element != null && counter < i - 1) {
			element = element.getNextElement();
			counter++;
		}
		if (element != null) {
			element.setNextElement(element.getNextElement().getNextElement());
		}
	}

	@Override
	public boolean contains(int value) {
		ListElement element = first;
		while (element != null) {
			if (element.getValue() == value) {
				return true;
			}
			element = element.getNextElement();
		}
		return false;
	}

	@Override
	public void add(int i, int value) {
		ListElement newElement = new ListElement(value);
		ListElement element = first;
		if (i <= 0) {
			newElement.setNextElement(first);
			first = newElement;
		} else {
			int counter = 0;
			while (element.getNextElement() != null && counter < i - 1) {
				element = element.getNextElement();
				counter++;
			}
			newElement.setNextElement(element.getNextElement());
			element.setNextElement(newElement);
		}
	}

	@Override
	public void addAll(int... value) {
		for (int j : value) {
			add(j);
		}
	}
}
