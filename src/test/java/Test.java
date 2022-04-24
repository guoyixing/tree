import io.github.guoyixing.tree.Tree;
import io.github.guoyixing.tree.TreeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 敲代码的旺财
 * @date 2022/4/24 14:51
 */
public class Test {

    public static void main(String[] args) {
        List<Menu> menuList = new ArrayList<>();

        menuList.add(new Menu("1", "0", "一级菜单1"));
        menuList.add(new Menu("2", "0", "一级菜单2"));
        menuList.add(new Menu("1-1", "1", "二级菜单1"));
        menuList.add(new Menu("1-2", "2", "二级菜单2"));
        menuList.add(new Menu("1-1-1", "1-1", "三级级菜单1"));
        menuList.add(new Menu("1-1-2", "1-1", "三级级菜单2"));

        List<Tree<Menu>> trees = new TreeUtils<>(menuList).buildForest();

        List<Tree<Menu>> allLeaf = TreeUtils.getAllLeaf(trees);

        List<Tree<Menu>> treeList = TreeUtils.toList(trees);

        Tree<Menu> node = TreeUtils.getNode(trees, "1");

        Menu nodeData = node.getNodeData();

        List<Menu> parent = node.getParent();

        List<Tree<Menu>> children = node.getChildren();
    }

}
