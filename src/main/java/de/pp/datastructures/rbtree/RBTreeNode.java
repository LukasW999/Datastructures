package de.pp.datastructures.rbtree;

public class RBTreeNode {
	private int value;
	private RBTreeNode left;
	private RBTreeNode right;
	private RBTreeNode parent;
	private boolean black;



	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public RBTreeNode getLeft() {
		return left;
	}

	public void setLeft(RBTreeNode left) {
		this.left = left;
	}

	public RBTreeNode getRight() {
		return right;
	}

	public void setRight(RBTreeNode right) {
		this.right = right;
	}

	public RBTreeNode getParent() {
		return parent;
	}

	public void setParent(RBTreeNode parent) {
		this.parent = parent;
	}
	public RBTreeNode(int value) {
		this.value = value;
		this.black = false;
	}
}
