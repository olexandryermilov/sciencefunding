package com.yermilov.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@DatabaseTable(tableName = "admin")
public class Admin {
    public static final String EMAIL_FIELD_NAME = "email";
    public static final String PASSWORD_FIELD_NAME = "password";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@DatabaseField(generatedId =true, columnName = "id")
    private Long id;
    @DatabaseField
    private String email;
    @DatabaseField
    private String password;

    public Admin(){}
    public Admin(String email, String password){
        this.email = email;
        this.password = password;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        Admin admin = (Admin) o;

        if (id != admin.id) return false;
        if (!email.equals(admin.email)) return false;
        return password.equals(admin.password);
    }

    @Override
    public int hashCode() {
        int result = id.intValue();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
