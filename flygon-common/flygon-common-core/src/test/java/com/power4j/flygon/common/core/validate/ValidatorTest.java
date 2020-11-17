package com.power4j.flygon.common.core.validate;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import java.util.Set;

import static org.hibernate.validator.testutil.ConstraintViolationAssert.assertThat;
import static org.hibernate.validator.testutil.ConstraintViolationAssert.violationOf;

public class ValidatorTest {

	private Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Data
	public static class Account {

		@Email
		private String email;

	}

	@Test
	public void validateTest() {
		Account account1 = new Account();
		account1.setEmail(null);
		Set<ConstraintViolation<Account>> violations1 = validator.validate(account1);
		assertThat(violations1).isEmpty();

		Account account2 = new Account();
		account2.setEmail("powe4j@example.com");
		Set<ConstraintViolation<Account>> violations2 = validator.validate(account2);
		assertThat(violations2).isEmpty();

		Account account3 = new Account();
		account3.setEmail("not_a_email");
		Set<ConstraintViolation<Account>> violations3 = validator.validate(account3);
		assertThat(violations3).containsOnlyViolations(violationOf(Email.class).withProperty("email"));
	}

}