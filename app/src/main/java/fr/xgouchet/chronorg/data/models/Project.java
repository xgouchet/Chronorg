package fr.xgouchet.chronorg.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */

public class Project implements Parcelable {

    private int id;
    @NonNull private String name;

    public Project() {
        id = -1;
        name = "‽";
    }

    public Project(@NonNull String name) {
        id = -1;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @NonNull public String getName() {
        return name;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (!name.equals(project.name)) return false;
        return true;

    }

    @Override public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override public String toString() {
        return "Project{" +
                "#" + id +
                " '" + name + '\'' +
                '}';
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    protected Project(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
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
