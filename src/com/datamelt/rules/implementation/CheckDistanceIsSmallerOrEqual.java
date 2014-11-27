/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * <p>Checks if the Levenshtein distance between two strings is smaller or equal than a given value.</p>
 * 
 * <p>
 * The first argument of a method is always the value of the field that one wants to check. The second argument is either another field 
 * or an expected (fixed) value to check against the first value.
 * </p>
 * <p>
 * Some methods may have additional arguments that can be passed to it.
 * </p>
 * 
 * @author uwe geercken
 */
public class CheckDistanceIsSmallerOrEqual extends GenericCheck
{
    /**
	 * specify two strings and a value for the distance to be evaluated
	 */
    public static boolean evaluate(String originalString,String compareString, int value )
    {
    	int[][] dp = new int[originalString.length() + 1][compareString.length() + 1];

    	for (int i = 0; i < dp.length; i++)
		{
			for (int j = 0; j < dp[i].length; j++)
			{
				dp[i][j] = i == 0 ? j : j == 0 ? i : 0;
				if (i > 0 && j > 0)
				{
					if (originalString.charAt(i - 1) == compareString.charAt(j - 1))
					{
						dp[i][j] = dp[i - 1][j - 1];
					}
					else
					{
						dp[i][j] = Math.min(dp[i][j - 1] + 1, Math.min(
								dp[i - 1][j - 1] + 1, dp[i - 1][j] + 1));
					}
				}
			}
		}
		return dp[originalString.length()][compareString.length()]< value;
    }
    
}
