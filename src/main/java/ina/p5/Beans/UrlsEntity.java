package ina.p5.Beans;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@NamedQuery(name = "Urls.deleteByUser", query = "DELETE FROM UrlsEntity u where u.username= ?1")
@NamedQuery(name = "Urls.byName", query = "SELECT u FROM UrlsEntity u where u.url= ?1")
@NamedQuery(name = "Urls.getAllByUsername", query = "SELECT u FROM UrlsEntity u where u.username= ?1")
@Table(name = "urls", schema = "inadb", catalog = "")
public class UrlsEntity {
    @Basic
    @Column(name = "username")
    @NotBlank()
    @Size(min = 3, max = 16)
    private String username;
    @Id
    @NotBlank(message = "Please enter an URL!")
    @Pattern(regexp = "^((http|https)://)?(www.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$", message = "URL Pattern is wrong!")
    @Column(name = "url")
    private String url;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlsEntity that = (UrlsEntity) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
