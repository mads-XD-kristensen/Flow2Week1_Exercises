package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person"),
    @NamedQuery(name = "Person.getAll", query = "SELECT p FROM Person p")

})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName, lastName, phone;

    @Temporal(TemporalType.DATE)
    private Date created;

    @Temporal(TemporalType.DATE)
    private Date lastEdited;

    public Person() {
    }

    public Person(String firstName, String lastName, String phone, Date created, Date lastEdited) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.created = created;
        this.lastEdited = lastEdited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited() {
        this.lastEdited = new Date();
    }

}
