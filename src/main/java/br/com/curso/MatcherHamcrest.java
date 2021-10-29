package br.com.curso;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class MatcherHamcrest {

	@Test
	public void devoConhecerMatcherHamcrest() {
//		Lista completa dos matchers
//		http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html

		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));

		// Verifica se é do tipo Integer
		Assert.assertThat(128, Matchers.isA(Integer.class));

		// Verifica se é do tipo Double
		Assert.assertThat(128d, Matchers.isA(Double.class));

		// Verifica se 128d é maior que 130d
		Assert.assertThat(128d, Matchers.greaterThan(130d));

		// Verifica se 128d é menor que 130d - vai falhar
		Assert.assertThat(128d, Matchers.lessThan(130d));

		// Verificando Listas
		// Primeiro eu crio a lista
		List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
		// Verificando qual o tamanho da Lista
		// * importei as classes Assert e Matchers para n precisar indicar
		assertThat(impares, hasSize(5));

		// Verifica na ordem especifica
		assertThat(impares, contains(1, 3, 5, 7, 9));

		// Verifica em qualquer ordem
		assertThat(impares, containsInAnyOrder(1, 3, 5, 7, 9));

		// Verifica somente se existe um elemento especifico
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1, 5));

		// Forma diferentes de fazer a mesma asserção de comparação por diferença
		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));

		// Asserção do tipo Qualquer um desses -tipo OU - Primeiro parametro é o expect
		assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
		assertThat("Luiz", anyOf(is("Maria"), is("Joaquina")));

		// Asserção do tipo Todos esses - tipo E - Primeiro parametro é o expect
		assertThat("Maria", allOf(is("Maria"), is("Joaquina")));

		// Asserção com cumulando vários tipos de verificação
		assertThat("Joaquina", allOf(startsWith("João"), endsWith("ima"), containsString("quina")));

	}
}
