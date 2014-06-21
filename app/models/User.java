package models;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Pascal
 * Date: 16/05/14
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="pb_user")
public class User extends Model{
    @Email
    @Required
    public String email;
    @Required
    public String password;
    public String fullname;
    public boolean isAdmin;
    public String avatar;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = hash(password);
        this.fullname = fullname;
    }

    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, hash(password)).first();
    }

    public static String hash(String password) {
        MessageDigest sha1;
        try {
            sha1 = MessageDigest.getInstance("SHA1");
            sha1.update(password.getBytes());
            BigInteger hash = new BigInteger(1, sha1.digest());
            return hash.toString(16).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return password;
        }

    }

    public String toString() {
        return fullname;
    }
}
