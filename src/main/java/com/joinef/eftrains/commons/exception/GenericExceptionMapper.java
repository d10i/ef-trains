package com.joinef.eftrains.commons.exception;

import com.sun.jersey.api.container.ContainerException;
import com.yammer.dropwizard.validation.InvalidEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Arrays;
import java.util.List;

public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionMapper.class);
    private static final int UNPROCESSABLE_ENTITY = 422;

    @Override
    public Response toResponse(Exception exception) {
        Response response;
        if (exception instanceof InvalidEntityException) {
            response = generateInvalidEntityResponse((InvalidEntityException) exception);
        } else if (exception instanceof EfTrainsException) {
            response = generateEfTrainsExceptionResponse((EfTrainsException) exception);
        } else if (exception instanceof WebApplicationException) {
            response = generateWebApplicationExceptionResponse((WebApplicationException) exception);
        } else {
            response = generateDefaultResponse(exception);
        }
        return response;
    }

    private Response generateInvalidEntityResponse(InvalidEntityException exception) {
        LOGGER.error("InvalidEntityException encountered: Adding errors to the response. Exception message: " + exception.getErrors());
        LOGGER.error("Stacktrace: ", exception);
        return Response.status(UNPROCESSABLE_ENTITY)
                .type(MediaType.APPLICATION_JSON)
                .entity(buildErrorJson(exception.getErrors()))
                .build();
    }

    private Response generateEfTrainsExceptionResponse(EfTrainsException exception) {
        LOGGER.error("EfTrainsException encountered: Adding errors to the response. Exception message: " + exception.getMessage());
        LOGGER.error("Stacktrace: ", exception);
        return Response.status(exception.getStatus())
                .type(MediaType.APPLICATION_JSON)
                .entity(buildErrorJson(Arrays.asList(exception.getMessage())))
                .build();
    }

    private Response generateWebApplicationExceptionResponse(WebApplicationException exception) {
        LOGGER.error("WebApplicationException encountered: Adding errors to the response. Exception message: " + exception.getMessage());
        LOGGER.error("Stacktrace: ", exception);
        return Response.status(exception.getResponse().getStatus())
                .type(MediaType.APPLICATION_JSON)
                .entity(buildErrorJson(Arrays.asList(exception.getMessage())))
                .build();
    }

    private Response generateDefaultResponse(Exception exception) {
        String causeMessage = (exception.getCause() != null ? exception.getCause().getMessage() : "");
        String errorMessage = exception.getMessage() + " - " + causeMessage;
        LOGGER.error("Exception encountered: Adding errors to the response. Exception message: " + errorMessage);
        LOGGER.error("Stacktrace: ", exception);
        int status = 500;
        if (exception instanceof ContainerException && exception.getMessage().contains("Exception obtaining parameters")) {
            status = 401;
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(buildErrorJson(Arrays.asList(errorMessage)))
                .build();
    }

    private String buildErrorJson(List<String> errors) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"errors\":[");
        if (errors != null && errors.size() > 0) {
            for (String error : errors) {
                String errorStr = error == null ? null : addBackslashes(error);
                sb.append("\"").append(errorStr).append("\",");
            }
            sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1);
        }
        sb.append("]}");
        return sb.toString();
    }

    private String addBackslashes(String error) {
        // "  becomes \"
        // \" becomes \\\"
        // Example:
        // {"err" : "E11000 duplicate key: { : ObjectId('5375dfa7e4b0802af3529321'), : \"3389e94f10b119c24dfde9fb03db2250e374bda32bb509db33dc93a8cc7e58f5\" }"}
        // Becomes:
        // {\"err\" : \"E11000 duplicate key: { : ObjectId('5375dfa7e4b0802af3529321'), : \\\"3389e94f10b119c24dfde9fb03db2250e374bda32bb509db33dc93a8cc7e58f5\\\" }\"}
        return error.replace("\"", "\\\"").replace("\\\\\"", "\\\\\\\"");
    }
}