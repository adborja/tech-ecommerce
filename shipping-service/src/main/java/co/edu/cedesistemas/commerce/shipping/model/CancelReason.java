package co.edu.cedesistemas.commerce.shipping.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CancelReason {

	private Reason reason;
	private String description;

	public enum Reason {
		ADDRESS_NOT_FOUND, CUSTOMER_NOT_FOUND, CUSTOMER_REJECTED
	}
}
