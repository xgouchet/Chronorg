package fr.xgouchet.chronorg.data.models;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Xavier Gouchet
 */
public class Timeline {

    @Retention(RetentionPolicy.CLASS)
    @IntDef(value = {Portal.Direction.FUTURE, Portal.Direction.PAST})
    public @interface Direction {
    }

    private int id;
    private long projectId;
    @NonNull private String name = "";
    private int parentId;
    private int portalId;
    @Direction private int direction;

    @Nullable private volatile Portal portal;
    @Nullable private volatile Timeline parent;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull public String getName() {
        return name;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setPortalId(int portalId) {
        this.portalId = portalId;
    }

    public int getPortalId() {
        return portalId;
    }

    public void setDirection(@Direction int direction) {
        this.direction = direction;
    }

    @Direction public int getDirection() {
        return direction;
    }

    public void setPortal(@NonNull Portal portal) {
        this.portal = portal;
    }

    @Nullable public Portal getPortal() {
        return portal;
    }

    public void setParent(@NonNull Timeline parent) {
        this.parent = parent;
    }

    @Nullable public Timeline getParent() {
        return parent;
    }
}
