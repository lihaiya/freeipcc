package com.sxx.jcc.core.utils.tree;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZTreeUtil {

	public static String treeBuilder(List dataList, int stepLength, String iconSkin) {
		return treeBuilder(dataList, stepLength, null, null, null, iconSkin);
	}

	public static String treeBuilder(List dataList, int stepLength, String rootName,
			String[] expandIds, String checkIds[], String iconSkin) {
		return treeBuilder(dataList, stepLength, null, expandIds, checkIds, iconSkin, true);
	}

	public static String treeBuilder(List dataList, int stepLength, String rootName,
			String[] expandIds, String checkIds[], String iconSkin, boolean viewEmptyFolder) {
		return treeBuilder(dataList, stepLength, null, expandIds, checkIds, iconSkin, viewEmptyFolder, null);
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
			String[] expandIds, String checkIds[], String iconSkin, boolean viewEmptyFolder,
			String showCheckIds[]) {
		// 获取第一个节点id长度
		int firstIdLength = ((ZTreeNode) dataList.get(0)).getId().length();

		StringBuffer sb = new StringBuffer();

		/* 虚拟根节点，rootName参数为null时，该节点不显示，否则该节点显示名称为rootName */
		ZTreeNode rootNode = new ZTreeNode();
		rootNode.setId("-1");
		rootNode.setPid("-2");
		rootNode.setName(rootName);
		rootNode.setValue("");
		rootNode.setParent(true);
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
		if (null != showCheckIds && showCheckIds.length > 0) {
			showCheckIdMap = new HashMap();
			for (int i = 0; i < showCheckIds.length; i++) {
				showCheckIdMap.put(showCheckIds[i], showCheckIds[i]);
			}
		}
		/* 遍历节点数据 */
		for (int i = 0; i < dataList.size(); i++) {
			ZTreeNode node = (ZTreeNode) dataList.get(i);
			if (node.getId().length() == firstIdLength) {
				node.setPid(rootNode.getId());
			} else {
				node.setPid(node.getId().substring(0, (node.getId().length() - stepLength)));
			}
			// 把当前节点添加到父节点上
			if (null != node.getPid() && treeMap.containsKey(node.getPid())) {
				ZTreeNode t = (ZTreeNode) treeMap.get(node.getPid());
				t.getChildNodes().add(node);
			}
			treeMap.put(node.getId(), node);
		}
		/* 判断当前是否需要显示自定义根节点名称，rootName参数为null则不显示根节点名称 */
		if (null != rootName) {
			sb.append("{\"id\":\"" + stringFormat(rootNode.getId()) + "\",\"name\":\""
					+ stringFormat(rootName) + "\"");
			sb
					.append(",\"value\":\"" + stringFormat(rootNode.getValue())
							+ "\",\"showcheck\":true");
			sb.append(",\"isexpand\":true,\"checkstate\":0");
			sb.append(",\"isexpand\":\"" + iconSkin + "\"");
			sb.append(",\"hasChildren\":true,\"children\":[");
			// 遍历子节点数据
			sb.append(getChildNode(rootNode.getChildNodes(), expandIdMap, checkIdMap, false,
					iconSkin, viewEmptyFolder, showCheckIdMap));
			sb.append("],\"complete\" : true}");
		} else {
			sb.append(getChildNode(rootNode.getChildNodes(), expandIdMap, checkIdMap, false,
					iconSkin, viewEmptyFolder, showCheckIdMap));
		}

		return sb.toString().replaceAll("/", "\\\\");
	}

	/**
	 * 遍历子节点数据
	 * 
	 * @param list
	 * @return
	 */
	private static String getChildNode(List list, Map expandIdMap, Map checkIdMap, boolean ajax,
			String iconSkin, boolean viewEmptyFolder, Map showCheckIdMap) {
		StringBuffer sb = new StringBuffer();
		list = sort(list);
		for (int i = 0; i < list.size(); i++) {
			ZTreeNode node = (ZTreeNode) list.get(i);
			if (node.isParent() == true && node.getChildNodes().size() == 0 && !viewEmptyFolder) {
				continue;
			}
			sb.append("{\"id\":\"" + stringFormat(node.getId()) + "\"");
			sb.append(",\"pid\":\"" + (node.getPid()) + "\"");
			sb.append(",\"name\":\"" + stringFormat(node.getName()) + "\"");
			sb.append(",\"value\":\"" + stringFormat(node.getValue()) + "\"");

			if (null != expandIdMap && expandIdMap.containsKey(node.getId())) {
				sb.append(",\"open\":true");
			} else {
				sb.append(",\"open\":" + node.isOpen());
			}
			if (null != checkIdMap && checkIdMap.containsKey(node.getId())) {
				sb.append(",\"checked\":true");
			} else {
				sb.append(",\"checked\":false");
			}
			if (null != showCheckIdMap) {
				if (showCheckIdMap.containsKey(node.getId())) {
					sb.append(",\"nocheck\":true");
				} else {
					sb.append(",\"nocheck\":false");
				}
			}
			if (node.getChildNodes().size() > 0) {
				node.setParent(true);
			}
			if (null != iconSkin && !"".equals(iconSkin)) {
				sb.append(",\"iconSkin\":\"" + iconSkin + "\"");
			}
			if (node.isParent()) {
				sb.append(",\"isParent\":true,");

				if (ajax) {
					sb.append("\"children\":[{}],");
				} else {
					sb.append("\"children\":[");
					sb.append(getChildNode(node.getChildNodes(), expandIdMap, checkIdMap, ajax,
							iconSkin, viewEmptyFolder, showCheckIdMap));
					sb.append("],");
				}
				// 递归遍历子节点数据

			} else {
				sb.append(",\"isParent\":false,");
			}

			sb.append("\"url\":\"" + stringFormat(node.getUrl()) + "\"");

			sb.append(",\"val1\":\"" + stringFormat(node.getVal1()) + "\"");
			sb.append(",\"val2\":\"" + stringFormat(node.getVal2()) + "\"");
			sb.append(",\"val3\":\"" + stringFormat(node.getVal3()) + "\"");
			sb.append(",\"val4\":\"" + stringFormat(node.getVal4()) + "\"");
			sb.append(",\"val5\":\"" + stringFormat(node.getVal5()) + "\"");
			sb.append(",\"val6\":\"" + stringFormat(node.getVal6()) + "\"");
			if (ajax) {
				sb.append(",\"complete\" : false},");
			} else {
				sb.append(",\"complete\" : true},");
			}

			// if (i != list.size() - 1) {
			// sb.append(",");
			// }
		}
		if (sb.toString().length() > 0) {
			return sb.toString().substring(0, sb.toString().length() - 1);
		} else {
			return "";
		}

	}

	/**
	 * 自定义tree node 数据按照id自增排序，
	 * 
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List sort(List dataList) {
		ZTreeCompare compare = new ZTreeCompare();
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
}
