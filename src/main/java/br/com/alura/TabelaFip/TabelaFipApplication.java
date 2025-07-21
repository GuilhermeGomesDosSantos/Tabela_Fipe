package br.com.alura.TabelaFip;

import br.com.alura.TabelaFip.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelaFipApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TabelaFipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();

		principal.exibirMenu();

	}
}
