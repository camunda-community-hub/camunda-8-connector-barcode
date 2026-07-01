package io.camunda.connector.barcode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.camunda.connector.cherrytemplate.CherryOutput;
import io.camunda.connector.cherrytemplate.RunnerParameter;

import java.util.List;
import java.util.Map;

public class BarcodeOutput implements CherryOutput {

    public static final String OUTPUT_DESTINATION_FILE = "destinationFile";
    public static final RunnerParameter parameterOutputDestinationFile = new RunnerParameter(OUTPUT_DESTINATION_FILE, // name
            "Destination variable name", // label
            String.class, // class
            RunnerParameter.Level.REQUIRED, "Process variable where the file reference is saved");
    public Object destinationFile;

    public List<RunnerParameter> outputParametersList = List.of(
            parameterOutputDestinationFile
    );
    @JsonIgnore
    @Override
    public List<Map<String, Object>> getOutputParameters() {
        return outputParametersList.stream().map(t -> t.toMap(null)).toList();
    }

}
