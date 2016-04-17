package com.example.kadi.countryinfo;

/**
 * Created by Kadi on 05/04/2016.
 */
public class User {
    private String username;
    private String mdp;

    public User(String username, String mdp) {
        this.username = username;
        this.mdp = mdp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", mdp='" + mdp + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        return !(mdp != null ? !mdp.equals(user.mdp) : user.mdp != null);

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (mdp != null ? mdp.hashCode() : 0);
        return result;
    }
}
