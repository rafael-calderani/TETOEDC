package br.com.calderani.rafael.tetoedc.model;

/**
 * Created by Rafael on 09/07/2017.
 */

public class User {
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String function;
    public String getFunction() { return function; }
    public void setFunction(String function) { this.function = function; }

    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    private String phone;
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    private String communityName;
    public String getCommunityName() { return communityName; }
    public void setCommunityName(String communityName) { this.communityName = communityName; }
}
