package org.custom.date.reader.test;


import java.time.Instant;
import java.util.Date;

import org.custom.date.reader.DateObjectReader;
import org.custom.date.reader.DateObjectReaderLink;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.perceptivesoftware.pie.common.data.Context;

public class DateObjectReaderTest extends Fixture {

	protected DateObjectReader dateReader;
	private Date TEST_DATE;
	private String TEST_DATE2 = "x014-05-12"; //$NON-NLS-1$

	@Before
	public void setup() throws Exception {
		dateReader = new DateObjectReader();
	}

	@Test
	public void testDateFormat() throws Exception {

		DateObjectReaderLink link = getDateObjectReaderConfiguration("/res/DateObjectReader_config.xml"); //$NON-NLS-1$

		Instant dateInstance = Instant.parse("2014-12-05T10:15:30.00Z"); //$NON-NLS-1$
		TEST_DATE = Date.from(dateInstance);

		Context inputData = new Context();
		inputData.set("myDate", TEST_DATE); //$NON-NLS-1$
		inputData.set(DateObjectReaderLink.TIME_ZONE_OUT, link.getTimeZoneOut());
		inputData.set(DateObjectReaderLink.OUTPUT_FORMAT, link.getOutputFormat());

		Context outputData = dateReader.read(datasources, inputMapping(input("l1", link)), inputData); //$NON-NLS-1$
		String results = outputData.getString("l1"); //$NON-NLS-1$

		Assert.assertEquals(results, "12/05/2014"); //$NON-NLS-1$
	}

	@Test
	public void testInvalidDateFormat() throws Exception {

		DateObjectReaderLink link = getDateObjectReaderConfiguration("/res/DateObjectReader_config.xml"); //$NON-NLS-1$

		Context inputData = new Context();
		inputData.set("myDate", TEST_DATE2); //$NON-NLS-1$
		inputData.set(DateObjectReaderLink.TIME_ZONE_OUT, link.getTimeZoneOut());
		inputData.set(DateObjectReaderLink.OUTPUT_FORMAT, link.getOutputFormat());

		try {
			dateReader.read(datasources, inputMapping(input("l1", link)), inputData); //$NON-NLS-1$
			Assert.fail("Should have thrown exceptin"); //$NON-NLS-1$
		} catch (ClassCastException e) {

			String msg = "Value (" + TEST_DATE2 + ") for " + "myDate" + " is not a Date"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			Assert.assertTrue(e.getMessage().contains(msg));
		}
	}

}
