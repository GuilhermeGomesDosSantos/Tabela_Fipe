package br.com.alura.TabelaFip.principal;

import br.com.alura.TabelaFip.model.Dados;
import br.com.alura.TabelaFip.model.Dados;
import br.com.alura.TabelaFip.model.Modelos;
import br.com.alura.TabelaFip.model.Veiculo;
import br.com.alura.TabelaFip.service.ConsumoAPI;
import br.com.alura.TabelaFip.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    ConsumoAPI consumo = new ConsumoAPI();
    private Scanner leitura = new Scanner(System.in);

    private String endereco;

    private ConverteDados conversor = new ConverteDados();

    public void exibirMenu(){
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma dar opções para consulta:
                """;

        System.out.println(menu);

        var opcao = leitura.nextLine();

        if(opcao.toLowerCase().startsWith("ca")){
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().startsWith("mot")){
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);

        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";

        json = consumo.obterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream().sorted(Comparator.comparing(m -> m.codigo()))
                .forEach(System.out::println);

        System.out.println("Digite um trecho do nome do veículo para consulta: ");
        var trechoVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream().filter(c -> c.nome().toLowerCase().contains(trechoVeiculo.toLowerCase())).collect(Collectors.toList());

        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para consultar valores: ");

        var codModelo = leitura.nextLine();

        endereco = endereco + "/" + codModelo + "/anos";

        json = consumo.obterDados(endereco);

        List<Dados> listaAnos = conversor.obterLista(json, Dados.class);
//        listaAnos.forEach(System.out::println);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < listaAnos.size(); i++) {
            var enderecoAnos = endereco + "/" + listaAnos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        veiculos.forEach(System.out::println);
    }

}
