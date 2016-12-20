package com.bestread.app.bestread;

import android.test.suitebuilder.annotation.LargeTest;

import com.goodreads.api.v1.Author;
import com.goodreads.api.v1.Following;
import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Update;
import com.goodreads.api.v1.Work;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class AuthorTest extends TestCase {
    private SessionManager session;

    public AuthorTest() {
        session = new SessionManager();
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret = "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token, tokenSecret);
    }

    //Test author search
    public void test0AuthorSearch() throws Exception {
        String query = "Stephen King";
        List<Work> works = session.g.search(query).getResults();

        Author author = works.get(0).getBestBook().getAuthor();
        assertEquals("Wrong author name", author.getName(), "Stephen King");
    }

    //Test author details
    public void test1Authorinfo() throws Exception {
        Author author = session.g.getAuthorById("3389");

        assertEquals(author.getName(),"Stephen King");
        assertEquals(author.getGender(),"male");
        assertEquals(author.getBooks().get(0).getTitle(),"The Shining");
    }
    //Test author follow
    public void test2AuthorFollow() throws Exception {
        Author author = session.g.getAuthorById("3389");

        boolean follow = session.g.followAuthor(author.getId()+"");
        assertTrue(follow);
    }

    //Test following already followd author
    public void test3AuthorAlreadyFollowed() throws Exception {
        Author author = session.g.getAuthorById("3389");

        boolean follow = session.g.followAuthor(author.getId()+"");
        assertFalse(follow);
    }

    //Test followed author add to following list
    public void test4AuthorFollowing() throws Exception {
        Author author = session.g.getAuthorById("3389");
        boolean isfollowing = false;
        Following follows = session.g.getFollowing(session.g.getAuthorizedUser().getId());

        for (int i = 0;i<follows.getFollowing().size();i++){
            if(follows.getFollowing().get(i).getId().equals(author.getUserId())){
                isfollowing = true;
                break;
            }
        }
        assertTrue(isfollowing);
    }

    //Test author unfollow
    public void test5AuthorUnfollow() throws Exception {
        Author author = session.g.getAuthorById("3389");
        boolean follow = session.g.unfollowUser(author.getUserId() + "");;
        assertTrue(follow);
    }
}
