package cwa.netflow.unittesting;

import static org.junit.Assert.*;

import org.junit.Test;

import cwa.netflow.protocol.ConvertByte;

public class ConvertByteTests {
	public static void main(String[] args) {
		Byte test = (byte)0x00;
		byte[] quad = { 0x00, 0x00, 0x00, 0x00};
		int j = 0;
		for (int i = 0; i < 255; i++, test++)
		{
			j = Byte.valueOf(test);
			
			if (j < 0)
				j = 256 + j;
			
			quad[3] = test;
			
			System.out.println("i = " + i + "\tj = " + j + "\tByte = " + test + "\tvalueOf" + Byte.valueOf(test)+ " MyHex = " + ConvertByte.byteToHex(test) + "\tQuad = " + ConvertByte.byteToULong(quad) );
		}

		test = (byte)0xC8; // decimal 200
		int i = Byte.valueOf(test);
		
		if (i < 0)
		{
			j = 256 + i;
			i = 128 - i;
		}
		
		System.out.println("i = " + i + "\tj = " + j + "\tByte = " + test + "\tvalueOf" + Byte.valueOf(test) + " MyHex = " + ConvertByte.byteToHex(test));
		
		byte[] array = { 0x7f, 0x33, 0x22, 0x11 };
		//byte[] array = { 0x00, (byte)0x89, (byte)0x80, (byte)0x98 };
		
		System.out.println("Number = " + ConvertByte.byteToULong(array));
		
	}
	
	@Test
	public void testByteToUnsignedInt() {
		byte[] array = { 0x00, 0x00, 0x00, 0x00 };
		assertEquals("Checked zero", 0, ConvertByte.byteToULong(array));
	}

	@Test
	public void testByteToHex() {
		assertEquals("Checked max","FF",ConvertByte.byteToHex((byte)0xFF));
		assertEquals("Checked min","00",ConvertByte.byteToHex((byte)0x00));
	}

	@Test
	public void testbyteToUint() {
		byte[] b ={ (byte) 0x0,(byte) 0x0,(byte) 0x0,(byte) 0xFF  };
		assertEquals("Checked max",255,ConvertByte.byteToULong(b));
	}

	@Test
	public void testByteToInt() {
		byte[] b ={ (byte) 0x0,(byte) 0x0,(byte) 0x0,(byte) 0xFF  };
		assertEquals("Checked max",255,ConvertByte.byteToULong(b));
	}

}
