/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.datamelt.rules.implementation;

import com.datamelt.util.CheckAnnotation;
import com.datamelt.util.CheckMethodAnnotation;

/**
 * Checks if the Levenshtein distance between two strings is greater than a given value.
 * <p>
 * 
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
@CheckAnnotation(name="Check Distance Is Greater", description="Checks if the Levenshtein distance between two strings is greater than a given value",nameDescriptive="is greater (Levenshtein distance)",checkSingleField=1)
public class CheckDistanceIsGreater extends GenericCheck
{
	/**
	 * specify two strings and an integer value for the Levenshtein distance to be evaluated
	 *
     * @param originalString	the first value for comparison
     * @param compareString 	the second value for comparison - to compare against the first value
     * @param value				expected value of the distance between the two strings
     * @return					indication if the Levenshtein distance is greater than the given value
     */
	@CheckMethodAnnotation(note="Levenshtein distance between two values",noteParameter={"The value for the distance between the two strings to be evaluated"})
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
		return dp[originalString.length()][compareString.length()]> value;
    }
    
}
