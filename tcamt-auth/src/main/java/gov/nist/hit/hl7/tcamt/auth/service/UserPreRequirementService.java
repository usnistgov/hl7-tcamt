package gov.nist.hit.hl7.tcamt.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nist.hit.auth.core.service.UserPreRequirementManager;
import gov.nist.hit.hl7.tcamt.auth.model.AckStatus;
import gov.nist.hit.hl7.tcamt.auth.model.OpAck;
import gov.nist.hit.hl7.tcamt.auth.model.User;
import gov.nist.hit.hl7.tcamt.auth.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public class UserPreRequirementService implements UserPreRequirementManager<UserInfo, User> {

	@Override
	public void setUserPreRequirements(User user, UserInfo principal) {
		if(user.getUsername() == null || user.getUsername().isEmpty()) {
			principal.getPreRequirements().add("username");
		}
	}

	@Override
	public void handlePreRequirementOnLogin(
			UserInfo principal,
			Set<String> preRequirements,
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		if(preRequirements.contains("username")) {
			response.sendRedirect("/setup-profile");
		}
	}

	@Override
	public void handlePreRequirementNotMet(
			UserInfo principal,
			Set<String> preRequirements,
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		if(preRequirements.contains("username")) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("application/json");
			OpAck<String> opAck = new OpAck(AckStatus.FAILED, "User does not meet pre-requirement", "", "setup-profile");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getWriter(), opAck);
		}
	}
}
