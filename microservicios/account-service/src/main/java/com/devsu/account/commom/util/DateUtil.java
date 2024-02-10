package com.devsu.account.commom.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class DateUtil {

  public static boolean validateDate(String date) {
    val valid = date.split("-");

    if (valid.length != 3) return true;

    try {
      val year = Integer.parseInt(valid[0]);
      val month = Integer.parseInt(valid[1]);
      val day = Integer.parseInt(valid[2]);

      if (year < 1900) return true;
      if (month < 1 || month > 12) return true;
      if (day < 1 || day > 31) return true;
    } catch (Exception e) {
      return true;
    }

    return false;
  }
}
