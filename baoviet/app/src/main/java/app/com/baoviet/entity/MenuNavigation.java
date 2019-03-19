package app.com.baoviet.entity;

import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by macosmv on 7/18/18.
 */

public class MenuNavigation implements Serializable {
    private int imageLogo;
    private String title;
    private String keyTransfer;
    private List<MenuNavigation> listMenuChild;

    public int getImageLogo() {
        return imageLogo;
    }

    public void setImageLogo(int imageLogo) {
        this.imageLogo = imageLogo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyTransfer() {
        return keyTransfer;
    }

    public void setKeyTransfer(String keyTransfer) {
        this.keyTransfer = keyTransfer;
    }

    public List<MenuNavigation> getListMenuChild() {
        return listMenuChild;
    }

    public void setListMenuChild(List<MenuNavigation> listMenuChild) {
        this.listMenuChild = listMenuChild;
    }
}
