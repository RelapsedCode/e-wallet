package gen.drazhev.ewallet.model;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Tells Hibernate to amke a table out of this class
@Entity
public class Wallet {
	@Id
	private Integer id = 1;

	@Column(name = "address")
	private String address;

	@Column(name = "balance")
	private double balance;

	@JdbcTypeCode(SqlTypes.JSON)
	List<Transaction> lastTransaction;

}
