package org.blockface.virtualshop.util;

public class Numbers
{
  public static Integer ParseInteger(String s)
  {
    try
    {
      Integer i = Integer.valueOf(Integer.parseInt(s));
      if (i.intValue() > 0) return i;
    }
    catch (NumberFormatException ex)
    {
      return Integer.valueOf(-1);
    }

    return Integer.valueOf(-1);
  }

  public static Float ParseFloat(String s)
  {
    try
    {
      Float i = Float.valueOf(Float.parseFloat(s));
      if (i.floatValue() > 0.0F) return i;
    }
    catch (NumberFormatException ex)
    {
      return Float.valueOf(-1.0F);
    }

    return Float.valueOf(-1.0F);
  }
}