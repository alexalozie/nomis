package org.nomisng.web.apierror;

import org.apache.commons.lang3.StringUtils;
import java.util.Map;

public class RecordExistException extends RuntimeException {
    public RecordExistException(Class clazz, String... searchParamsMap) {
        super(RecordExistException.generateMessage(clazz.getSimpleName(), ErrorMapper.toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " already exist " + searchParams;
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public RecordExistException() {
    }
    //</editor-fold>
}
