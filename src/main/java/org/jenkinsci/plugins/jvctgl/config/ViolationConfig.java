package org.jenkinsci.plugins.jvctgl.config;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

import se.bjurr.violations.lib.reports.Parser;

public class ViolationConfig implements Serializable {
  private static final long serialVersionUID = 9009372864417543781L;

  private String pattern;
  /** @see Violation. */
  private Parser parser;
  /** @see Violation. */
  private String reporter;

  public ViolationConfig() {}

  @DataBoundConstructor
  public ViolationConfig(Parser parser, String pattern, String reporter) {
    this.parser = parser;
    this.pattern = pattern;
    this.reporter = reporter;
  }

  public String getPattern() {
    return this.pattern;
  }

  public Parser getParser() {
    return this.parser;
  }

  public String getReporter() {
    if (this.reporter == null) {
      return this.parser.name();
    }
    return reporter;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setParser(Parser parser) {
    this.parser = parser;
  }

  public void setReporter(String reporter) {
    this.reporter = reporter;
  }

  @Override
  public String toString() {
    return "ViolationConfig [pattern="
        + pattern
        + ", parser="
        + parser
        + ", reporter="
        + reporter
        + "]";
  }
}
