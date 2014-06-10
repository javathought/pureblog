package controllers;

import models.Post;
import models.Setting;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.Before;

import java.util.List;

public class Application extends Controller {

    @Before
    public static void load() {
        renderArgs.put("setting",Setting.find("order by id").first());
    }

    public static void index() {
        Post frontPost = Post.find("order by postedAt desc").first();
        List<Post> olderPosts = Post.find("order by postedAt desc").from(1).fetch(10);
        render(frontPost, olderPosts);
    }

    public static void show(Long id) {
        Post post = Post.findById(id);
        render(post);
    }

    public static void postComment(Long postId, @Required String author, @Required String content) {
        Post post = Post.findById(postId);
        if (validation.hasErrors()) {
            render("Application/show.html", post);
        }
        post.addComment(author, content);
        flash.success("Thanks for posting %s", author);
        show(postId);
    }

}