import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
  @Getter
  @EqualsAndHashCode.Include
  private final String iban;
  @Getter
  private BigDecimal balance;


  public Account(String iban) {
    this.iban = iban;
    this.balance = BigDecimal.ZERO;
  }


  public void deposit(@NonNull BigDecimal amount) throws AmountInvalidException {
    validateAmount(amount);
    this.balance = this.balance.add(amount);
  }


  public void withdraw(BigDecimal amount) throws AmountInvalidException, InsufficientMoneyException {
    validateAmount(amount);

    if (this.balance.compareTo(amount) < 0) {
      throw new InsufficientMoneyException("Es ist nicht genügend Geld auf dem Konto!");
    }

    this.balance = this.balance.subtract(amount);
  }


  private void validateAmount(@NonNull BigDecimal amount) throws AmountInvalidException {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new AmountInvalidException("Der Betrag darf nicht kleiner oder gleich 0 sein!");
    }
  }
}
