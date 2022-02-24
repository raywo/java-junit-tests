import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

  @DisplayName("Einzahlung mit gültigem Betrag resultiert in entsprechender Erhöhung des Saldos")
  @Test
  void depositWithValidAmountShouldWork() throws AmountInvalidException {
    // Arrange
    BigDecimal amount = BigDecimal.valueOf(20);
    Account account = new Account("DE1234");
    BigDecimal expectedBalance = amount;

    // Act
    account.deposit(amount);

    // Assert
    BigDecimal newBalance = account.getBalance();
    assertEquals(expectedBalance, newBalance, "Du doof!");
  }


  @Test
  @Disabled("Deaktiviert bis Bug #4711 ausgebessert ist.")
  void depositWithInvalidAmountShouldThrow() {
    // Arrange
    Account account = new Account("DE1234");
    BigDecimal amount = BigDecimal.valueOf(-20);
    String expectedMessage = "Der Betrag darf nicht kleiner oder gleich 0 sein!";

    // Act + Assert
    Exception exception = assertThrows(AmountInvalidException.class, () -> {
      account.deposit(amount);
    });
    String actualMessage = exception.getMessage();
    String assertMessage = "\nexpected: " + expectedMessage + "\nactual: " + actualMessage;
    assertTrue(actualMessage.contains(expectedMessage), assertMessage);
  }


  @Test
  void depositWithNullAmountShouldThrow() {
    // Arrange
    Account account = new Account("DE1234");
    BigDecimal amount = null;

    // Act + Assert
    assertThrows(NullPointerException.class, () -> {
      account.deposit(amount);
    });
  }


  @Test
  void withdrawWithValidAmountAndSufficientBalanceShouldDecreaseBalance() throws AmountInvalidException, InsufficientMoneyException {
    // Arrange
    Account account = new Account("DE1234");
    BigDecimal amount = BigDecimal.valueOf(50);
    account.deposit(amount);
    BigDecimal expectedBalance = BigDecimal.ZERO;

    // Act
    account.withdraw(amount);

    // Assert
    assertEquals(expectedBalance, account.getBalance());
  }


  @Test
  void withdrawWithValidAmountAndInsufficientBalanceShouldThrow() {
    // Arrange
    Account account = new Account("DE1234");
    BigDecimal amount = BigDecimal.valueOf(50);

    // Act + Assert
    assertThrows(InsufficientMoneyException.class, () -> {
      account.withdraw(amount);
    });
  }


  @Test
  void withdrawWithInvalidAmountShouldThrow() {
    // Arrange
    Account account = new Account("DE1234");
    BigDecimal amount = BigDecimal.valueOf(-50);

    // Act + Assert
    assertThrows(AmountInvalidException.class, () -> {
      account.withdraw(amount);
    });
  }


  @Test
  void withdrawWithNullAmountShouldThrow() {
    // Arrange
    Account account = new Account("DE1234");
    BigDecimal amount = null;

    // Act + Assert
    assertThrows(NullPointerException.class, () -> {
      account.withdraw(amount);
    });
  }


  @Test
  void newAccountShouldHaveZeroBalanceAndGivenIban() {
    // Arrange
    String expectedIban = "DE1234";
    BigDecimal expectedBalance = BigDecimal.ZERO;

    // Act
    Account account = new Account(expectedIban);

    // Assert
    String actualIban = account.getIban();
    BigDecimal actualBalance = account.getBalance();
    assertEquals(actualIban, expectedIban);
    assertEquals(expectedBalance, actualBalance);
  }


  @Test
  void toStringGivesIbanAndBalance() throws AmountInvalidException {
    // Arrange
    String iban = "DE1234";
    BigDecimal expectedBalance = BigDecimal.valueOf(125);
    Account account = new Account(iban);
    account.deposit(expectedBalance);
    String expectedString = String.format("Account(iban=%s, balance=%s)",
        iban, expectedBalance);

    // Act
    String actualToString = account.toString();

    // Assert
    assertEquals(expectedString, actualToString);
  }
}
