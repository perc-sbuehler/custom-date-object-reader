package org.custom.date.reader;

import com.perceptivesoftware.pie.common.data.Context;
import com.perceptivesoftware.pie.common.data.ContextShape;
import com.perceptivesoftware.pie.util.connector.AbstractAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteMe extends AbstractAction {
    private static final String IN_PARAM = "InputParam";
    private static final String OUT_PARAM = "OutputParam";
    Logger logger = LoggerFactory.getLogger(DeleteMe.class);
    
    private ContextShape INPUT_SHAPE = new ContextShape() {
        // TODO: Define the context shape that is required by this action in order to complete execution
        {
            addString(IN_PARAM);
        }
    };

    private ContextShape OUTPUT_SHAPE = new ContextShape() {
        // TODO: Define the context shape to inform the engine what parameters it can expect as output from this action
        {
            addString(OUT_PARAM);
        }
    };

    @Override
    // TODO: This method can be unsynchronized, but you must be able to guarantee that it is thread-safe.
    public synchronized Context execute(Context input) {
        // Log something to show how logging works
        logger.info(  
            "Executing action DeleteMe with input [%s]",
            input.getString(IN_PARAM));

        // TODO: perform action

        logger.info("Action DeleteMe completed.");

        // Create the output data context
        Context outputData = new Context();
        outputData.set(OUT_PARAM, "test");
        return outputData;
    }

    @Override
    public ContextShape getInputs() {
        return INPUT_SHAPE;
    }

    @Override
    public ContextShape getOutputs() {
        return OUTPUT_SHAPE;
    }
}
