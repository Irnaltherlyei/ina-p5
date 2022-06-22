package ina.p5.Beans;

import javax.persistence.*;

@Entity
@NamedQuery(name = "User.byName", query = "SELECT u FROM UserEntity u where u.username= ?1")
@NamedQuery(name = "User.isMatching", query = "SELECT u FROM UserEntity u where  u.username= ?1 and  u.password=?2")
@Table(name = "user", schema = "inadb", catalog = "")
public class UserEntity {
    @Id
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
