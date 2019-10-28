package com.abc.mango.admin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PasswordEncoderTest {

	@Test
	public void main() {
		String salt = "helloworld";
		PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
		String encode = encoderMd5.encode("test");
		log.info(encode);
		boolean passwordValid = encoderMd5.matches("1bd98ed329aebc7b2f89424b5a38926e", "test");
		log.info(""+passwordValid);
		
		passwordValid = encoderMd5.matches("083a8db3ff5b9b4ece3ef2bde03226c8", "test");
		log.info(""+passwordValid);

		PasswordEncoder encoderSha = new PasswordEncoder(salt, "SHA");
		String pass2 = encoderSha.encode("test");
		log.info(pass2);
		boolean passwordValid2 = encoderSha.matches("1bd98ed329aebc7b2f89424b5a38926e", "test");
		log.info(""+passwordValid2);
		passwordValid2 = encoderSha.matches("1e4346dcb54c1444e91668e04b8ca4f74f42958e", "test");
		log.info(""+passwordValid2);
	}
}
