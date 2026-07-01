package io.camunda.connector.barcode;

public class BarcodeError {


    public static final String ERROR_BAD_INPUTPARAMETER = "BAD_INPUTPARAMETER";
    public static final String ERROR_BAD_INPUTPARAMETER_EXPLANATION = "During the bind, some input does not have the expected type";


    public static final String INCORRECT_STORAGEDEFINITION = "INCORRECTSTORAGEDEFINITION";
    public static final String INCORRECT_STORAGEDEFINITION_EXPLANATION = "Definition to access the storage is incorrect";

    public static final String SAVE_OPERATION = "SAVE_OPERATION";
    public static final String SAVE_OPERATION_EXPLANATION = "During saved operation";

    public static final String NO_DESTINATION_STORAGE_DEFINITION_DEFINE = "NO_DESTINATION_STORAGE_DEFINITION_DEFINE";
    public static final String NO_DESTINATION_STORAGE_DEFINITION_DEFINE_EXPLANATION = "A destination storage must be defined to store the barcode generated";

    public static final String  GENERATION_ERROR = "GENERATION_ERROR";
    public static final String  GENERATION_ERROR_EXPLANATION = "During the generation operation";

}
