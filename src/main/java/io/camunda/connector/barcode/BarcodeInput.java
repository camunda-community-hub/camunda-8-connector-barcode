package io.camunda.connector.barcode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.zxing.BarcodeFormat;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.cherrytemplate.CherryInput;
import io.camunda.connector.cherrytemplate.RunnerParameter;
import io.camunda.filestorage.storage.StorageDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * the JsonIgnoreProperties is mandatory: the template may contain additional widget to help the designer, especially on the OPTIONAL parameters
 * This avoids the MAPPING Exception
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BarcodeInput implements CherryInput {
    /**
     * Attention, each Input here must be added in the BarcodeFunction, list of InputVariables
     */
    public static final String CODE = "code";
    /**
     * Input need for ExtractPages
     */
    public static final String BARCODE_FORMAT = "barcodeFormat";
    public static final String DESTINATION_FILE_NAME = "destinationFileName";
    public static final String DESTINATION_JSONSTORAGEDEFINITION = "destinationJsonStorageDefinition";

    public static final String BARCODE_FORMAT_AZTEC = BarcodeFormat.AZTEC.toString();
    public static final String BARCODE_FORMAT_CODABAR = BarcodeFormat.CODABAR.toString();
    public static final String BARCODE_FORMAT_CODE_128 = BarcodeFormat.CODE_128.toString();
    public static final String BARCODE_FORMAT_CODE_39 = BarcodeFormat.CODE_39.toString();
    public static final String BARCODE_FORMAT_CODE_93 = BarcodeFormat.CODE_93.toString();
    public static final String BARCODE_FORMAT_DATA_MATRIX = BarcodeFormat.DATA_MATRIX.toString();
    public static final String BARCODE_FORMAT_EAN_13 = BarcodeFormat.EAN_13.toString();
    public static final String BARCODE_FORMAT_EAN_8 = BarcodeFormat.EAN_8.toString();
    public static final String BARCODE_FORMAT_ITF = BarcodeFormat.ITF.toString();
    public static final String BARCODE_FORMAT_PDF_417 = BarcodeFormat.PDF_417.toString();
    public static final String BARCODE_FORMAT_QR_CODE = BarcodeFormat.QR_CODE.toString();
    public static final String BARCODE_FORMAT_UPC_A = BarcodeFormat.UPC_A.toString();
    public static final String BARCODE_FORMAT_UPC_E = BarcodeFormat.UPC_E.toString();


    public static final String OUTPUT_FORMAT = "outputFormat";
    public static final String OUTPUT_FORMAT_PNG = "PNG";
    public static final String OUTPUT_FORMAT_JPEG = "JPEG";
    public static final String OUTPUT_FORMAT_BMP = "BMP";
    public static final String OUTPUT_FORMAT_GIF = "GIFPNG";
    public static final String OUTPUT_FORMAT_WBPM = "WBPM";

    /**
     *
     */

    public static final RunnerParameter parameterBarcode = new RunnerParameter(
            BarcodeInput.CODE,
            // name
            "Code", // label
            String.class, // class
            RunnerParameter.Level.REQUIRED, // level
            "Code");

    public static final RunnerParameter parameterBarcodeFormat = new RunnerParameter(
            BarcodeInput.BARCODE_FORMAT,
            // name
            "Barcode Format", // label
            String.class, // class
            RunnerParameter.Level.REQUIRED, // level
            "Barcode format")
            .addChoice(BARCODE_FORMAT_AZTEC, BARCODE_FORMAT_AZTEC)
            .addChoice(BARCODE_FORMAT_CODABAR, BARCODE_FORMAT_CODABAR)
            .addChoice(BARCODE_FORMAT_CODE_128, BARCODE_FORMAT_CODE_128)
            .addChoice(BARCODE_FORMAT_CODE_39, BARCODE_FORMAT_CODE_39)
            .addChoice(BARCODE_FORMAT_CODE_93, BARCODE_FORMAT_CODE_93)
            .addChoice(BARCODE_FORMAT_DATA_MATRIX, BARCODE_FORMAT_DATA_MATRIX)
            .addChoice(BARCODE_FORMAT_EAN_13, BARCODE_FORMAT_EAN_13)
            .addChoice(BARCODE_FORMAT_EAN_8, BARCODE_FORMAT_EAN_8)
            .addChoice(BARCODE_FORMAT_ITF, BARCODE_FORMAT_ITF)
            .addChoice(BARCODE_FORMAT_PDF_417, BARCODE_FORMAT_PDF_417)
            .addChoice(BARCODE_FORMAT_QR_CODE, BARCODE_FORMAT_QR_CODE)
            .addChoice(BARCODE_FORMAT_UPC_A, BARCODE_FORMAT_UPC_A)
            .addChoice(BARCODE_FORMAT_UPC_E, BARCODE_FORMAT_UPC_E);

    public static final RunnerParameter parameterOutputFormat = new RunnerParameter(
            BarcodeInput.OUTPUT_FORMAT,
            // name
            "Output format", // label
            String.class, // class
            RunnerParameter.Level.REQUIRED, // level
            "Output format")
            .addChoice(OUTPUT_FORMAT_PNG, OUTPUT_FORMAT_PNG)
            .addChoice(OUTPUT_FORMAT_JPEG, OUTPUT_FORMAT_JPEG)
            .addChoice(OUTPUT_FORMAT_BMP, OUTPUT_FORMAT_BMP)
            .addChoice(OUTPUT_FORMAT_GIF, OUTPUT_FORMAT_GIF)
            .addChoice(OUTPUT_FORMAT_WBPM, OUTPUT_FORMAT_WBPM);

    public static final RunnerParameter parameterDestinationFileName = new RunnerParameter(
            BarcodeInput.DESTINATION_FILE_NAME,
            // name
            "Destination file name", // label
            String.class, // class
            RunnerParameter.Level.REQUIRED, // level
            "Name of the new file created");
    public static final RunnerParameter parameterDestinationJsonStorageDefinition = new RunnerParameter(
            BarcodeInput.DESTINATION_JSONSTORAGEDEFINITION, // name
            "JSon Storage Destination", // label
            Map.class, // class
            RunnerParameter.Level.REQUIRED, // level
            "Storage Definition in Json.");

    public List<RunnerParameter> inputParametersList = List.of(
            parameterBarcode,
            parameterBarcodeFormat,
            parameterOutputFormat,
            parameterDestinationFileName,
            parameterDestinationJsonStorageDefinition
    );


    private final Logger logger = LoggerFactory.getLogger(BarcodeInput.class.getName());
    public Long dpi = 300L;
    private String code;
    private String barcodeFormat;
    private String destinationFileName;
    private Object destinationJsonStorageDefinition;
    private String outputFormat;

    public String getCode() {
        return code;
    }

    public String getBarcodeFormat() {
        return barcodeFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public String getDestinationFileName() {
        return destinationFileName;
    }

    public Object getDestinationJsonStorageDefinition() {
        return destinationJsonStorageDefinition;
    }


    @JsonIgnore
    @Override
    public List<Map<String, Object>> getInputParameters() {
        return inputParametersList.stream().map(t -> t.toMap(null)).toList();
    }

    /**
     * Return a Storage definition
     *
     * @return the storage definition
     * @throws ConnectorException if the connection
     */
    @JsonIgnore
    public StorageDefinition getDestinationStorageDefinitionObject() throws ConnectorException {
        try {
            StorageDefinition storageDefinitionObj = null;
            // Attention, it may be an empty string due to the modeler which not like null value
            if (getDestinationJsonStorageDefinition() != null && !getDestinationJsonStorageDefinition().toString().trim().isEmpty()) {
                storageDefinitionObj = StorageDefinition.getFromObject(getDestinationJsonStorageDefinition());
                return storageDefinitionObj;
            }

            return null;
        } catch (Exception e) {
            logger.error("Can't get the FileStorage - bad Gson value :" + getDestinationJsonStorageDefinition());
            throw new ConnectorException(BarcodeError.INCORRECT_STORAGEDEFINITION,
                    "FileStorage information" + getDestinationJsonStorageDefinition());
        }
    }
}
