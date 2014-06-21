package models;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: Pascal
 * Date: 18/05/14
 * Time: 02:21
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Comment extends Model{

    @Required
    public String author;

    @Required
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    public DateTime postedAt;

    @Lob
    @Required
    @MaxSize(10000)
    public String content;

    @Required
    @ManyToOne
    public Post post;

    public Comment(Post post, String author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.postedAt = new DateTime();
    }
}
