package io.github.guoyixing.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构
 *
 * @author 敲代码的旺财
 * @date 2021/3/24 10:55
 */
public class Tree<T extends TreeI> {
    /**
     * 参数的id，value的id
     */
    private String id;

    /**
     * 树节点的名称，一般使用value中的名称
     */
    private String name;

    /**
     * 节点存放的数据
     */
    private T nodeData;

    /**
     * 包含的子节点数量
     */
    private Integer total;

    /**
     * 父节点节点
     */
    private List<T> parent;
    /**
     * 子节点
     */
    private List<Tree<T>> children;
    /**
     * 当前树深度，从1开始
     */
    private Integer level;

    public Tree() {
    }

    public Tree(T t, List<Tree<T>> children, List<T> parent, Integer level) {
        this.id = t.getTreeId();
        this.name = t.getTreeName();
        this.nodeData = t;
        this.total = children.size();
        this.children = children;
        this.parent = parent;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getNodeData() {
        return nodeData;
    }

    public void setNodeData(T nodeData) {
        this.nodeData = nodeData;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<T> getParent() {
        return new ArrayList<>(parent);
    }

    public void setParent(List<T> parent) {
        this.parent = parent;
    }
}
