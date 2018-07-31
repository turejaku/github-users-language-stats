
/*
 * @author: Jakub Turek
 * GitHub: turejaku
 * 2018
 */

import java.io.IOException;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;

public class App {

	public String getGreeting() {
		return "GitHub user\'s langugages statistics for public repositories \n \n";
	}

	public static void main(String[] args) throws IOException {
		System.out.println(new App().getGreeting());
		String username= JOptionPane.showInputDialog("GitHub username:");
		System.out.println("Username: " + username);
		Map<String, Long> languages = new TreeMap<>();
		List<String> keys = new ArrayList<String>();
		List<Long> values = new ArrayList<Long>();
		RepositoryService service = new RepositoryService();
		System.out.println("\nNames of user\'s repositories: ");
		for (Repository repo : service.getRepositories(username))
			System.out.println(repo.getName());

		for (Repository repo : service.getRepositories(username)) {
			List<Repository> repolist = new ArrayList<Repository>();
			repolist.add(repo);
			languages = service.getLanguages(repo);
			for (Map.Entry<String, Long> mapEntry : languages.entrySet()) {
				keys.add(mapEntry.getKey());
				values.add(mapEntry.getValue());

			}
			languages.clear();

		}
		long sum = 0;
		for (int i = 0; i < values.size(); i++)
			sum = sum + values.get(i);
		
		Map<String, Double> languagesPercentage = new TreeMap<>();
		for (int i = 0; i < keys.size(); i++) {
			double val = 0;
			languagesPercentage.put(keys.get(i), val);
		}

		for (Map.Entry<String, Double> mapEntry : languagesPercentage.entrySet()) {
			for (int i = 0; i < keys.size(); i++) {
				if (((Comparable<String>) mapEntry.getKey()).compareTo(keys.get(i)) == 0) {
					mapEntry.setValue((double)mapEntry.getValue() + (double)values.get(i));
				}
			}

		}

		

		for (Map.Entry<String, Double> mapEntry : languagesPercentage.entrySet()) {
			double tempValue = (double) Math.round((mapEntry.getValue()/(double)sum*100.0)*1000)/1000;
			mapEntry.setValue(tempValue);
		}
		keys.clear();
		List<Double> valuesOfPercentage = new ArrayList<Double>();
		for (Map.Entry<String, Double> mapEntry : languagesPercentage.entrySet()) {
			keys.add(mapEntry.getKey());
			valuesOfPercentage.add(mapEntry.getValue());
		}
		System.out.println("\nStatistics: ");
		for(int i=0; i<keys.size(); i++)
			System.out.println(keys.get(i)+ ": " + valuesOfPercentage.get(i)+"%");
	}
}
