package gen.drazhev.ewallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@Column(name = "transactionId", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer transactionId;

	@Column(name = "transactionUUID")
	private String transactionUUID;

	@Column(name = "senderAddress")
	private String senderAddress;

	@Column(name = "receiverAddress")
	private String receiverAddress;

	@Column(name = "amount")
	private double amount;

	@Column(name = "status")
	private String status;

}
