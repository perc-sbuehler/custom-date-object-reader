package org.custom.date.reader;

import java.util.Collection;
import java.util.Date;

import com.perceptivesoftware.pie.common.data.Context;
import com.perceptivesoftware.pie.common.mapping.DataSource;
import com.perceptivesoftware.pie.common.mapping.DataSource.None;
import com.perceptivesoftware.pie.common.mapping.input.InputMapping;
import com.perceptivesoftware.pie.common.mapping.input.InputParameter;
import com.perceptivesoftware.pie.common.mapping.input.RowSource;
import com.perceptivesoftware.pie.util.reader.AbstractReader;

public class DateObjectReader extends AbstractReader<DateObjectReaderLink, RowSource.None<DataSource.None>, DataSource.None> {
	
	public DateObjectReader() {
		super(DateObjectReaderLink.class, null, null);
	}

	public Context read(Collection<None> datasources, InputMapping<DateObjectReaderLink, com.perceptivesoftware.pie.common.mapping.input.RowSource.None<None>, None> mapping, Context inputData) 
			throws Exception {

		Context result = new Context();

		for (InputParameter<DateObjectReaderLink, ?, ?> param : mapping.getParameters()) {
			DateObjectReaderLink source = param.getSource();

			String dateRefName = source.getDateReference().getName();
			Date date = inputData.getDate(dateRefName);

			if ( date == null )	{
				result.set(param.getName(),  "" ); //$NON-NLS-1$
			}
			else {
				result.set(param.getName(), source.formatDate(date.toInstant()));
			}
		}

		return result;
	}
}
