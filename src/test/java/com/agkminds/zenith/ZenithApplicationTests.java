package com.agkminds.zenith;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.agkminds.zenith.repository.ChatRepository;
import com.agkminds.zenith.repository.CommentRepository;
import com.agkminds.zenith.repository.MessageRepository;
import com.agkminds.zenith.repository.PostRepository;
import com.agkminds.zenith.repository.ReelRepository;
import com.agkminds.zenith.repository.StoryRepository;
import com.agkminds.zenith.repository.UserRepository;

@SpringBootTest(properties = {
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
class ZenithApplicationTests {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private ChatRepository chatRepository;

	@MockBean
	private CommentRepository commentRepository;

	@MockBean
	private MessageRepository messageRepository;

	@MockBean
	private PostRepository postRepository;

	@MockBean
	private ReelRepository reelRepository;

	@MockBean
	private StoryRepository storyRepository;

	@Test
	void contextLoads() {
	}

}
