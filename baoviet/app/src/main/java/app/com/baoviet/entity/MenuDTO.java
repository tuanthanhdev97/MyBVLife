package app.com.baoviet.entity;

import java.util.List;
import java.math.BigDecimal;

public class MenuDTO {
    private String menuId;
    private String menuName;
    private BigDecimal menuLevel;
    private BigDecimal roleId;
    private String menuIcon;
    private String menuPath;
    private String isActive;
    private BigDecimal menuPosition;
    private BigDecimal isPublic;
    private List<MenuDTO> children;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public BigDecimal getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(BigDecimal menuLevel) {
        this.menuLevel = menuLevel;
    }

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public BigDecimal getMenuPosition() {
        return menuPosition;
    }

    public void setMenuPosition(BigDecimal menuPosition) {
        this.menuPosition = menuPosition;
    }

    public BigDecimal getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(BigDecimal isPublic) {
        this.isPublic = isPublic;
    }

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }
}
