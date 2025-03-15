package com.sxx.jcc.core.utils.tree;

import java.util.Comparator;

public class TreeCompare implements Comparator {

	public int compare(Object o1, Object o2) {
		TreeNode node1 = (TreeNode) o1;
		TreeNode node2 = (TreeNode) o2;
		int result = node1.getId().compareTo(node2.getId());
		return result;
	}

}
