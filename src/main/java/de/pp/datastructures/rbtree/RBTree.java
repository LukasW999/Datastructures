package de.pp.datastructures.rbtree;

public class RBTree {
	private RBTreeNode root;

	/**
	 * This Method adds a value correctly to the Tree. If there is currently no
	 * value in the Tree the new value becomes the root.
	 * 
	 * @param insertValue
	 */
	public void add(int insertValue) {
		if (root == null) {
			root = new RBTreeNode(insertValue);
			root.setBlack(true);
		} else {
			RBTreeNode node = root;
			while (true) {
				if (insertValue <= node.getValue()) {
					if (node.getLeft() != null) {
						node = node.getLeft();
					} else {
						node.setLeft(new RBTreeNode(insertValue));
						node.getLeft().setParent(node);
						repairTree(node.getLeft());
						return;
					}
				} else {
					if (node.getRight() != null) {
						node = node.getRight();
					} else {
						node.setRight(new RBTreeNode(insertValue));
						node.getRight().setParent(node);
						repairTree(node.getRight());
						return;
					}
				}
			}
		}
	}

	/**
	 * Returns you the uncle if there is one.
	 * 
	 * @param node The node you want the uncle from.
	 * @return The uncle of the Node.
	 */
	private RBTreeNode getUncle(RBTreeNode node) {
		if (node.getParent() == null || node.getParent().getParent() == null) {
			return null;
		}

		if (node.getParent().getParent().getRight() != null) {
			if (node.getParent().getValue() < node.getParent().getParent().getValue()) { // Checks if your
																							// Parent was a left
																							// or a right child.
				return node.getParent().getParent().getRight();
			} else {
				return node.getParent().getParent().getLeft();
			}
		} else {
			return null;
		}
	}

	/**
	 * The 3 cases you need to check if you want to insert in an RBTree. First: Your
	 * Uncle is Red. Second: You are the right child of a left child. Third: You are
	 * the left child of a right child.
	 * 
	 * @param node the Node you want to add.
	 */
	private void repairTree(RBTreeNode node) {
		while (!node.getParent().isBlack()) {
			if (node.getParent() == node.getParent().getParent().getLeft()) {
				RBTreeNode y = node.getParent().getParent().getRight();
				if (y != null && !y.isBlack()) {
					node.getParent().setBlack(true);
					y.setBlack(true);
					node.getParent().getParent().setBlack(false);
					node = node.getParent().getParent();
				} else if (node == node.getParent().getRight()) {
					node = node.getParent();
					rotateLeft(node);
				}
				if (node.getParent() == null || node.getParent().getParent() == null) {
					return;
				}

				node.getParent().setBlack(true);
				node.getParent().getParent().setBlack(false);
				rotateRight(node.getParent().getParent());

			} else {
				RBTreeNode y = node.getParent().getParent().getLeft();
				if (y != null && !y.isBlack()) {
					node.getParent().setBlack(true);
					y.setBlack(true);
					node.getParent().getParent().setBlack(false);
					node = node.getParent().getParent();
				} else if (node == node.getParent().getLeft()) {
					node = node.getParent();
					rotateRight(node);
				}

				if (node.getParent() == null || node.getParent().getParent() == null) {
					return;
				}
				node.getParent().setBlack(true);
				node.getParent().getParent().setBlack(false);
				rotateLeft(node.getParent().getParent());

			}
		}
		root.setBlack(true);

	}

//	private void repairTree(RBTreeNode node) {
//		if (node == root) { // if you are the You dont need to change anything
//			return;
//		}
//		RBTreeNode uncle = getUncle(node);
//		if (uncle != null) {
//			if (!uncle.isBlack()) {
//				changeTheColoursOfYourParentAndYourUncleToRedAndYourGrandparentToRed(node);
////				rbAddExtra(node.getParent());
//			} else if (node == node.getParent().getRight()) {
//				rotateLeft(node.getParent());
//				// inner child
//			} else if() {
//				rotate
//				// outer child
//			}
//			rotateParent(node);
//			rotateGrandParent(node);
////				rbAddExtra(node.getParent());
//		}
//
//	}

	/**
	 * Checks if you are the right child from a left child or if you are the left
	 * child of a right child. If that is true it switches the connections that you
	 * become the Parent of your Parent.
	 * 
	 * @param node
	 *
	 */
	public void rotateLeft(RBTreeNode node) {
		RBTreeNode y = node.getRight();
		node.setRight(y.getLeft());
		if (y.getLeft() != null) {
			y.getLeft().setParent(node);
		}
		y.setParent(node.getParent());
		if (node.getParent() == null) {
			root = y;
		} else if (node == node.getParent().getLeft()) {
			node.getParent().setLeft(y);
		} else {
			node.getParent().setRight(y);
		}
		y.setLeft(node);
		node.setParent(y);

	}

	public void rotateRight(RBTreeNode node) {
		RBTreeNode y = node.getLeft();
		node.setLeft(y.getRight());
		if (y.getRight() != null) {
			y.getRight().setParent(node);
		}
		y.setParent(node.getParent());
		if (node.getParent() == null) {
			root = y;
		} else if (node == node.getParent().getRight()) {
			node.getParent().setRight(y);
		} else {
			node.getParent().setLeft(y);
		}
		y.setRight(node);
		node.setParent(y);
	}
//	private void rotateParent(RBTreeNode node) {
//		if (node.getParent() == node.getParent().getParent().getLeft()) { // Checks if you are the right
//																			// child from a left child
//			if (node.getParent().getValue() < node.getValue()) {
//				RBTreeNode newChild = node.getParent(); // Stores the Parent that the Connections can be changed without
//				// losing the Parent.
//				node.setParent(node.getParent().getParent());
//				node.getParent().setLeft(node);
//				newChild.setParent(node);
//				node.setLeft(newChild);
//				newChild.setRight(null);
//			}
//		} else {
//			if (node.getParent().getValue() > node.getValue()) {
//				RBTreeNode newChild = node.getParent(); // Stores the Parent that the Connections can be changed without
//														// losing the Parent.
//				node.setParent(node.getParent().getParent());
//				node.getParent().setRight(node);
//				newChild.setParent(node);
//				node.setRight(newChild);
//				newChild.setLeft(null);
//			}
//		}
//	}

	/**
	 * Checks if you are the right child of a right child or if you are the left
	 * child of a left child. If thats the case it change the connections that the
	 * parent is at the place from the Grandparent, the Grandparent becomes the
	 * right or left child of the Parent.
	 * 
	 * @param node
	 */
	private void rotateGrandParent(RBTreeNode node) {
		if (node.getParent().getValue() < node.getParent().getParent().getValue()) {
			if (node.getValue() < node.getParent().getValue()) {

				RBTreeNode newRightParent = node.getParent().getParent(); // Stores the Grandparent that the Connections
																			// can be changed without losing the
																			// Grandparent.
				node.getParent().setParent(newRightParent.getParent());
				node.getParent().getParent().setLeft(node.getParent()); // EVTL Fehlerquelle da nicht überprüft wird ob
																		// der grandparent das recht oder das linke kind
																		// des grand grand parents ist
				newRightParent.setLeft(node.getParent().getRight());
				node.getParent().getRight().setParent(newRightParent);
				node.getParent().setRight(newRightParent);
				newRightParent.setParent(node.getParent());
				node.getParent().setBlack(true);
				getYourSibling(node).setBlack(false);
			}
		} else if (node.getParent().getValue() > node.getParent().getParent().getValue()) {
			if (node.getValue() > node.getParent().getValue()) {
				RBTreeNode newLeftParent = node.getParent().getParent();// Stores the Grandparent that the Connections
																		// can be changed without losing the
																		// Grandparent.
				node.getParent().setParent(newLeftParent.getParent());
				if (newLeftParent.getParent() != null) {
					if (newLeftParent.getParent().getValue() > newLeftParent.getValue()) {
						node.getParent().getParent().setLeft(node.getParent());
					} else {
						node.getParent().getParent().setRight(node.getParent());
					}
				}
			}
		}
	}

	/**
	 * Switches the color of your Parent and your Uncle to Black. And if your
	 * Grandfather is not the root it becomes red.
	 * 
	 * @param node You
	 */
	private void changeTheColoursOfYourParentAndYourUncleToRedAndYourGrandparentToRed(RBTreeNode node) {
		node.getParent().setBlack(true);
		getUncle(node).setBlack(true);
		if (node.getParent().getParent() == root) { // Checks if your Grandfather is the root.
			node.getParent().getParent().setBlack(true);
		} else {
			node.getParent().getParent().setBlack(false);
		}
	}

	/**
	 * Gives you your Sibling. If you have none return null.
	 * 
	 * @param node You
	 * @return Your sibling
	 */
	private RBTreeNode getYourSibling(RBTreeNode node) {
		if (node.getParent() != null) { // If you have no Parent you have no sibling
			if (node.getParent().getValue() < node.getValue()) { // Checks if you are a Right or a left child
				if (node.getParent().getLeft() != null) { // Checks if you have a sibling
					return node.getParent().getLeft();
				}
			} else {
				if (node.getParent().getRight() != null) { // Checks if you have a sibling
					return node.getParent().getRight();
				}
			}
		}
		return null;
	}

//	private void ifTheUncleIsRed(RBTreeNode node) {
//		if (isTheUncleBlack(node) == false) {
//			node.getParent().setBlack(true);
//			getYourUncle(node).setBlack(true);
//			if (node.getParent().getParent().getValue() != root.getValue()) {
//				node.getParent().getParent().setBlack(false);
//			} else {
//				node.getParent().getParent().setBlack(true);
//			}
//		}
//	}
//
//	/**
//	 * Checks if the Uncle of the node is Black
//	 * 
//	 * @param child
//	 * @return true if the uncle is Black
//	 */
//	private boolean isTheUncleBlack(RBTreeNode child) {
//		if(getYourUncle(child)!= null) {
//		return getYourUncle(child).isBlack();
//		}
//		return true;
//	}
//
//	/**
//	 * Switches the Colour of the node.
//	 * 
//	 * @param node
//	 */
//

	/**
	 * Is there that you dont need to insert the root by yourself.
	 */
	public void outputTree() {
		outputTree(root);
	}

	/**
	 * Prints the Value of the Tree like a Tree.
	 * 
	 * @param node
	 */
	private void outputTree(RBTreeNode node) {
		System.out.println("Mitte : " + node.getValue());
		if (node.getLeft() != null) {
			System.out.println("Links von " + node.getValue() + ":" + node.getLeft().getValue());
			outputTree(node.getLeft());
		}
		if (node.getRight() != null) {
			System.out.println("Rechts von " + node.getValue() + ": " + node.getRight().getValue());
			outputTree(node.getRight());
		}
	}

	/**
	 * Is there that you dont need to insert the root by yourself.
	 */
	public int depth() {
		return depth(root);
	}

	/**
	 * Gives you the depth of the Tree.
	 * 
	 * @param node
	 * @return The depth of the Tree.
	 */
	private int depth(RBTreeNode node) {
		if (node == null) {
			return 0;
		}
		return Math.max(depth(node.getLeft()), depth(node.getRight())) + 1;
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
	private void treeToString(RBTreeNode node) {
		if (node.getLeft() != null) {
			treeToString(node.getLeft());
		}
		System.out.println(node.getValue() + " hat die Farbe " + (node.isBlack() ? "Schwarz" : "Rot"));
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
	private int size(RBTreeNode node) {
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
	private RBTreeNode getTheBiggest(RBTreeNode node) {
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
	private RBTreeNode getTheSmallest(RBTreeNode node) {
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
	public RBTreeNode find(int value) {
		RBTreeNode node = root;
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

	public RBTreeNode getRoot() {
		return root;
	}

	/**
	 * Remove a value from the Tree
	 * 
	 * @param value
	 */
	public void remove(int value) {
		// find node

		RBTreeNode node = find(value);
		if (node == null) {
			System.out.println(value + " kann nicht gelöscht werden da " + value + " nicht vorhanden ist.");
			return;
		}
		// search replacement node
		RBTreeNode switchNode = null;
		if (node.getLeft() != null) {
			switchNode = getTheBiggest(node.getLeft());
		} else if (node.getRight() != null) {
			switchNode = getTheSmallest(node.getRight());
		}

		// finden ersatz ende funktioniert
		// verbindungen ändern
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
