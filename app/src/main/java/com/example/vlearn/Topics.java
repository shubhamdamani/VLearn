package com.example.vlearn;

public class Topics {                           /*Topic structure class */


    String name = null;
    boolean selected = false;

    public Topics( String name, boolean selected) {
        super();

        this.name = name;
        this.selected = selected;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
