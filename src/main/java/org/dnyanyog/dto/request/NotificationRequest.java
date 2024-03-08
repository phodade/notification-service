package org.dnyanyog.dto.request;



import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;

@Component
public class NotificationRequest {
	
    @NotBlank(message = "Client ID cannot be blank")
	private String clientId;

    @NotBlank(message = "Mode cannot be blank")
	private String mode;

    @NotBlank(message = "Subject cannot be blank")
	private String subject;

    @NotBlank(message = "Body cannot be blank")
	private String body;

	private String footer;

    @NotBlank(message = "From field cannot be blank")
	private String from;

    @NotBlank(message = "To field cannot be blank")
	private String to;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
