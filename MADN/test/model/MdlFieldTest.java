package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MdlFieldTest {

	private MdlField field;
	private MdlPlayer player;

	@Before
	public void setUp() throws Exception {
		field = new MdlField();
		player = new MdlPlayer();
	}
	
	@Test
	public void testGetFieldType() {
		field.setFieldType(EnumFieldType.HOME);
		assertEquals(EnumFieldType.HOME, field.getFieldType());
	}

	@Test
	public void testSetFieldType() {
		testGetFieldType();
		/*mdlField field = new mdlField();
		assertTrue(field.getFieldType() == null);
		field.setFieldType(FieldType.HOME);
		assertTrue(field.getFieldType() == FieldType.HOME);*/
	}
	
	@Test
	public void testSetMySpieler() {
		testGetMySpieler();
	}
	
	@Test
	public void testGetMySpieler() {
		assertTrue(field.getMySpieler() == null);
		field.setMySpieler(player);
		assertSame(player, field.getMySpieler());
	}
}
