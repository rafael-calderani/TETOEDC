package br.com.calderani.rafael.tetoedc.model;

/**
 * Created by Rafael on 09/07/2017.
 */

public class User {
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String login;
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

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

    private String communityId;
    public String getCommunityId() { return communityId; }
    public void setCommunityId(String communityId) { this.communityId = communityId; }
}
