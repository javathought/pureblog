package controllers;

import models.Post;
import models.Setting;
import play.Logger;
import play.cache.Cache;
import play.data.validation.Required;
import play.libs.Codec;
import play.libs.Images;
import play.mvc.Controller;
import play.mvc.Before;
import sun.util.logging.resources.logging;

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
        String randomID = Codec.UUID();
        Logger.info("id : [%s]", randomID);
        render(post, randomID);

    }

    public static void postComment(Long postId,
                                   @Required(message="Author is required") String author,
                                   @Required(message="A message is required") String content,
                                   @Required(message="Please type the code") String code,
                                   String randomID) {
        Post post = Post.findById(postId);
        validation.equals(
                code, Cache.get(randomID)
        ).message("Invalid code. Please type it again");
        if (validation.hasErrors()) {
            render("Application/show.html", post, randomID);
        }
        post.addComment(author, content);
        flash.success("Thanks for posting %s", author);
        Cache.delete(randomID);
        show(postId);
    }

    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#E4EAFD");
        Cache.set(id, code, "10mn");
        renderBinary(captcha);
    }

    public static void listTagged(String tag) {
        List<Post> posts = Post.findTaggedWith(tag);
        render(tag, posts);
    }

}