package ir.mohammadi.taskmanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Task implements Serializable {
    @SerializedName("userID")
    private String user_id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("_id")
    private String id;

    public Task() {

    }
    public Task(String user_id, String name, String description,String id) {
        this.user_id = user_id;
        this.name = name;
        this.description = description;
        this.id=id;

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {this.user_id = user_id;
    }
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
