package challenge;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	//File name
	public static final String PATH_FILE = "data.csv";

	// Quantas nacionalidades (coluna `nationality`) diferentes existem no arquivo?
	public int q1() throws IOException {

		List<String> nationalities = new ArrayList<String>();
		CSVReader csvReader = new CSVReader(PATH_FILE);

		//index of the nationality colum
		int indexOfNationality = csvReader.columns.indexOf("nationality");

		//init stream object
		csvReader.initStream();

		//iterate over the lines
		csvReader.csvFile.skip(1).forEach(it -> {
			String nat = it.toString().split(",")[indexOfNationality];
			if(!nationalities.contains(nat))
				nationalities.add(nat);
		});

		return nationalities.size();
	}

	// Quantos clubes (coluna `club`) diferentes existem no arquivo?
	// Obs: Existem jogadores sem clube.
	public int q2() throws IOException {

		List<String> values = new ArrayList<String>();
		CSVReader csvReader = new CSVReader(PATH_FILE);

		//index of the club colum
		int index = csvReader.columns.indexOf("club");

		//init stream object
		csvReader.initStream();

		//iterate over the lines
		csvReader.csvFile.skip(1).forEach(it -> {
			String club = it.toString().split(",")[index];
			if(!values.contains(club))

				if(!club.isEmpty()) {
					values.add(club);
				}
		});

		return values.size();
	}

	// Liste o primeiro nome (coluna `full_name`) dos 20 primeiros jogadores.
	public List<String> q3() throws IOException {

		CSVReader csvReader = new CSVReader(PATH_FILE);
		List<String> values = new ArrayList<String>();

		//index of the full_name colum
		int index = csvReader.columns.indexOf("full_name");

		//init stream object
		csvReader.initStream();

		//iterate over the lines
		csvReader.csvFile.skip(1).limit(20).forEach(it -> {
			String name = it.toString().split(",")[index];
			if(!values.contains(name))
				try {
					name = new String(name.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			values.add(name);
		});

		return values;
	}

	// Quem são os top 10 jogadores que possuem as maiores cláusulas de rescisão?
	// (utilize as colunas `full_name` e `eur_release_clause`)
	public List<String> q4() throws IOException {

		CSVReader csvReader = new CSVReader(PATH_FILE);

		List<String> values = new ArrayList<String>();

		//index of the nationality colum
		int index = csvReader.columns.indexOf("eur_release_clause");

		//init stream object
		csvReader.initStream();

		//where the magic happens
		List<String> col = (List<String>) csvReader.csvFile.skip(1).sorted(Comparator.comparing(o -> {
			String value = o.toString().split(",")[index];
			if (value.equals("")) {
				return Double.parseDouble("0");
			} else {
				return Double.parseDouble(value);
			}
		}).reversed()).collect(Collectors.toList());

		//limit the first 10 lines
		col.stream().limit(10)
		.forEach(it -> {
			try {
				values.add(new String(it.split(",")[2].getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});

		return values;		
	}

	// Quem são os 10 jogadores mais velhos (use como critério de desempate o campo `eur_wage`)?
	// (utilize as colunas `full_name` e `birth_date`)
	public List<String> q5() throws IOException {

		CSVReader csvReader = new CSVReader(PATH_FILE);

		List<String> values = new ArrayList<String>();

		//index of the full_name and birth_date colum
		int birth_date = csvReader.columns.indexOf("birth_date");
		int fullName = csvReader.columns.indexOf("full_name");

		//init stream object
		csvReader.initStream();

		//where the magic happens
		List<String> col = (List<String>) csvReader.csvFile.skip(1).sorted(Comparator.comparing(o -> {
			String valueName = o.toString().split(",")[fullName];
			String valueDate = o.toString().split(",")[birth_date];

			LocalDate dataComoDate = LocalDate.parse(valueDate);

			return dataComoDate;

		})).collect(Collectors.toList());

		//limit the first 10 lines
		col.stream().limit(10)
		.forEach(it -> {
			try {
				values.add(new String(it.split(",")[2].getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		});

		return values;		
	}

	// Conte quantos jogadores existem por idade. Para isso, construa um mapa onde as chaves são as idades e os valores a contagem.
	// (utilize a coluna `age`)
	public Map<Integer, Integer> q6() throws IOException {

		CSVReader csvReader = new CSVReader(PATH_FILE);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		//index of the age colum
		int indexOfAge = csvReader.columns.indexOf("age");

		//init stream object
		csvReader.initStream();

		//iterate over the lines
		csvReader.csvFile.skip(1).forEach(it -> {
			String age = it.toString().split(",")[indexOfAge];
			int ageAux = Integer.parseInt(age);

			int contador = 0;

			if(!map.containsKey(ageAux)) {

				contador = contador + 1;
				map.put(ageAux, contador);

			} else {

				contador = map.get(ageAux);
				contador = contador + 1;

				map.replace(ageAux, contador);

			}
		});		

		return map;
	}

}

