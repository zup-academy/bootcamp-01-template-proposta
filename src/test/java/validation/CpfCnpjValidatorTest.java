package validation;

import io.github.evertocnsouza.validation.impl.CpfCnpjValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CpfCnpjValidatorTest {

    @Test
    @DisplayName("não deve aceitar qdo nao for cnpj ou cpf")
    public void teste1() throws Exception {
        CpfCnpjValidator validator = new CpfCnpjValidator(); //Cenario
        boolean valido = validator.isValid("", null); // Ação
        Assertions.assertFalse(valido); // Verificação
    }
    @Test
    @DisplayName("deve aceitar quando é cpf")
    public void teste2() throws Exception {
        CpfCnpjValidator validator = new CpfCnpjValidator();
        boolean valido = validator.isValid("08709225641", null);
        Assertions.assertTrue(valido);
    }
    @Test
    @DisplayName("deve aceitar quando é cnpj")
    public void teste3() throws Exception {
        CpfCnpjValidator validador = new CpfCnpjValidator();
        boolean valido = validador.isValid("90778497000166", null);
        Assertions.assertTrue(valido);
    }
}
