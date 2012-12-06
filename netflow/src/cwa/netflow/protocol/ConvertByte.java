/* Copyright (c) 2012, David Turvey
*  All rights reserved.

*  Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
*  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
*  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation 
*  and/or other materials provided with the distribution.

*  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
*  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS 
*  BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
*  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
*  IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
*  THE POSSIBILITY OF SUCH DAMAGE.
*/

package cwa.netflow.protocol;

/** ConvertBye - Utility class for converting byte data into Hex(string) and Long values  
*
* @author David Turvey
* @version 1.0 6-10-12
*/

public class ConvertByte {
		
		// converts a single byte to a simple java int treats byte as unsigned short
		public static int byteToUnsignedInt(byte b)
		{
			int i = Byte.valueOf(b);
			if (i < 0)
				i = 256 + i;
			return i;
		}
		
		/** Description of byteToHex(byte b)
		 * 
		 * Utility method to convert a byte to a hex value string
		 * 
		 * @param b			Byte to convert
		 * @return string		String containing converted hex value			
		 * 
		 */	
		public static String byteToHex(byte b)
		{
			String retv;
			
			char []c = new char[2];
			int i = b, i1;
			int index = 1;
			for (int j = 0; j < 2; j++, index--)
			{
				i1 = i & 0x000F;
				c[index] = toChar(i1);
				i = i >>> 4;
			}
			retv = new String(c); 
			return retv;
		}
		
		/** Description of toChar(int i)
		 * 
		 * Converts single byte to the ASCII value character
		 * 
		 * @param i		integer to convert
		 * 
		 */
	
		private static char toChar(int i)
		{
			char retv;
			if (i < 10)
				retv = (char)(48 + i);  //ASCII char for 0 = 48
			else
				retv = (char)(65 + i - 10);  //ASCII char for A = 48
			
			return retv;
		}
	
		/** Description of byteToULong(byte[] b)
		 * 
		 * Converts a 4 byte array into a single unsigned long int 
		 * 
		 * @param b			byte array in most significant bit order 
		 * @return			converted value
		 * 
		 */
		
		public static long byteToULong(byte[] b)
		{
			long retv = (long)0x00000000;
			int num = 0;
			for (int i = 0; i < 4; i++)
			{
				// nasty stuff to cope with no unsigned left shift
				num = b[i];
				if (num < 0)
					num = 256 + num;
				
				retv = retv << 8;
				retv = retv | num;
			}
			return retv;
		}
		
		/** Description of byteToInt(byte b)
		 * 
		 * Converts a 4 byte array into a single signed int 
		 * 
		 * @param b			byte in most significant bit order 
		 * @return			converted value
		 * 
		 */
		public static int byteToInt(byte b)
		{
			int i = Byte.valueOf(b);
			if(i < 0)
				i = i + 256;
			return i;
		}

}
