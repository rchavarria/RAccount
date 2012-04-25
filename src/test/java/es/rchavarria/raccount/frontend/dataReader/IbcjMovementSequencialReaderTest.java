package es.rchavarria.raccount.frontend.dataReader;

import java.io.InputStream;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.model.DoubleMovement;

public class IbcjMovementSequencialReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNext() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		InputStream is = getClass().getResourceAsStream("/datatest/mov ibcj.txt");

		SequencialReader<DoubleMovement> reader = new IbcjMovementSequencialReader(is);
		DoubleMovement m = null;

		m = reader.next();
		Assert.assertEquals("Descripcion 1", m.getDescription());
		Assert.assertEquals("11/07/10", sdf.format(m.getMovementDate()));
		Assert.assertEquals(10d, m.getAmount());
		m = reader.next();
		Assert.assertEquals("Descripcion 2 /// En dos filas", m.getDescription());
		Assert.assertEquals("13/07/10", sdf.format(m.getMovementDate()));
		Assert.assertEquals(600d, m.getAmount());
		m = reader.next();
		Assert.assertEquals("Movimiento en negativo", m.getDescription());
		Assert.assertEquals("14/07/10", sdf.format(m.getMovementDate()));
		Assert.assertEquals(-40d, m.getAmount());

		Assert.assertNull(reader.next());
	}

}
