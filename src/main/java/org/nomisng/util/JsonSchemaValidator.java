package org.nomisng.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.InputStream;
import java.util.Set;

public class JsonSchemaValidator {
    private static InputStream inputStreamFromClasspath(String path ) {

        // returning stream
        return Thread.currentThread().getContextClassLoader().getResourceAsStream( path );
    }

    // main() method start
    public static void main( String[] args ) throws Exception {

        // create instance of the ObjectMapper class
        ObjectMapper objectMapper = new ObjectMapper();

        // create an instance of the JsonSchemaFactory using version flag
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance( SpecVersion.VersionFlag.V201909 );

        // store the JSON data in InputStream
        try(
                InputStream jsonStream = inputStreamFromClasspath( "/json/treatment_valid.json" );
                InputStream schemaStream = inputStreamFromClasspath( "/json/treatment_schema.json" )
        ){

            // read data from the stream and store it into JsonNode

            JsonNode json = objectMapper.readTree(jsonStream);

            // get schema from the schemaStream and store it into JsonSchema
            JsonSchema schema = schemaFactory.getSchema(schemaStream);

            // create set of validation message and store result in it
            Set<ValidationMessage> validationResult = schema.validate( json );

            // show the validation errors
            if (validationResult.isEmpty()) {

                // show custom message if there is no validation error
                System.out.println( "There is no validation errors" );

            } else {

                // show all the validation error
                validationResult.forEach(vm -> System.out.println(vm.getMessage()));
            }
        }
    }
}
