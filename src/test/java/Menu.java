import io.github.guoyixing.tree.TreeI;

/**
 * @author 敲代码的旺财
 * @date 2022/4/24 14:52
 */
public class Menu implements TreeI {

    private String id;

    private String parentId;

    private String name;

    private Long sortOrder;

    public Menu() {
    }

    public Menu(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

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
