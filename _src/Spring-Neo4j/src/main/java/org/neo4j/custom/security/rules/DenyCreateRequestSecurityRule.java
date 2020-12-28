package org.neo4j.custom.security.rules;

import javax.servlet.http.HttpServletRequest;

import org.neo4j.server.rest.security.SecurityFilter;
import org.neo4j.server.rest.security.SecurityRule;

public class DenyCreateRequestSecurityRule implements SecurityRule{
	
	//Read about RFC - 2617 for more information about REALM - 
	//http://tools.ietf.org/html/rfc2617RFC 2617
	public static final String REALM = "WallyWorld";

	@Override
	public boolean isAuthorized(HttpServletRequest request) {
		//Deny all other Type of Request except GET 
		if(request.getMethod().equalsIgnoreCase("GET")){
			return true;
		}
		return false;
	}

	@Override
	public String forUriPath() {
		
		//This security rule will apply only on the URL which starts /db/data/
		return "/db/data/*";
	}

	@Override
	public String wwwAuthenticateHeader() {
		//Read about RFC - 2617 for more information about REALM - 
		//http://tools.ietf.org/html/rfc2617RFC 2617
		return SecurityFilter.basicAuthenticationResponse(REALM);
	}	
}
