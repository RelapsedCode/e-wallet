package gen.drazhev.ewallet.model;

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
@Entity
public class PlatformFunds {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private double totalGrossAmount;
	private double lastAddedFee;

}