package com.devinhouse.testesunitarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FolhaDePagamentoTest {
    private FolhaDePagamento folha;

    @BeforeEach
    public void setup() { folha = new FolhaDePagamento(); }

    @Nested
    @DisplayName("calcularSalarioBruto")
    class calcularSalarioBrutoTests {
        @Test
        @DisplayName("Quando não informado gratificação nem função 'gerente' deve retornar o salario base")
        void calcularSalarioBruto_salarioBase() {
            Double salarioBase = 2800.0;
            Double gratificacao = null;
            String funcao = "consultor";

            Double salarioBruto = folha.calcularSalarioBruto(salarioBase, gratificacao, funcao );

            assertNotNull(salarioBruto);
            assertEquals(salarioBase, salarioBruto);
        }

        @Test
        @DisplayName("Quando informado gratificacao deve retornar salarioBase mais gratificacao")
        void calcularioSalarioBruto_gratificacao() {
            Double salarioBase = 2800.0;
            Double gratificacao = 1000.0;
            String funcao = "consultor";

            Double salarioBruto = folha.calcularSalarioBruto(salarioBase, gratificacao, funcao);

            assertTrue(salarioBase < salarioBruto);
            assertEquals(salarioBase + gratificacao, salarioBruto);
        }

        @Test
        @DisplayName("Quando informada função 'gerente' deve retornar o salarioBase mais o percentual previsto")
        void calcularSalarioBruto_gerente() {
            Double salarioBase = 2800.0;
            Double gratificacao = null;
            String funcao = "gerente";

            Double salarioBruto = folha.calcularSalarioBruto(salarioBase, gratificacao, funcao);

            assertTrue(salarioBase < salarioBruto);
            assertEquals(salarioBase * 1.3, salarioBruto);
        }

        @Test
        @DisplayName("Quando informada função 'gerente' e gratificacao, deve retornar salarioBase mais os valores previstos")
        void calcularSalarioBruto_gerenteGratificacao() {
            Double salarioBase = 2800.0;
            Double gratificacao = 10000.5;
            String funcao = "gerente";

            Double salarioBruto = folha.calcularSalarioBruto(salarioBase, gratificacao, funcao);

            assertTrue(salarioBase < salarioBruto);
            assertEquals((salarioBase + gratificacao) * 1.3, salarioBruto);
        }
    }

    @Nested
    @DisplayName("calcularSalarioLiquido")
    class calcularSalarioLiquidoTests {
        @Test
        @DisplayName("Quando não houver desconto deve retornar o salario")
        void calcularSalarioLiquido_semDesconto() {
            Double salarioBruto = 9500.0;
            Double salarioLiquido = folha.calcularSalarioLiquido(salarioBruto, new ArrayList<>());
            assertEquals(salarioBruto, salarioLiquido);
        }

        @Test
        @DisplayName("Quando informado descontos, deve retornar o salario menos a soma de descontos")
        void calcularSalarioLiquido_descontos() {
            //given
            Double salarioBruto = 9500.0;
            List<Double> descontos = List.of(250.0, 300.0);
            Double descontosTotal = descontos.stream().reduce(Double::sum).get();

            //when
            Double salarioLiquido = folha.calcularSalarioLiquido(salarioBruto, descontos);

            //then
            assertEquals(salarioBruto - descontosTotal, salarioLiquido);
            assertTrue(salarioBruto > salarioLiquido);
        }

        @Test
        @DisplayName("Quando a soma dos descontos for maior que o salário deve lançar exceção")
        void calcularSalarioLiquido_descontosMaiorQueSalario() {
            //given
            Double salarioBruto = 2500.0;
            List<Double> descontos = List.of(2000.0, 500.1);
            assertThrows(IllegalStateException.class, () -> folha.calcularSalarioLiquido(salarioBruto, descontos));
        }

    }

}