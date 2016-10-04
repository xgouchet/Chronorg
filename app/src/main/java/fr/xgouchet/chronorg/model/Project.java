package fr.xgouchet.chronorg.model;

import android.support.annotation.Nullable;

/**
 * @author Xavier Gouchet
 */

public class Project {

    private int id;
    @Nullable private String name;
    @Nullable private String description;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
