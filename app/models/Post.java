package models;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.data.validation.MaxSize;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    @Required
    public String title;

    @Required
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    public DateTime postedAt;

    @Lob
    @Required
    @MaxSize(10000)
    public String content;

    @Required
    @ManyToOne
    public User author;

    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
    public List<Comment> comments;

    @ManyToMany(cascade=CascadeType.PERSIST)
    public Set<Tag> tags;

    public Post(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new DateTime();
        this.comments = new ArrayList<Comment>();
        this.tags = new TreeSet<Tag>();
    }

    public Post addComment(String author, String content) {
        Comment newComment = new Comment(this, author, content).save();
        this.comments.add(newComment);
        this.save();
        return this;
    }

    public Post previous() {
        return Post.find("postedAt < ? order by postedAt desc", postedAt).first();
    }

    public Post next() {
        return Post.find("postedAt > ? order by postedAt asc", postedAt).first();
    }

    public Post tagItWith(String name) {
        tags.add(Tag.findOrCreateByName(name));
        return this;
    }

    public static List<Post> findTaggedWith(String tag) {
        return Post.find(
                "select distinct p from Post p join p.tags as t where t.name = ?", tag
        ).fetch();
    }

    public static List<Post> findTaggedWith(String... tags) {
        return Post.find(
                "select distinct p from Post p join p.tags as t where t.name in (:tags) group by p.id, p.author, " +
                        "p.title, p.content,p.postedAt having count(t.id) = :size"
        ).bind("tags", tags).bind("size", tags.length).fetch();
    }

    public String toString() {
        return title;
    }
}
