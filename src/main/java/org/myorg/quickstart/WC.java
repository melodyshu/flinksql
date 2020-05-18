package org.myorg.quickstart;

import java.io.Serializable;

/**
 * @Author: huangzhimao
 * @Date: 2020-05-18
 * @Description:
 */
public class WC implements Serializable {
private String word;
private Long frequency;

  public WC() {
  }

  public WC(String word, Long frequency) {
    this.word = word;
    this.frequency = frequency;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public Long getFrequency() {
    return frequency;
  }

  public void setFrequency(Long frequency) {
    this.frequency = frequency;
  }
}
