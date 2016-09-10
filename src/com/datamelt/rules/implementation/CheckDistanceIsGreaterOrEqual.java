/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * Checks if the Levenshtein distance between two strings is greater or equal to a given value.
 * <p>
 * 
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckDistanceIsGreaterOrEqual extends GenericCheck
{
	/**
	 * specify two strings and an integer value for the Levenshtein distance to be evaluated
	 *
     * @param originalString	the first value for comparison
     * @param compareString 	the second value for comparison - to compare against the first value
     * @param value				expected value of the distance between the two strings
     * @return					indication if the Levenshtein distance is greater or equal to the given value
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
		return dp[originalString.length()][compareString.length()]>= value;
    }
    
}
