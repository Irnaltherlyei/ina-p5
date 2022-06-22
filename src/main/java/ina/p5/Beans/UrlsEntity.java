package ina.p5.Beans;

import javax.persistence.*;

@Entity
@Table(name = "urls", schema = "inadb", catalog = "")
public class UrlsEntity {
    @Basic
    @Column(name = "username")
    private String username;
    @Id
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
