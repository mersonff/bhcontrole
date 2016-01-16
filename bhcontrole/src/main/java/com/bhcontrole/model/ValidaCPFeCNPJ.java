package com.bhcontrole.model;

import java.util.InputMismatchException;

public class ValidaCPFeCNPJ {

	public static boolean isCPF(String cpf) {
		String valor = cpf.replace(".", "");
		valor = valor.replace("-", "");

		if (valor == null)
			return false;

		// considera-se erro valor's formados por uma sequencia de numeros
		// iguais
		if (valor.equals("00000000000") || valor.equals("11111111111") || valor.equals("22222222222")
				|| valor.equals("33333333333") || valor.equals("44444444444") || valor.equals("55555555555")
				|| valor.equals("66666666666") || valor.equals("77777777777") || valor.equals("88888888888")
				|| valor.equals("99999999999") || (valor.length() != 11))
			return (false);
		char dig10, dig11;
		int sm, i, r, num, peso;
		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do valor em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (valor.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);
			// converte no respectivo caractere numerico
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (valor.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);
			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == valor.charAt(9)) && (dig11 == valor.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static boolean isCNPJ(String cnpj) {
		String valor = cnpj.replace(".", "");
		valor = valor.replace("-", "");
		valor = valor.replace("/", "");
		
		// considera-se erro valor's formados por uma sequencia de numeros iguais
		if (valor.equals("00000000000000") || valor.equals("11111111111111") || valor.equals("22222222222222")
				|| valor.equals("33333333333333") || valor.equals("44444444444444") || valor.equals("55555555555555")
				|| valor.equals("66666666666666") || valor.equals("77777777777777") || valor.equals("88888888888888")
				|| valor.equals("99999999999999") || (valor.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o cÃ³digo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-Ã©simo caractere do valor em um nÃºmero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posiÃ§Ã£o de '0' na tabela ASCII)
				num = (int) (valor.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (valor.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os dÃ­gitos calculados conferem com os dÃ­gitos
			// informados.
			if ((dig13 == valor.charAt(12)) && (dig14 == valor.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}
}
