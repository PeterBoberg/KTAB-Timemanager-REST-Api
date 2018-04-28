package se.karingotrafiken.timemanager.rest.utils;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class UriUtils {

    public static URI toUri(UriInfo info, long appendedPath) {
        return info.getAbsolutePathBuilder().path(Long.toString(appendedPath)).build();
    }
}
