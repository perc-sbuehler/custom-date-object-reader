package org.custom.date.reader.test;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.custom.date.reader.DateObjectReaderLink;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.perceptivesoftware.pie.common.data.Context;
import com.perceptivesoftware.pie.common.mapping.DataSource;
import com.perceptivesoftware.pie.common.mapping.DataSource.None;
import com.perceptivesoftware.pie.common.mapping.dependency.Reference;
import com.perceptivesoftware.pie.common.mapping.input.InputMapping;
import com.perceptivesoftware.pie.common.mapping.input.InputParameter;
import com.perceptivesoftware.pie.common.mapping.input.InputRowSet;
import com.perceptivesoftware.pie.common.mapping.input.ParameterSource;
import com.perceptivesoftware.pie.common.mapping.input.RowSource;
//import com.perceptivesoftware.pie.common.mapping.output.OutputMapping;
//import com.perceptivesoftware.pie.common.mapping.output.OutputParameter;
//import com.perceptivesoftware.pie.common.mapping.output.ParameterTarget;
//import com.perceptivesoftware.pie.common.mapping.output.RowTarget;
import com.perceptivesoftware.pie.engine.mapping.source.XMLRowSource;
import com.perceptivesoftware.pie.engine.mapping.source.XMLSource;

@RunWith(MockitoJUnitRunner.class)
public abstract class Fixture {

    public Collection<DataSource.None> datasources = Collections.emptyList();

    public Context data;
    public DocumentBuilder builder;

    @Before
    public void setupFixture() throws Exception {

        data = new Context();
        builder = DocumentBuilderFactory.newInstance()
                                        .newDocumentBuilder();
    }

    public <T extends ParameterSource<R, D>, R extends RowSource<R, D>, D extends DataSource<D>> InputParameter<T, R, D> input(final String name,
        final T source) {
        return new InputParameter<T, R, D>() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public T getSource() {
                return source;
            }

            @Override
            public void setName(@SuppressWarnings("hiding") String name) {
                // don't.
            }

            @Override
            public void setSource(@SuppressWarnings("hiding") T source) {
                // don't.
            }
        };
    }

    @SafeVarargs
    public final <P extends ParameterSource<R, D>, R extends RowSource<R, D>, D extends DataSource<D>> InputRowSet<P, R, D> inputRowSet(
        final String name, final R rowSource, final InputMapping<P, R, D>... mappings) {
        return new InputRowSet<P, R, D>() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public R getSource() {
                return rowSource;
            }

            @Override
            public List<InputMapping<P, R, D>> getMappings() {
                return Arrays.asList(mappings);
            }

            @Override
            public void setName(String aname) {
                // no
            }

            @Override
            public void setSource(R source) {
                // no
            }
        };
    }

    @SafeVarargs
    public final <T extends ParameterSource<R, D>, R extends RowSource<R, D>, D extends DataSource<D>> InputMapping<T, R, D> inputMapping(
        final InputParameter<T, R, D>... inputParameters) {
        return new InputMapping<T, R, D>() {
            @Override
            public List<InputParameter<T, R, D>> getParameters() {
                return Arrays.asList(inputParameters);
            }

            @Override
            public List<InputRowSet<T, R, D>> getRowsets() {
                return Collections.emptyList();
            }
        };
    }

    public final <T extends ParameterSource<R, D>, R extends RowSource<R, D>, D extends DataSource<D>> InputMapping<T, R, D> inputMapping(
        final InputParameter<T, R, D> inputParameters) {
        return new InputMapping<T, R, D>() {
            @Override
            public List<InputParameter<T, R, D>> getParameters() {
                return Arrays.asList(inputParameters);
            }

            @Override
            public List<InputRowSet<T, R, D>> getRowsets() {
                return Collections.emptyList();
            }
        };
    }

    public <P extends ParameterSource<R, D>, R extends RowSource<R, D>, D extends DataSource<D>> InputMapping<P, R, D> inputMapping(
        final List<InputParameter<P, R, D>> parameters, final List<InputRowSet<P, R, D>> rowsets) {
        return new InputMapping<P, R, D>() {
            @Override
            public List<InputParameter<P, R, D>> getParameters() {
                return parameters;
            }

            @Override
            public List<InputRowSet<P, R, D>> getRowsets() {
                return rowsets;
            }
        };
    }

//    public final <T extends ParameterTarget<R, D>, R extends RowTarget<R, D>, D extends DataSource<D>> OutputParameter<T, R, D> output(
//        final String name, final T target) {
//        return new OutputParameter<T, R, D>() {
//            @Override
//            public String getName() {
//                return name;
//            }
//
//            @Override
//            public T getTarget() {
//                return target;
//            }
//
//            @Override
//            public void setName(String aname) {
//                // no
//            }
//
//            @Override
//            public void setTarget(T unusedTarget) {
//                // no
//            }
//        };
//    }
//
//    @SafeVarargs
//    public final <T extends ParameterTarget<R, D>, R extends RowTarget<R, D>, D extends DataSource<D>> OutputMapping<T, R, D> outputMapping(
//        final OutputParameter<T, R, D>... outputParameters) {
//        return new OutputMapping<T, R, D>() {
//            @Override
//            public List<OutputParameter<T, R, D>> getParameters() {
//                return Arrays.asList(outputParameters);
//            }
//        };
//    }

    @SafeVarargs
    public final <T> List<T> list(T... items) {
        return Arrays.asList(items);
    }

    /**
     * Returns a new instance of {@link Reference}.
     * 
     * @return {@link Reference}
     */
    public Reference getReference() {
        return getReference("reference");
    }

    /**
     * Returns a new instance of {@link Reference}.
     * 
     * @param name {@link String} Set the name property of the reference, cannot be null or empty.
     * @return {@link Reference}
     */
    public Reference getReference(String name) {
        Reference reference = new Reference();
        reference.setName(name);

        return reference;
    }

    /**
     * Returns a new instance of {@link XMLSource}.
     * 
     * @param reference {@link Reference} Sets the reference property, cannot be null or empty.
     * @param xpath {@link String} Sets the xpath property, cannot be null or empty.
     * @return {@link XMLSource}
     */
    public XMLSource getXmlSource(Reference reference, String xpath) {
        XMLSource source = new XMLSource();
        source.setReference(reference);
        source.setXPath(xpath);

        return source;
    }

    /**
     * Returns a new instance of {@link XMLSource}.
     * 
     * @param context {@link String} Sets the context property, cannot be null or empty.
     * @param xpath {@link String} Sets the xpath property, cannot be null or empty.
     * @return {@link XMLSource}
     */
    public XMLSource getXmlSource(String context, String xpath) {
        XMLSource source = new XMLSource();
        source.setContext(context);
        source.setXPath(xpath);

        return source;
    }

    /**
     * Returns a new instance of {@link XMLRowSource}.
     * 
     * @param context {@link String} Sets the context property, cannot be null or empty.
     * @param id {@link String} Sets the id property, cannot be null or empty.
     * @param xpath {@link String} Sets the xpath property, cannot be null or empty.
     * @return {@link XMLRowSource}
     */
    public XMLRowSource getXmlRowSource(String context, String id, String xpath) {
        XMLRowSource rowSource = new XMLRowSource();
        rowSource.setContext(context);
        rowSource.setId(id);
        rowSource.setXPath(xpath);

        return rowSource;
    }

    /**
     * Returns a new instance of {@link XMLRowSource}.
     * 
     * @param reference {@link Reference} Sets the reference property, cannot be null or empty.
     * @param id {@link String} Sets the id property, cannot be null or empty.
     * @param xpath {@link String} Sets the xpath property, cannot be null or empty.
     * @return {@link XMLRowSource}
     */
    public XMLRowSource getXmlRowSource(Reference reference, String id, String xpath) {
        XMLRowSource rowSource = new XMLRowSource();
        rowSource.setReference(reference);
        rowSource.setId(id);
        rowSource.setXPath(xpath);

        return rowSource;
    }

    /**
     * Returns an empty {@link ArrayList}.
     * 
     * @return {@link ArrayList}
     */
    public List<XMLRowSource> getXmlRowSources() {
        return new ArrayList<XMLRowSource>();
    }

    /**
     * Returns an {@link ArrayList} populated with the provided {@link XMLRowSource}'s.
     * 
     * @param rowSources {@code varargs} of {@link XMLRowSource}'s.
     * @return {@link ArrayList}
     */
    public List<XMLRowSource> getXmlRowSources(XMLRowSource... rowSources) {
        List<XMLRowSource> sources = new ArrayList<XMLRowSource>();
        for (XMLRowSource source : rowSources) {
            sources.add(source);
        }

        return sources;
    }

    /**
     * Returns an {@code EmptyList}.
     * 
     * @return {@code EmptyList}
     */
    public List<None> getDataSources() {
        return Collections.<DataSource.None> emptyList();
    }
    
	public final InputStream getResource(String path) {
		return getClass().getResourceAsStream(path);
	}
	
	protected DateObjectReaderLink getDateObjectReaderConfiguration(String path)
			throws Exception {
		JAXBContext context = JAXBContext
				.newInstance(DateObjectReaderLink.class);
		Unmarshaller um = context.createUnmarshaller();
		return (DateObjectReaderLink) um.unmarshal(getResource(path));
	}
		
	protected Context getContextFromLink(DateObjectReaderLink readerLink, Date testDate)
			throws Exception {
		Context inputData1 = new Context();

		for (Method method : readerLink.getClass().getMethods()) {

			if (method.getReturnType() == Reference.class
					&& method.getParameterTypes().length == 0) {

				Reference ref = (Reference) method.invoke(readerLink);

				if (ref == null) {
					continue;
				}

				String name = ref.getName();
				if (name != null && !name.isEmpty()) {
					inputData1.set(name, testDate);
				}
			}
		}

		return inputData1;
	}

}
