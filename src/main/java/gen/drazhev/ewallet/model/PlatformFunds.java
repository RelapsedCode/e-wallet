package gen.drazhev.ewallet.model;

import jakarta.persistence.Entity;
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
	private Integer id = 1;

	private double totalGrossAmount;
	private double lastAddedFee;

}
