package br.com.fiap.domain.usuario;

import br.com.fiap.domain.ValueObject;

public class CPF extends ValueObject {
    private final String value;

    public CPF(String value) {
        this.value = value;
    }

    public boolean isValidCPF() {
        String cpf = this.value;
        if (this.value == null) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (!cpf.matches("\\d{11}")) {
            return false;
        }

        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        return checkDigits(cpf);
    }

    private boolean checkDigits(String cpf) {
        try {
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(cpf.charAt(i));
                sum1 += digit * (10 - i);
                sum2 += digit * (11 - i);
            }

            int check1 = 11 - (sum1 % 11);
            check1 = (check1 >= 10) ? 0 : check1;

            sum2 += check1 * 2;
            int check2 = 11 - (sum2 % 11);
            check2 = (check2 >= 10) ? 0 : check2;

            return check1 == Character.getNumericValue(cpf.charAt(9))
                    && check2 == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
