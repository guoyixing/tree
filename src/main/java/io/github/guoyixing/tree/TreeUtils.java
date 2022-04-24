package io.github.guoyixing.tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 生成树的工具类
 *
 * @author 敲代码的旺财
 * @date 2021/3/24 11:02
 */
public class TreeUtils<T extends TreeI> {

    public final static String TREE_KEY = "tree";
    /**
     * 根据父级id分组后的信息
     */
    final Map<String, List<T>> parentIdGroup;
    /**
     * 需要生成树的数据信息
     */
    List<T> dataList;

    private Comparator<Tree<T>> sort = Comparator.comparing(Tree::getId);

    public TreeUtils(List<T> dataList) {
        this.dataList = dataList;
        this.parentIdGroup = dataList.stream().parallel()
                .filter(m -> m.getTreeParentId() != null && !"".equals(m.getTreeParentId()))
                .collect(Collectors.groupingBy(TreeI::getTreeParentId));
    }

    /**
     * 获取树的所有叶子节点
     *
     * @param trees 树
     */
    public static <R extends TreeI> List<Tree<R>> getAllLeaf(List<Tree<R>> trees) {
        List<Tree<R>> leafList = new ArrayList<>();

        Stack<Tree<R>> accessStack = new Stack<>();
        accessStack.addAll(trees);

        while (!accessStack.isEmpty()) {
            Tree<R> treeNode = accessStack.pop();
            if (treeNode.getChildren() == null || treeNode.getChildren().size() == 0) {
                leafList.add(treeNode);
            } else {
                accessStack.addAll(treeNode.getChildren());
            }
        }
        return leafList;
    }

    /**
     * 将树结构转成list结构
     *
     * @param trees 树
     */
    public static <R extends TreeI> List<Tree<R>> toList(List<Tree<R>> trees) {
        List<Tree<R>> list = new ArrayList<>();

        Stack<Tree<R>> accessStack = new Stack<>();
        accessStack.addAll(trees);

        while (!accessStack.isEmpty()) {
            Tree<R> treeNode = accessStack.pop();
            list.add(treeNode);
            accessStack.addAll(treeNode.getChildren());
        }
        return list;
    }

    /**
     * 获取指定树节点
     *
     * @param trees 树
     */
    public static <R extends TreeI> Tree<R> getNode(List<Tree<R>> trees, String nodeId) {
        Stack<Tree<R>> accessStack = new Stack<>();
        accessStack.addAll(trees);

        while (!accessStack.isEmpty()) {
            Tree<R> treeNode = accessStack.pop();
            if (nodeId.equals(treeNode.getId())) {
                return treeNode;
            } else {
                accessStack.addAll(treeNode.getChildren());
            }
        }
        throw new RuntimeException("无法查询到指定的树节点");
    }

    /**
     * 获取指定树中的所有节点
     *
     * @param trees 树
     */
    public static <R extends TreeI> List<R> getList(Tree<R> trees) {
        Stack<Tree<R>> accessStack = new Stack<>();
        accessStack.add(trees);
        List<R> treeNodeList = new ArrayList<>();

        while (!accessStack.isEmpty()) {
            Tree<R> treeNode = accessStack.pop();
            treeNodeList.add(treeNode.getNodeData());
            accessStack.addAll(treeNode.getChildren());
        }
        return treeNodeList;
    }

    /**
     * 创建森林
     */
    public List<Tree<T>> buildForest() {
        //获取深林的根节点
        List<String> treeIds = dataList.stream().map(TreeI::getTreeId).collect(Collectors.toList());
        final List<T> rootNode = dataList.stream()
                .filter(m -> m.getTreeParentId() == null || "".equals(m.getTreeParentId())
                        || !treeIds.contains(m.getTreeParentId()))
                .collect(Collectors.toList());

        //根节点逐一生成树
        List<Tree<T>> treeList = new ArrayList<>(rootNode.size());
        for (T data : rootNode) {
            int level = 1;
            List<T> parent = new ArrayList<>();
            treeList.add(buildTree(data, parent, level));
        }
        if (treeList.size() != 0) {
            treeList.sort(sort);
        }
        return treeList;
    }

    /**
     * 创建树
     *
     * @param data  数据
     * @param level 层级
     */
    private Tree<T> buildTree(T data, List<T> parent, Integer level) {
        //获取子节点
        final List<T> children = Optional.ofNullable(parentIdGroup.get(data.getTreeId())).orElse(new ArrayList<>());

        List<T> nextParent = new ArrayList<>(parent);
        nextParent.add(data);

        List<Tree<T>> childrenTree = new ArrayList<>(children.size());
        for (T child : children) {
            Integer nextLevel = level + 1;
            Tree<T> childTree = buildTree(child, nextParent, nextLevel);
            childrenTree.add(childTree);
        }
        if (childrenTree.size() != 0) {
            childrenTree.sort(sort);
        }
        //生成树节点
        return new Tree<>(data, childrenTree, parent, level);
    }

    public TreeUtils<T> setSort(Comparator<Tree<T>> sort) {
        this.sort = sort;
        return this;
    }
}
