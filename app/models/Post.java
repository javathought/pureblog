package models;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pascal
 * Date: 16/05/14
 * Time: 01:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="pb_post")
public class Post extends Model{

    public String title;
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    public DateTime postedAt;

    @Lob
    public String content;

    @ManyToOne
    public User author;

    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
    public List<Comment> comments;

    public Post(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new DateTime();
        this.comments = new ArrayList<Comment>();
    }

    public Post addComment(String author, String content) {
        Comment newComment = new Comment(this, author, content).save();
        this.comments.add(newComment);
        this.save();
        return this;
    }

}
