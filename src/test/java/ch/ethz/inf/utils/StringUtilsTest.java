package ch.ethz.inf.utils;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringUtilsTest {
	@Test
	public void isNullOrEmptyTest() {
		assertTrue(StringUtils.isNullOrEmpty(null));
		assertTrue(StringUtils.isNullOrEmpty(""));
		assertFalse(StringUtils.isNullOrEmpty("asd //"));
		assertFalse(StringUtils.isNullOrEmpty("  asd das asd"));
		assertFalse(StringUtils.isNullOrEmpty(" asdg   "));
	}
}
