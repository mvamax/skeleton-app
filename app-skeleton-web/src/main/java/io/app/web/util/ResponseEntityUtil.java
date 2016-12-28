package io.app.web.util;

import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

	public static Long getLocationHeaderId(ResponseEntity<?> response){
		String path = response.getHeaders().getLocation().getPath();
		String[] segments = path.split("/");
		String idStr = segments[segments.length-1];
		Long id = Long.parseLong(idStr);
		return id;
	}
}
