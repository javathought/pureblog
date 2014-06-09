package models;

import play.db.jpa.Model;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: pascalabaziou
 * Date: 07/06/2014
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Setting extends Model {

    public String title;
    public String brand;
    public String tagline;

}
