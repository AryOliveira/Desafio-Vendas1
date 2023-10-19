package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Sale> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				list.add(new Sale(month, year, seller, items, total));
				line = br.readLine();

			}
			// Análise 1: Cinco primeiras vendas de 2016 de maior preço médio, ordenadas
			// decrescentemente por preço médio
			List<Sale> sales2016 = list.stream()
					.filter(x -> x.getYear() == 2016)
					.sorted(Comparator.comparing(Sale::averagePrice)
					.reversed())
					.limit(5)
					.toList();

			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");
			sales2016.forEach(System.out::println);

			// Análise 2: Valor total vendido pelo vendedor Logan nos meses 1 e 7 de
			// qualquer ano
			
			Double totalLogan = list.stream()
					.filter(x -> x.getSeller().equals("Logan"))
					.filter(x -> x.getMonth() >= 1 && x.getMonth() <= 7) 
				    //filter(x -> x.getYear() == 2016) 
					.mapToDouble(Sale::getTotal).sum();					
			
			System.out.println();
			System.out.println("Valor total vendido por Logan nos meses 1 e 7 =  " + String.format("%.2f", totalLogan));

		} catch (IOException e) {
			System.err.println("Erro: " + e.getMessage());
		}
		sc.close();
	}
}

/*
 * 
 * // Filtrar vendas de 2016
 * 
 * List<Sale> sales2016 = sales2016.stream().filter(sale ->
 * sale.getMonth().toString().startsWith("2016")) .collect(Collectors.toList());
 * 
 * // Cinco primeiras vendas de 2016 de maior preço médio List<Sale>
 * top5Sales2016 = sales2016.stream().sorted((s1, s2) ->
 * s2.averagePrice().compareTo(s1.averagePrice()))
 * .limit(5).collect(Collectors.toList());
 * 
 * System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");
 * top5Sales2016.forEach(System.out::println);
 * 
 * Collection<Sale> sale = null; // Valor total vendido pelo vendedor Logan nos
 * meses 1 e 7 de qualquer ano Double loganSalesValue = sale.stream().filter(x
 * -> x.getSeller().equals("Logan")) .filter(x -> x.getMonth() == 1 ||
 * x.getMonth() == 7).mapToDouble(Sale::getTotal).sum();
 * 
 * System.out.
 * printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f\n",
 * loganSalesValue);
 * 
 */
