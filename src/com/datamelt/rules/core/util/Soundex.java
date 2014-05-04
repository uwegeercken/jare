package com.datamelt.rules.core.util;

/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: LICENSE,v 1.8 2004/02/09 03:33:38 ian Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java 
 * language and environment is gratefully acknowledged.
 * 
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */

public class Soundex
{
	/* Implements the mapping
	   * from: AEHIOUWYBFPVCGJKQSXZDTLMNR
	   * to:   00000000111122222222334556
	   */
	  public static final char[] MAP = {
	    //A  B   C   D   E   F   G   H   I   J   K   L   M
	    '0','1','2','3','0','1','2','0','0','2','2','4','5',
	    //N  O   P   W   R   S   T   U   V   W   X   Y   Z
	    '5','0','1','2','6','2','3','0','1','0','2','0','2'
	  };

	  /** Convert the given String to its Soundex code.
	   * @return null If the given string can't be mapped to Soundex.
	   */
	  public static String soundex(String s) {

	    // Algorithm works on uppercase (mainframe era).
	    String t = s.toUpperCase();

	    StringBuffer res = new StringBuffer();
	    char c, prev = '?';

	    // Main loop: find up to 4 chars that map.
	    for (int i=0; i<t.length() && res.length() < 4 &&
	      (c = t.charAt(i)) != ','; i++) {

	      // Check to see if the given character is alphabetic.
	      // Text is already converted to uppercase. Algorithm
	      // only handles ASCII letters, do NOT use Character.isLetter()!
	      // Also, skip double letters.
	      if (c>='A' && c<='Z' && c != prev) {
	        prev = c;

	        // First char is installed unchanged, for sorting.
	        if (i==0)
	          res.append(c);
	        else {
	          char m = MAP[c-'A'];
	          if (m != '0')
	            res.append(m);
	        }
	      }
	    }
	    if (res.length() == 0)
	      return null;
	    for (int i=res.length(); i<4; i++)
	      res.append('0');
	    return res.toString();
	  }
}
