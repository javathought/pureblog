package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pascalabaziou
 * Date: 15/06/2014
 * Time: 19:21
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Tag extends Model implements Comparable<Tag> {
    public String name;

    private Tag(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public int compareTo(Tag otherTag) {
        return name.compareTo(otherTag.name);
    }

    public static Tag findOrCreateByName(String name) {
        Tag tag = Tag.find("byName", name).first();
        if(tag == null) {
            tag = new Tag(name);
        }
        return tag;
    }

    public static List<Map> getCloud() {
        List<Map> result = Tag.find(
                "select new map(t.name as tag, count(p.id) as pound) from Post p join p.tags as t group by t.name order by t.name"
        ).fetch();
        return result;
    }

}
