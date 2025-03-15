package com.sxx.jcc.core.utils.tree;

import java.util.Comparator;

public class ZTreeCompare implements Comparator {

	public int compare(Object o1, Object o2) {
		ZTreeNode node1 = (ZTreeNode) o1;
		ZTreeNode node2 = (ZTreeNode) o2;
		int result = node1.getDisporder() - node2.getDisporder();
		return result;
	}
}
