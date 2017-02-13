package co.in.dreamguys.cream.model;

/**
 * Created by user5 on 06-02-2017.
 */

public class ExpandedMenuModel {

    String iconName = "";
    int iconImg = -1; // menu icon resource id
    boolean groupPos;

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIconImg() {
        return iconImg;
    }

    public void setIconImg(int iconImg) {
        this.iconImg = iconImg;
    }

    public boolean getGroupPos() {
        return groupPos;
    }

    public void setGroupPos(boolean groupPos) {
        this.groupPos = groupPos;
    }
}
