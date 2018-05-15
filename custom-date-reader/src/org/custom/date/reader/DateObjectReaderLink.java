package org.custom.date.reader;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.perceptivesoftware.pie.common.mapping.DataSource;
import com.perceptivesoftware.pie.common.mapping.dependency.Reference;
import com.perceptivesoftware.pie.common.mapping.dependency.ReferenceType;
import com.perceptivesoftware.pie.common.mapping.input.InputValidationContext;
import com.perceptivesoftware.pie.common.mapping.input.ParameterSource;
import com.perceptivesoftware.pie.common.mapping.input.RowSource;
import com.perceptivesoftware.pie.common.mapping.input.RowSource.None;
import com.perceptivesoftware.pie.common.mapping.validation.ValidationResults;
import com.perceptivesoftware.pie.common.mapping.validation.ValidationResults.Builder;
import com.perceptivesoftware.pie.common.mapping.validation.Violation;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {DateObjectReaderLink.DATE_REFERENCE, DateObjectReaderLink.TIME_ZONE_OUT, DateObjectReaderLink.OUTPUT_FORMAT})
@XmlRootElement(name = DateObjectReaderLink.NODE_NAME)
public class DateObjectReaderLink implements ParameterSource<RowSource.None<DataSource.None>, DataSource.None> {
	public static final String NODE_NAME = "dateObjectReader"; //$NON-NLS-1$
	public static final String DATE_REFERENCE = "dateReference"; //$NON-NLS-1$
	public static final String TIME_ZONE_OUT = "timeZoneOut"; //$NON-NLS-1$
	public static final String OUTPUT_FORMAT = "outputFormat"; //$NON-NLS-1$
	
	@ReferenceType(String.class)
    @XmlElement(required = true, name = DATE_REFERENCE)
    protected Reference dateReference;
	
	@XmlElement(required = true, name = TIME_ZONE_OUT)
	protected String timeZoneOut;
	
	
	@XmlElement(required = true, name = OUTPUT_FORMAT)
	protected String outputFormat;	
	
	public String getTimeZoneOut() {
		return timeZoneOut;
	}

	public void setTimeZoneOut(String timeZone) {
		this.timeZoneOut = timeZone;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public Reference getDateReference() {
		return dateReference;
	}

	public void setDateReference(Reference dateReference) {
		this.dateReference = dateReference;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(dateReference, timeZoneOut, outputFormat);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof DateObjectReaderLink)) {
            return false;
        }

        final DateObjectReaderLink other = (DateObjectReaderLink) obj;

        return Objects.equal(dateReference, other.dateReference) && 
        		Objects.equal(timeZoneOut, other.timeZoneOut) && 
        		Objects.equal(outputFormat, other.outputFormat);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
        		.add(DATE_REFERENCE, dateReference)
        		.add(TIME_ZONE_OUT, timeZoneOut)
        		.add(OUTPUT_FORMAT, outputFormat)
        		.toString();
    }
    

    @Override
    public ValidationResults validateSource(InputValidationContext<None<DataSource.None>, DataSource.None> context) {
        Builder builder = new ValidationResults.Builder();
       
        builder.checkViolation(DATE_REFERENCE, new Violation("Date reference cannot be null or empty"), getDateReference() != null); //$NON-NLS-1$
        
        try {
        	validateTimeZone( getTimeZoneOut());
        } catch (Exception e) {
            builder.addViolation(TIME_ZONE_OUT, new Violation("Invalid time zone '%s'.", timeZoneOut)); //$NON-NLS-1$
        }    
        
        try {
        	validateDateFormat(getOutputFormat());
        } catch (Exception e) {
            builder.addViolation(OUTPUT_FORMAT, new Violation("Invalid output date format '%s'.", outputFormat)); //$NON-NLS-1$
        }        

        return builder.build();
    }
    
    public void validateTimeZone( String timeZone ) throws Exception {
    	Instant instant = Instant.now();  	
    	instant.atZone( ZoneId.of(timeZone));
    }
    
    public void validateDateFormat( String outputFormat) throws Exception {    	
    	Instant instant = Instant.now();  	
        ZonedDateTime zonedTime = instant.atZone( ZoneId.of("GMT")); //$NON-NLS-1$
        zonedTime.format(DateTimeFormatter.ofPattern(outputFormat));
    }
           
    public String formatDate(Instant instant) throws ParseException {    	
        ZonedDateTime zonedTime = ZonedDateTime.ofInstant(instant, ZoneId.of(getTimeZoneOut()));
        return zonedTime.format(DateTimeFormatter.ofPattern(getOutputFormat()));     	
    }
    
}
