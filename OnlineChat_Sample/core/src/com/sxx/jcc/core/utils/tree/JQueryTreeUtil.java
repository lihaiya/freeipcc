package com.sxx.jcc.core.utils.tree;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JQueryTreeUtil {

	/**
	 * 根据树结构数据组织json数据,当前方法默认为异步加载
	 * 
	 * @param dataList
	 *            树结构数据
	 * @param stepLength
	 *            树结构数据步长
	 * @param rootName
	 *            自定义根节点名称,当为null时不显示
	 * @param preId
	 *            需要显示的节点的父ID，第一次初始化时可以指定为-1，以后为当前点击节点的ID
	 * @param reflash
	 *            判断当前节点是否加载还是刷新，true：刷新、false：加载，加载时显示虚拟节点，刷新时不需要再次显示根节点
	 * @return
	 */
	public static String ajaxTreeBuilder(List dataList, int stepLength, String rootName,
			String preId, boolean reflash) {
		StringBuffer sb = new StringBuffer();

		/* 虚拟根节点，该节点不显示 */
		TreeNode rootNode = new TreeNode();
		rootNode.setId(preId);
		rootNode.setPid("-2");
		rootNode.setText(rootName);
		rootNode.setValue(null);
		rootNode.setHasChildren(true);
		Map treeMap = new HashMap();
		treeMap.put(rootNode.getId(), rootNode);
		/* 遍历节点数据 */
		for (int i = 0; i < dataList.size(); i++) {
			TreeNode node = (TreeNode) dataList.get(i);
			if (node.getId().length() == stepLength) {
				node.setPid(rootNode.getId());
			} else {
				node.setPid(node.getId().substring(0, (node.getId().length() - stepLength)));
			}
			if (null != node.getPid() && treeMap.containsKey(node.getPid())) {
				TreeNode t = (TreeNode) treeMap.get(node.getPid());
				t.getChildNodes().add(node);
			}
			treeMap.put(node.getId(), node);
		}
		/* 判断当前是否需要显示自定义根节点名称，rootName参数为null则不显示根节点名称 */
		if (null != rootName && null != preId && "-1".equals(preId) && reflash == false) {
			sb.append("{\"id\":\"" + stringFormat(rootNode.getId()) + "\",\"text\":\""
					+ stringFormat(rootName) + "\"");
			sb.append(",\"value\":\"" + stringFormat(rootNode.getValue())
					+ "\",\"showcheck\":false");
			sb.append(",\"isexpand\":true,\"checkstate\":0");
			sb.append(",\"hasChildren\":true,\"ChildNodes\":[");
			// 遍历子节点数据
			sb.append(getChildNode(rootNode.getChildNodes(), null, null, true, null));
			sb.append("],\"complete\" : true}");
		} else {
			sb.append(getChildNode(rootNode.getChildNodes(), null, null, true, null));
		}
		return sb.toString().replaceAll("/", "\\\\");
	}

	/**
	 * 根据树结构数据组织json数据,当前方法默认为异步加载
	 * 
	 * @param dataList
	 *            树结构数据
	 * @param stepLength
	 *            树结构数据步长
	 * @param preId
	 *            需要显示的节点的父ID，第一次初始化时可以指定为-1，以后为当前点击节点的ID
	 * @return
	 */
	public static String ajaxTreeBuilder(List dataList, int stepLength, String preId) {
		StringBuffer sb = new StringBuffer();

		/* 虚拟根节点，该节点不显示 */
		TreeNode rootNode = new TreeNode();
		rootNode.setId(preId);
		rootNode.setPid("-2");
		rootNode.setText("");
		rootNode.setValue("");
		rootNode.setHasChildren(true);
		Map treeMap = new HashMap();
		treeMap.put(rootNode.getId(), rootNode);
		/* 遍历节点数据 */
		for (int i = 0; i < dataList.size(); i++) {
			TreeNode node = (TreeNode) dataList.get(i);
			if (node.getId().length() == stepLength) {
				node.setPid(rootNode.getId());
			} else {
				node.setPid(node.getId().substring(0, (node.getId().length() - stepLength)));
			}
			if (null != node.getPid() && treeMap.containsKey(node.getPid())) {
				TreeNode t = (TreeNode) treeMap.get(node.getPid());
				t.getChildNodes().add(node);
			}
			treeMap.put(node.getId(), node);
		}
		sb.append(getChildNode(rootNode.getChildNodes(), null, null, true, null));

		return sb.toString().replaceAll("/", "\\\\");
	}

	/**
	 * 根据树结构数据组织json数据,当前方法默认为一次性加载
	 * 
	 * @param dataList
	 *            树结构数据
	 * @param stepLength
	 *            树结构数据步长
	 * @return
	 */
	public static String treeBuilder(List dataList, int stepLength) {
		return treeBuilder(dataList, stepLength, null);
	}

	/**
	 * 根据树结构数据组织json数据,当前方法默认为一次性加载
	 * 
	 * @param dataList
	 *            树结构数据
	 * @param stepLength
	 *            树结构数据步长
	 * @param rootName
	 *            自定义根节点名称,当为null时不显示
	 * @return
	 */
	public static String treeBuilder(List dataList, int stepLength, String rootName) {
		return treeBuilder(dataList, stepLength, rootName, null, null);
	}

	public static String treeBuilder(List dataList, int stepLength, String rootName,
			String[] expandIds, String checkIds[]) {
		return treeBuilder(dataList, stepLength, rootName, expandIds, checkIds, null);
	}

	/**
	 * * 根据树结构数据组织json数据
	 * 
	 * @param dataList
	 *            树结构数据
	 * @param stepLength
	 *            树结构数据步长
	 * @param rootName
	 *            自定义根节点名称,当为null时不显示
	 * @param expandIds
	 *            展开节点，当为null时不展开
	 * @param checkIds
	 *            默认选中节点，当为null时不选中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String treeBuilder(List dataList, int stepLength, String rootName,
			String[] expandIds, String checkIds[], String showCheckIds[]) {
		// 获取第一个节点id长度
		int firstIdLength = ((TreeNode) dataList.get(0)).getId().length();

		StringBuffer sb = new StringBuffer();

		/* 虚拟根节点，rootName参数为null时，该节点不显示，否则该节点显示名称为rootName */
		TreeNode rootNode = new TreeNode();
		rootNode.setId("-1");
		rootNode.setPid("-2");
		rootNode.setText("");
		rootNode.setValue("");
		rootNode.setHasChildren(true);
		Map treeMap = new HashMap();
		treeMap.put(rootNode.getId(), rootNode);

		/* 如果expandIds参数不为空，创建临时map对象记录需要展开的ID，为后面json数据中设置isexpand做准备 */
		Map expandIdMap = new HashMap();
		if (null != expandIds && expandIds.length > 0) {
			for (int i = 0; i < expandIds.length; i++) {
				String code = expandIds[i];
				// 获取当前节点级数
				int step = code.length() / stepLength;
				for (int j = 0; j < step; j++) {
					// 获取当前节点的上一级节点ID，并判断上一级节点是否存在
					String preId = expandIds[i]
							.substring(0, expandIds[i].length() - stepLength * j);
					if (preId.length() >= firstIdLength) {
						expandIdMap.put(preId, preId);
					}
				}
				expandIdMap.put(expandIds[i], expandIds[i]);
			}
		}
		/* 如果checkIds参数不为空，创建临时map对象记录需要选中的节点ID，为后面json数据中设置checkstate做准备 */
		Map checkIdMap = new HashMap();
		if (null != checkIds && checkIds.length > 0) {
			for (int i = 0; i < checkIds.length; i++) {
				checkIdMap.put(checkIds[i], checkIds[i]);
			}
		}

		Map showCheckIdMap = null;
		if (null != showCheckIds) {
			showCheckIdMap = new HashMap();
			for (int i = 0; i < showCheckIds.length; i++) {
				showCheckIdMap.put(showCheckIds[i], showCheckIds[i]);
			}
		}
		/* 遍历节点数据 */
		for (int i = 0; i < dataList.size(); i++) {
			TreeNode node = (TreeNode) dataList.get(i);
			if (node.getId().length() == firstIdLength ) {
				node.setPid(rootNode.getId());
			} else {
				if (node.getId().length() > stepLength)
					node.setPid(node.getId().substring(0, (node.getId().length() - stepLength)));
			}
			// 把当前节点添加到父节点上
			if (null != node.getPid() && treeMap.containsKey(node.getPid())) {
				TreeNode t = (TreeNode) treeMap.get(node.getPid());
				t.getChildNodes().add(node);
			}
			treeMap.put(node.getId(), node);
		}
		/* 判断当前是否需要显示自定义根节点名称，rootName参数为null则不显示根节点名称 */
		if (null != rootName) {
			sb.append("{\"id\":\"" + stringFormat(rootNode.getId()) + "\",\"text\":\""
					+ stringFormat(rootName) + "\"");
			sb
					.append(",\"value\":\"" + stringFormat(rootNode.getValue())
							+ "\",\"showcheck\":true");
			sb.append(",\"isexpand\":true,\"checkstate\":0");
			sb.append(",\"hasChildren\":true,\"ChildNodes\":[");
			// 遍历子节点数据
			sb.append(getChildNode(rootNode.getChildNodes(), expandIdMap, checkIdMap, false,
					showCheckIdMap));
			sb.append("],\"complete\" : true}");
		} else {
			sb.append(getChildNode(rootNode.getChildNodes(), expandIdMap, checkIdMap, false,
					showCheckIdMap));
		}
		return sb.toString();
		//return sb.toString().replaceAll("/", "\\\\");
	}

	
	/*  
	 * 角色分配
	*/
	
	@SuppressWarnings("unchecked")
	public static String treeBuilder1(List dataList, int stepLength, String rootName,
			String[] expandIds, String checkIds[], String showCheckIds[]) {
		// 获取第一个节点id长度
		int firstIdLength = ((TreeNode) dataList.get(0)).getId().length();

		StringBuffer sb = new StringBuffer();

		/* 虚拟根节点，rootName参数为null时，该节点不显示，否则该节点显示名称为rootName */
		TreeNode rootNode = new TreeNode();
		rootNode.setId("-1");
		rootNode.setPid("-2");
		rootNode.setText("");
		rootNode.setValue("");
		rootNode.setHasChildren(true);
		Map treeMap = new HashMap();
		treeMap.put(rootNode.getId(), rootNode);

		/* 如果expandIds参数不为空，创建临时map对象记录需要展开的ID，为后面json数据中设置isexpand做准备 */
		Map expandIdMap = new HashMap();
		if (null != expandIds && expandIds.length > 0) {
			for (int i = 0; i < expandIds.length; i++) {
				String code = expandIds[i];
				// 获取当前节点级数
				int step = code.length() / stepLength;
				for (int j = 0; j < step; j++) {
					// 获取当前节点的上一级节点ID，并判断上一级节点是否存在
					String preId = expandIds[i]
							.substring(0, expandIds[i].length() - stepLength * j);
					if (preId.length() >= firstIdLength) {
						expandIdMap.put(preId, preId);
					}
				}
				expandIdMap.put(expandIds[i], expandIds[i]);
			}
		}
		/* 如果checkIds参数不为空，创建临时map对象记录需要选中的节点ID，为后面json数据中设置checkstate做准备 */
		Map checkIdMap = new HashMap();
		if (null != checkIds && checkIds.length > 0) {
			for (int i = 0; i < checkIds.length; i++) {
				checkIdMap.put(checkIds[i], checkIds[i]);
			}
		}

		Map showCheckIdMap = null;
		if (null != showCheckIds) {
			showCheckIdMap = new HashMap();
			for (int i = 0; i < showCheckIds.length; i++) {
				showCheckIdMap.put(showCheckIds[i], showCheckIds[i]);
			}
		}
		/* 遍历节点数据 */
		for (int i = 0; i < dataList.size(); i++) {
			TreeNode node = (TreeNode) dataList.get(i);
			if (node.getId().length() == firstIdLength || node.getId().length() == 5) {
				node.setPid(rootNode.getId());
			} else {
				if (node.getId().length() > stepLength)
					node.setPid(node.getId().substring(0, (node.getId().length() - stepLength)));
			}
			// 把当前节点添加到父节点上
			if (null != node.getPid() && treeMap.containsKey(node.getPid())) {
				TreeNode t = (TreeNode) treeMap.get(node.getPid());
				t.getChildNodes().add(node);
			}
			treeMap.put(node.getId(), node);
		}
		/* 判断当前是否需要显示自定义根节点名称，rootName参数为null则不显示根节点名称 */
		if (null != rootName) {
			sb.append("{\"id\":\"" + stringFormat(rootNode.getId()) + "\",\"text\":\""
					+ stringFormat(rootName) + "\"");
			sb
					.append(",\"value\":\"" + stringFormat(rootNode.getValue())
							+ "\",\"showcheck\":true");
			sb.append(",\"isexpand\":true,\"checkstate\":0");
			sb.append(",\"hasChildren\":true,\"ChildNodes\":[");
			// 遍历子节点数据
			sb.append(getChildNode(rootNode.getChildNodes(), expandIdMap, checkIdMap, false,
					showCheckIdMap));
			sb.append("],\"complete\" : true}");
		} else {
			sb.append(getChildNode(rootNode.getChildNodes(), expandIdMap, checkIdMap, false,
					showCheckIdMap));
		}
		return sb.toString();
		//return sb.toString().replaceAll("/", "\\\\");
	}
	
	
	/**
	 * 遍历子节点数据
	 * 
	 * @param list
	 * @return
	 */
	public static String getChildNode(List list, Map expandIdMap, Map checkIdMap, boolean ajax,
			Map showCheckIdMap) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			TreeNode node = (TreeNode) list.get(i);
			sb.append("{\"id\":\"" + stringFormat(node.getId()) + "\"");
			sb.append(",\"pid\":\"" + (node.getPid()) + "\"");
			
			sb.append(",\"value\":\"" + stringFormat(node.getValue()) + "\"");
			//判断当前节点是否需要显示复选框或者是否有权限点击
			if (null != showCheckIdMap) {
				if (showCheckIdMap.containsKey(node.getId())) {
					sb.append(",\"text\":\"" + stringFormat(node.getText()) + "\"");
					if (node.isShowCheck()) {
						sb.append(",\"showcheck\":true");
					} else {
						sb.append(",\"showcheck\":false");
					}
					sb.append(",\"enable\":\"Y\"");
				} else {
					sb.append(",\"text\":\"" + "<span style=color:gray>"+stringFormat(node.getText()) +"<span>"+ "\"");
					sb.append(",\"showcheck\":false");
					sb.append(",\"enable\":\"N\"");
				}
			} else {
				sb.append(",\"text\":\"" + stringFormat(node.getText()) + "\"");
				if (node.isShowCheck()) {
					sb.append(",\"showcheck\":true");
				} else {
					sb.append(",\"showcheck\":false");
				}
			}
			if (null != expandIdMap && expandIdMap.containsKey(node.getId())) {
				sb.append(",\"isexpand\":true");
			} else {
				sb.append(",\"isexpand\":" + node.isExpand());
			}
			if (null != checkIdMap && checkIdMap.containsKey(node.getId())) {
				sb.append(",\"checkstate\":1");
			} else {
				sb.append(",\"checkstate\":0");
			}

			if (node.isHasChildren()) {
				sb.append(",\"hasChildren\":true,");
				if (ajax) {
					sb.append("\"ChildNodes\":[{}],");
				} else {
					sb.append("\"ChildNodes\":[");
					sb.append(getChildNode(node.getChildNodes(), expandIdMap, checkIdMap, ajax,
							showCheckIdMap));
					sb.append("],");
				}
				// 递归遍历子节点数据

			} else {
				sb.append(",\"hasChildren\":false,");
			}

			sb.append("\"url\":\"" + stringFormat(node.getUrl()) + "\"");
			sb.append(",\"childNum\":\"" + stringFormat(node.getChildNum()) + "\"");
			sb.append(",\"val1\":\"" + stringFormat(node.getVal1()) + "\"");
			sb.append(",\"val2\":\"" + stringFormat(node.getVal2()) + "\"");
			sb.append(",\"val3\":\"" + stringFormat(node.getVal3()) + "\"");
			sb.append(",\"val4\":\"" + stringFormat(node.getVal4()) + "\"");
			sb.append(",\"val5\":\"" + stringFormat(node.getVal5()) + "\"");
			sb.append(",\"val6\":\"" + stringFormat(node.getVal6()) + "\"");
			sb.append(",\"val7\":\"" + stringFormat(node.getVal7()) + "\"");
			sb.append(",\"textVal\":\"" + stringFormat(node.getText()) + "\"");
			if (ajax) {
				sb.append(",\"complete\" : false}");
			} else {
				sb.append(",\"complete\" : true}");
			}

			if (i != list.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 根据树结构数据组织json数据,当前方法默认为异步加载
	 * 
	 * @param dataList
	 *            树结构数据
	 * @param preId
	 *            需要显示的节点的父ID，第一次初始化时可以指定为-1，以后为当前点击节点的ID
	 * @return
	 */
	public static String ajaxBuilderTree(List dataList, String preId) {
		dataList = sort(dataList);
		StringBuffer sb = new StringBuffer();

		/* 虚拟根节点，rootName参数为null时，该节点不显示，否则该节点显示名称为rootName */
		TreeNode rootNode = new TreeNode();
		rootNode.setId(preId);
		rootNode.setPid("-2");
		rootNode.setText("");
		rootNode.setValue("");
		rootNode.setHasChildren(true);
		Map treeMap = new HashMap();
		treeMap.put(rootNode.getId(), rootNode);

		/* 遍历节点数据 */
		for (int i = 0; i < dataList.size(); i++) {
			TreeNode node = (TreeNode) dataList.get(i);
			if (null != node.getPid() && treeMap.containsKey(node.getPid())) {
				TreeNode t = (TreeNode) treeMap.get(node.getPid());
				t.getChildNodes().add(node);
			}
			treeMap.put(node.getId(), node);
		}
		sb.append(getChildNode(rootNode.getChildNodes(), null, null, true, null));
		return sb.toString().replaceAll("/", "\\\\");

	}

	/**
	 * 根据树结构数据组织json数据,当前方法默认为一次性加载
	 * 
	 * @param dataList
	 *            树结构数据(当前数据中的对象的pid不能为空且为当前节点的实际pid，传入数据可以随意排序，程序自动完成树结构排序)
	 * @param rootName
	 *            自定义根节点名称,当为null时不显示
	 * @param expandIds
	 *            展开节点，当为null时不展开
	 * @param checkIds
	 *            默认选中节点，当为null时不选中
	 * @return
	 */
	public static String builderTree(List dataList, String rootName, String[] expandIds,
			String checkIds[]) {
		dataList = sort(dataList);
		StringBuffer sb = new StringBuffer();

		/* 虚拟根节点，rootName参数为null时，该节点不显示，否则该节点显示名称为rootName */
		TreeNode rootNode = new TreeNode();
		rootNode.setId("-1");
		if (rootName == null) {
			TreeNode firstTreeNode = (TreeNode) dataList.get(0);
			if (firstTreeNode.getPid() != null) {
				rootNode.setId(firstTreeNode.getPid());
			}
		}
		rootNode.setPid("-2");
		rootNode.setText("");
		rootNode.setValue("");
		rootNode.setHasChildren(true);
		Map treeMap = new HashMap();
		treeMap.put(rootNode.getId(), rootNode);

		/* 如果checkIds参数不为空，创建临时map对象记录需要选中的节点ID，为后面json数据中设置checkstate做准备 */
		Map checkIdMap = new HashMap();
		if (null != checkIds && checkIds.length > 0) {
			for (int i = 0; i < checkIds.length; i++) {
				checkIdMap.put(checkIds[i], checkIds[i]);
			}
		}

		/* 遍历节点数据 */
		for (int i = 0; i < dataList.size(); i++) {
			TreeNode node = (TreeNode) dataList.get(i);
			if (null != node.getPid() && treeMap.containsKey(node.getPid())) {
				TreeNode t = (TreeNode) treeMap.get(node.getPid());
				t.getChildNodes().add(node);
			}
			treeMap.put(node.getId(), node);
		}

		/* 如果expandIds参数不为空，创建临时map对象记录需要展开的ID，为后面json数据中设置isexpand做准备 */
		Map expandIdMap = new HashMap();
		if (null != expandIds && expandIds.length > 0) {
			for (int i = 0; i < expandIds.length; i++) {
				String[] idArray = getExpandId(treeMap, expandIds[i]).split(",");
				for (int j = 0; j < idArray.length; j++) {
					if (null != idArray[j] && !"".equals(idArray[j])) {
						expandIdMap.put(idArray[j], idArray[j]);
					}
				}
			}
		}

		/* 判断当前是否需要显示自定义根节点名称，rootName参数为null则不显示根节点名称 */
		if (null != rootName) {
			sb.append("{\"id\":\"" + rootNode.getId() + "\",\"text\":\"" + rootName + "\"");
			sb.append(",\"value\":\"" + rootNode.getValue() + "\",\"showcheck\":false");
			sb.append(",\"isexpand\":true,\"checkstate\":0");
			sb.append(",\"hasChildren\":true,\"ChildNodes\":[");
			// 遍历子节点数据
			sb.append(getChildNode(rootNode.getChildNodes(), expandIdMap, checkIdMap, false, null));
			sb.append("],\"complete\" : true}");
		} else {
			sb.append(getChildNode(rootNode.getChildNodes(), expandIdMap, checkIdMap, false, null));
		}
		return sb.toString().replaceAll("/", "\\\\");
	}

	/**
	 * 获取当前需要展开节点以及上级节点(递推到根节点)组合字符串，以逗号分隔
	 * 
	 * @param treeMap
	 * @param currentId
	 * @return
	 */
	private static String getExpandId(Map treeMap, String currentId) {
		StringBuffer sb = new StringBuffer();
		if (null != currentId && treeMap.containsKey(currentId)) {
			TreeNode t = (TreeNode) treeMap.get(currentId);
			String pid = t.getPid();
			sb.append(t.getId() + ",");
			sb.append(getExpandId(treeMap, pid));// 递归调用
		}
		return sb.toString();
	}

	/**
	 * 自定义tree node 数据按照id自增排序，
	 * 
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List sort(List dataList) {
		TreeCompare compare = new TreeCompare();
		Collections.sort(dataList, compare);
		return dataList;
	}

	/**
	 * 对字符串特殊字符进行处理
	 * 
	 * @param s
	 * @return
	 */
	public static String stringFormat(String s) {
		if (null == s) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			// case '\\': //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
			// sb.append("\\\\");
			// break;
			case '\\':
				sb.append("");
			case '/':
				sb.append("\\/");
				break;
			case '\b': // 退格
				sb.append("\\b");
				break;
			case '\f': // 走纸换页
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n"); // 换行
				break;
			case '\r': // 回车
				sb.append("\\r");
				break;
			case '\t': // 横向跳格
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

	}
}
