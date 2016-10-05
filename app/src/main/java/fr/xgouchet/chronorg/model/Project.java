package fr.xgouchet.chronorg.model;

import android.support.annotation.Nullable;

/**
 * @author Xavier Gouchet
 */

public class Project {

    private int id;
    @Nullable private String name;
    @Nullable private String description;

    public Project() {
        id = -1;
        name = null;
        description = null;
    }

    public Project(@Nullable String name, @Nullable String description) {
        id = -1;
        this.name = name;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable public String getName() {
        return name;
    }

    @Nullable public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
