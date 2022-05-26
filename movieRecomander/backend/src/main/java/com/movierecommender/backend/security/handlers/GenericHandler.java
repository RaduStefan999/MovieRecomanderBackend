package com.movierecommender.backend.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class GenericHandler
{
	private GenericHandler(){}

	static void handleError(HttpServletResponse httpServletResponse,
							   String error, String message) throws IOException
	{
		Map<String,Object> response = new HashMap<>();
		response.put("errorsType", "Authorisation");
		response.put("errors", error);

		String[] messageArray = new String[1];
		messageArray[0] = message;

		response.put("errorsSummary", messageArray);

		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpServletResponse.setContentType(MediaType.APPLICATION_JSON.toString());
		OutputStream out = httpServletResponse.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(out,response);

		out.flush();
	}
}
