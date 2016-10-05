package fr.xgouchet.chronorg.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * @author Xavier Gouchet
 */

public class Project implements Parcelable {

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

    public int getId() {
        return id;
    }

    @Nullable public String getName() {
        return name;
    }

    @Nullable public String getDescription() {
        return description;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;
        return description != null ? description.equals(project.description) : project.description == null;

    }

    @Override public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "Project{" +
                "#" + id +
                " '" + name + '\'' +
                " (" + description + ')' +
                '}';
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    protected Project(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        @Override public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
