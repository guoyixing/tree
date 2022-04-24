# 父子结构转树状结构的工具
将数据库中的父子结构（id和parentId）的数据转成树状结构
方便的查询树中的节点，某个几节点的所有父级等

# 使用方法
1. 将需要的树结构化的类添加TreeI接口
    ```java
    public class Menu implements TreeI {
        private String id;
        private String parentId;
        private String name;
    
        /* 省略getter、setter */
    
        @Override
        public String getTreeId() {
            return getId();
        }
   
        @Override
        public String getTreeParentId() {
            return getParentId();
        }
   
        @Override
        public String getTreeName() {
            return getName();
        }
    }
    ```
2. 生成树结构
   ```java
    public class Test {
        public static void main(String[] args) {
            List<Menu> menuList = new ArrayList<>();
    
            menuList.add(new Menu("1", "0", "一级菜单1"));
            menuList.add(new Menu("2","0","一级菜单2"));
            menuList.add(new Menu("1-1","1","二级菜单1"));
            menuList.add(new Menu("1-2","2","二级菜单2"));
            menuList.add(new Menu("1-1-1","1-1","三级级菜单1"));
            menuList.add(new Menu("1-1-2","1-1","三级级菜单2"));
            
            //树结构
            List<Tree<Menu>> trees = new TreeUtils<>(menuList).buildForest();
        }
    }
   ```

3. 效果
    ```json
    [
        {
            "id": "1",
            "parentId": "0",
            "name": "一级菜单1"
        },
        {
            "id": "2",
            "parentId": "0",
            "name": "一级菜单2"
        },
        {
            "id": "1-1",
            "parentId": "0",
            "name": "二级菜单1"
        },
        {
            "id": "1-2",
            "parentId": "0",
            "name": "二级菜单2"
        },
        {
            "id": "1-1-1",
            "parentId": "0",
            "name": "三级级菜单1"
        },
        {
            "id": "1-2-2",
            "parentId": "0",
            "name": "三级级菜单2"
        }
    ]
    ```
    转成
    ```json
    [
        {
            "id": "1",
            "name": "一级菜单1",
            "nodeData": {
                "id": "1",
                "parentId": "0",
                "name": "一级菜单1"
            },
            "total": 1,
            "parent": [],
            "children": [
                {
                    "id": "1-1",
                    "name": "二级菜单1",
                    "nodeData": {
                        "id": "1-1",
                        "parentId": "1",
                        "name": "二级菜单1"
                    },
                    "total": 2,
                    "parent": [
                        {
                            "id": "1",
                            "parentId": "0",
                            "name": "一级菜单1"
                        }
                    ],
                    "children": [
                        {
                            "id": "1-1-1",
                            "name": "三级级菜单1",
                            "nodeData": {
                                "id": "1-1-1",
                                "parentId": "1-1",
                                "name": "三级级菜单1"
                            },
                            "total": 0,
                            "parent": [
                                {
                                    "id": "1",
                                    "parentId": "0",
                                    "name": "一级菜单1"
                                },
                                {
                                    "id": "1-1",
                                    "parentId": "1",
                                    "name": "二级菜单1"
                                }
                            ],
                            "children": [],
                            "level": 3
                        },
                        {
                            "id": "1-1-2",
                            "name": "三级级菜单2",
                            "nodeData": {
                                "id": "1-1-2",
                                "parentId": "1-1",
                                "name": "三级级菜单2"
                            },
                            "total": 0,
                            "parent": [
                                {
                                    "id": "1",
                                    "parentId": "0",
                                    "name": "一级菜单1"
                                },
                                {
                                    "id": "1-1",
                                    "parentId": "1",
                                    "name": "二级菜单1"
                                }
                            ],
                            "children": [],
                            "level": 3
                        }
                    ],
                    "level": 2
                }
            ],
            "level": 1
        },
        {
            "id": "2",
            "name": "一级菜单2",
            "nodeData": {
                "id": "2",
                "parentId": "0",
                "name": "一级菜单2"
            },
            "total": 1,
            "parent": [],
            "children": [
                {
                    "id": "1-2",
                    "name": "二级菜单2",
                    "nodeData": {
                        "id": "1-2",
                        "parentId": "2",
                        "name": "二级菜单2"
                    },
                    "total": 0,
                    "parent": [
                        {
                            "id": "2",
                            "parentId": "0",
                            "name": "一级菜单2"
                        }
                    ],
                    "children": [],
                    "level": 2
                }
            ],
            "level": 1
        }
    ]
    ```
   
# 提供的一些工具方法
## 1、根据id查找树中的节点
```java
List<Tree<Menu>> trees = new TreeUtils<>(menuList).buildForest();

Tree<Menu> node = TreeUtils.getNode(trees, "1");
```

## 2、获取节点中的数据
```java
List<Tree<Menu>> trees = new TreeUtils<>(menuList).buildForest();

Tree<Menu> node = TreeUtils.getNode(trees, "1");

Menu nodeData = node.getNodeData();
```

## 3、获取某个节点的所有父节点
```java
List<Tree<Menu>> trees = new TreeUtils<>(menuList).buildForest();

Tree<Menu> node = TreeUtils.getNode(trees, "1");

List<Menu> parent = node.getParent();
```

## 4、获取某个节点的子树
```java
List<Tree<Menu>> trees = new TreeUtils<>(menuList).buildForest();

Tree<Menu> node = TreeUtils.getNode(trees, "1");

List<Tree<Menu>> children = node.getChildren();
```

## 5、获取所有的叶子节点
```java
List<Tree<Menu>> trees = new TreeUtils<>(menuList).buildForest();

List<Tree<Menu>> allLeaf = TreeUtils.getAllLeaf(trees);
```

## 6、将树结构转成列表
```java
List<Tree<Menu>> trees = new TreeUtils<>(menuList).buildForest();

List<Tree<Menu>> treeList = TreeUtils.toList(trees);
```

## 7、生成树的时候进行排序
```java
List<Tree<Menu>> trees = new TreeUtils<>(menuList)
        .setSort(Comparator.comparing(t1 -> t1.getNodeData().getSortOrder()))
        .buildForest();
```