package de.pp.datastructures.tree;

public class Tree {
	private TreeNode root;

	public int depth() {
		TreeNode node = root;
		return depth(node);
	}

	private int depth(TreeNode node) {
		if (node == null) {
			return 0;
		}
		return Math.max(depth(node.getLeft()), depth(node.getRight())) + 1;
	}

	/**
	 * This Method adds a value correctly to the Tree. If there is currently no
	 * value in the Tree the new value becomes the root.
	 * 
	 * @param insertValue
	 */
	public void add(int insertValue) {
		if (root == null) {
			root = new TreeNode(insertValue);
		} else {
			TreeNode node = root;
			while (true) {
				if (insertValue <= node.getValue()) {
					if (node.getLeft() != null) {
						node = node.getLeft();
					} else {
						node.setLeft(new TreeNode(insertValue));
						node.getLeft().setParent(node);
						return;
					}
				} else {
					if (node.getRight() != null) {
						node = node.getRight();
					} else {
						node.setRight(new TreeNode(insertValue));
						node.getRight().setParent(node);
						return;
					}
				}
			}
		}
	}

	/**
	 * Prints the Value of the node and his neighbors.
	 * 
	 * @param node
	 */
	public void outputTree(TreeNode node) {
		if (node.getLeft() != null) {
			System.out.println(node.getLeft().getValue());
		}
		System.out.println(node.getValue());

		if (node.getRight() != null) {
			System.out.println(node.getRight().getValue());
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public void treeToString() {
		treeToString(root);
	}

	/**
	 * Prints the Tree in the right order.
	 * 
	 * @param node
	 * @param result
	 * @return An List with all values sorted.
	 */
	private void treeToString(TreeNode node) {
		if (node.getLeft() != null) {
			treeToString(node.getLeft());
		}
		System.out.println(node.getValue());
		if (node.getRight() != null) {
			treeToString(node.getRight());
		}
	}

	public int size() {
		return size(root);
	}

	/**
	 * Gives you the total number of Elements in the Tree.
	 * 
	 * @param node
	 * @param result
	 * @return
	 */
	private int size(TreeNode node) {
		if (node == null) {
			return 0;
		}
		return size(node.getLeft()) + size(node.getRight()) + 1;
	}

	/**
	 * Gives you the biggest value in the Tree
	 * 
	 * @param node
	 * @return
	 */
	private TreeNode getTheBiggest(TreeNode node) {
		while (node.getRight() != null) {
			node = node.getRight();
		}
		return node;
	}

	/**
	 * Gives you the biggest value in the Tree.
	 * 
	 * @param node
	 * @return
	 */
	private TreeNode getTheSmallest(TreeNode node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}

	/**
	 * Checks if the value is in the Tree.
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains(int value) {
		return find(value) != null;
	}

	/**
	 * Finds a TreeNote
	 * 
	 * @param value
	 * @return
	 */
	private TreeNode find(int value) {
		TreeNode node = root;
		while (node != null) {
			if (value == node.getValue()) {
				return node;
			}
			if (value <= node.getValue()) {
				node = node.getLeft();
			} else {
				node = node.getRight();
			}
		}
		return null;
	}

	/**
	 * Remove a value from the Tree
	 * 
	 * @param value
	 */
	public void remove(int value) {
		// find node

		TreeNode node = find(value);
		if (node == null) {
			System.out.println(value + " kann nicht gelöscht werden da " + value + " nicht vorhanden ist.");
			return;
		}
		// search replacement node
		TreeNode switchNode = null;
		if (node.getLeft() != null) {
			switchNode = getTheBiggest(node.getLeft());
		} else if (node.getRight() != null) {
			switchNode = getTheSmallest(node.getRight());
		}

		// switch Connections 
		if (switchNode == null) {
			if (node.getParent().getValue() < node.getValue()) {
				node.getParent().setRight(null);
				return;
			} else {
				node.getParent().setLeft(null);
				return;
			}
		}
		if (switchNode.getValue() < switchNode.getParent().getValue()) {
			switchNode.getParent().setLeft(null);
		} else {
			switchNode.getParent().setRight(null);
		}
		if (node.getLeft() != null) {
			switchNode.setLeft(node.getLeft());
			switchNode.getLeft().setParent(switchNode);
		}
		if (node.getRight() != null) {
			switchNode.setRight(node.getRight());
			switchNode.getRight().setParent(switchNode);
		}
		if (node.getParent() != null) {
			if (node.getParent().getValue() >= node.getValue()) {
				node.getParent().setLeft(switchNode);
			} else {
				node.getParent().setRight(switchNode);
			}
		} else {
			root = switchNode;
		}

		switchNode.setParent(node.getParent());

	}
}
