package org.custom.date.reader.test;

import java.io.InputStream;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.custom.date.reader.DateObjectReader;
import org.custom.date.reader.DateObjectReaderLink;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.perceptivesoftware.pie.common.data.Context;
import com.perceptivesoftware.pie.common.mapping.dependency.Reference;
import com.perceptivesoftware.pie.common.mapping.validation.ValidationResults;
import com.perceptivesoftware.pie.common.mapping.validation.Violation;

public class DateObjectReaderLinkTest extends Fixture{

	protected DateObjectReader reader;
	Context inputData;

	private Date TEST_DATE = new Date();
	private String TEST_DATE_REF = "myDate"; //$NON-NLS-1$
	private String TIME_ZONE = "GMT"; //$NON-NLS-1$
	
	DateObjectReaderLink link;

	@Before
	public void setup() throws Exception {
		
		
		JAXBContext context = JAXBContext
					.newInstance(DateObjectReaderLink.class);
			Unmarshaller um = context.createUnmarshaller();
	 link = (DateObjectReaderLink) um.unmarshal(getClass().getResourceAsStream("/res/DateObjectReader_config.xml"));
	
	 
		Reference ref = link.getDateReference();
		String timeZone = link.getTimeZoneOut();
		String output = link.getOutputFormat();

		Assert.assertEquals(TIME_ZONE, timeZone);
		Assert.assertEquals("MM/dd/YYYY", output); //$NON-NLS-1$
		Assert.assertEquals("myDate", ref.getName()); //$NON-NLS-1$

		inputData = getContextFromLink(link, TEST_DATE);

		Assert.assertEquals(TEST_DATE, inputData.getDate(TEST_DATE_REF));
	}

	@Test
	public void testSuccessfulSearchConfig() throws Exception {

		ValidationResults results = link.validateSource(null);

		Assert.assertTrue("no Violations ", results.getViolations().isEmpty()); //$NON-NLS-1$
	}

	@Test
	public void testMissingParams() throws Exception {

		// Set all the params to null -- implying that the reader was empty
		link.setDateReference(null);
		link.setTimeZoneOut(null);
		link.setOutputFormat(null);

		ValidationResults results = link.validateSource(null);

		Map<String, Violation> violations = results.getViolations();
		Assert.assertEquals(3, violations.size());

		Assert.assertTrue(violations.get(DateObjectReaderLink.DATE_REFERENCE)
				.getProblem()
				.contains("Date reference cannot be null or empty")); //$NON-NLS-1$
		Assert.assertTrue(violations.get(DateObjectReaderLink.TIME_ZONE_OUT)
				.getProblem().contains("Invalid time zone")); //$NON-NLS-1$
		Assert.assertTrue(violations.get(DateObjectReaderLink.OUTPUT_FORMAT)
				.getProblem().contains("Invalid output date format")); //$NON-NLS-1$

	}
	
	@Test(expected = DateTimeException.class)
	public void testInvalidTimeZone() throws Exception {

		// Expect an error thrown when we input an invalid time zone
		link.validateTimeZone("NOTVALID"); //$NON-NLS-1$
		Assert.fail("An Exception should have been thrown but was not over time zone"); //$NON-NLS-1$
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDateFormat() throws Exception {

		// Expect an error thrown when we input an invalid date format
		link.validateDateFormat("NOTVALID"); //$NON-NLS-1$
		Assert.fail("An Exception should have been thrown but was not over output format"); //$NON-NLS-1$
	}

	@Test
	public void testFormatDate() throws Exception {
		
		Instant dateInstance = Instant.parse("2014-12-12T10:15:30.00Z"); //$NON-NLS-1$
		
		Assert.assertEquals("12/12/2014", link.formatDate(dateInstance)); //$NON-NLS-1$
	}	
	
}
