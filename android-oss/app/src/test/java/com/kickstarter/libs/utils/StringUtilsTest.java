package com.kickstarter.libs.utils;

import com.kickstarter.KSRobolectricTestCase;

import org.junit.Test;

public class StringUtilsTest extends KSRobolectricTestCase {

  @Test
  public void testIsEmail() {
    assertTrue(StringUtils.isEmail("hello@kickstarter.com"));
    assertFalse(StringUtils.isEmail("hello@kickstarter"));
  }

  @Test
  public void testIsEmpty() {
    assertTrue(StringUtils.isEmpty(""));
    assertTrue(StringUtils.isEmpty(" "));
    assertTrue(StringUtils.isEmpty("     "));
    assertTrue(StringUtils.isEmpty(null));
    assertFalse(StringUtils.isEmpty("a"));
    assertFalse(StringUtils.isEmpty(" a "));
  }

  @Test
  public void testIsPresent() {
    assertFalse(StringUtils.isPresent(""));
    assertFalse(StringUtils.isPresent(" "));
    assertFalse(StringUtils.isPresent("     "));
    assertFalse(StringUtils.isPresent(null));
    assertTrue(StringUtils.isPresent("a"));
    assertTrue(StringUtils.isPresent(" a "));
  }

  @Test
  public void testSentenceCase() {
    assertEquals("", StringUtils.sentenceCase(""));
    assertEquals("A", StringUtils.sentenceCase("a"));
    assertEquals("Apple", StringUtils.sentenceCase("APPLE"));
    assertEquals("Apple", StringUtils.sentenceCase("APple"));
    assertEquals("Apple", StringUtils.sentenceCase("apple"));
    assertEquals("Snapple apple", StringUtils.sentenceCase("Snapple Apple"));
    assertEquals("Snapple apple snapple", StringUtils.sentenceCase("Snapple Apple Snapple"));
  }

  @Test
  public void testTrim() {
    assertEquals("", StringUtils.trim(""));
    assertEquals("", StringUtils.trim(" "));
    assertEquals("A", StringUtils.trim(" A"));
    assertEquals("A", StringUtils.trim("A "));
    assertEquals("A", StringUtils.trim(" A "));
    assertEquals("", StringUtils.trim("\u00A0"));
    assertEquals("A", StringUtils.trim("\u00A0A"));
    assertEquals("A", StringUtils.trim("A\u00A0"));
    assertEquals("A", StringUtils.trim("\u00A0A\u00A0"));
    assertEquals("", StringUtils.trim("\u00A0 "));
    assertEquals("A", StringUtils.trim("\u00A0 A"));
    assertEquals("A", StringUtils.trim("A\u00A0 "));
    assertEquals("A", StringUtils.trim("\u00A0 A \u00A0"));
  }
}
