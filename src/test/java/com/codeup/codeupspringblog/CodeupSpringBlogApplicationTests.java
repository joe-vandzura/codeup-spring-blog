package com.codeup.codeupspringblog;


import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeupSpringBlogApplication.class)
@AutoConfigureMockMvc
public class CodeupSpringBlogApplicationTests {

	private User testUser;
	private HttpSession httpSession;

	@Autowired
	private MockMvc mvc;

	@Autowired
	UserRepository userDao;

	@Autowired
	PostRepository postsDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Before
	public void setup() throws Exception {

		testUser = userDao.findByUsername("testUser");

		// Creates the test user if not exists
		if(testUser == null){
			User newUser = new User();
			newUser.setUsername("testUser");
			newUser.setPassword(passwordEncoder.encode("pass"));
			newUser.setEmail("testUser@codeup.com");
			testUser = userDao.save(newUser);
		}

		// Throws a Post request to /login and expect a redirection to the Posts index page after being logged in
		httpSession = this.mvc.perform(MockMvcRequestBuilders.post("/login").with(csrf())
						.param("username", "testUser")
						.param("password", "pass"))
				.andExpect(status().is(HttpStatus.FOUND.value()))
				.andExpect(redirectedUrl("/posts"))
				.andReturn()
				.getRequest()
				.getSession();
	}

	@Test
	public void contextLoads() {
		// Sanity Test, just to make sure the MVC bean is working
		Assert.assertNotNull(mvc);
	}

	@Test
	public void testIfUserSessionIsActive() throws Exception {
		// It makes sure the returned session is not null
		Assert.assertNotNull(httpSession);
	}

	@Test
	public void testCreatePost() throws Exception {
		// Makes a Post request to /posts/create and expect a redirection to the Post
		this.mvc.perform(
						MockMvcRequestBuilders.post("/posts/create").with(csrf())
								.session((MockHttpSession) httpSession)
								// Add all the required parameters to your request like this
								.param("title", "test")
								.param("body", "for sale"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void testShowPost() throws Exception {

		Post existingPost = postsDao.findAll().get(0);

		// Makes a Get request to /ads/{id} and expect a redirection to the Ad show page
		this.mvc.perform(MockMvcRequestBuilders.get("/posts/" + existingPost.getId()).with(csrf()).session((MockHttpSession) httpSession))
				.andExpect(status().isOk())
				// Test the dynamic content of the page
				.andExpect(MockMvcResultMatchers.content().string(containsString(existingPost.getBody())));
	}

	@Test
	public void testPostsIndex() throws Exception {
		Post existingPost = postsDao.findAll().get(0);
		System.out.println("HERE");
		// Makes a Get request to /ads and verifies that we get some of the static text of the ads/index.html template and at least the title from the first Ad is present in the template.
		this.mvc.perform(MockMvcRequestBuilders.get("/posts"))
				.andExpect(status().isOk())
				// Test the static content of the page
				.andExpect(MockMvcResultMatchers.content().string(containsString("Here are all the posts")))
				// Test the dynamic content of the page
				.andExpect(MockMvcResultMatchers.content().string(containsString(existingPost.getTitle())));
	}

	@Test
	public void testEditPost() throws Exception {
		// Gets the first Post for tests purposes
		Post existingPost = postsDao.findAll().get(0);

		// Makes a Post request to /posts/{id}/edit and expect a redirection to the Post show page
		this.mvc.perform(
						MockMvcRequestBuilders.post("/posts/" + existingPost.getId() + "/edit").with(csrf())
								.session((MockHttpSession) httpSession)
								.param("id", "3")
								.param("title", "edited title")
								.param("body", "edited description"))
				.andExpect(status().is3xxRedirection());

		// Makes a GET request to /posts/{id} and expect a redirection to the Post show page
		this.mvc.perform(MockMvcRequestBuilders.get("/posts/" + existingPost.getId()).with(csrf()).session((MockHttpSession) httpSession))
				.andExpect(status().isOk())
				// Test the dynamic content of the page
				.andExpect(MockMvcResultMatchers.content().string(containsString("edited title")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("edited description")));
	}


}

