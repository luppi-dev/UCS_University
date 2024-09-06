package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TDE_files {

    private static final String MOVIES_FILE_ADDRESS = ""; //your file pathname here
    private static final String DIRECTORS_FILE_ADDRESS = ""; //your file pathname here

    public static void main(String[] args) throws FileNotFoundException {
        Map<Long, List<Director>> directorMap = readDirectors();
        List<Movie> movies = readMoviesFile(directorMap);



        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println();
            System.out.println("Funcionalidades:");
            System.out.println("1 > Listar todos os filmes");
            System.out.println("2 > Listar os filmes e seus diretores");
            System.out.println("3 > Listar todos os diretores");
            System.out.println("4 > Sair");

            int option = scanner.nextInt();
            switch (option) {
                case 1 -> listAllMovies(movies);
                case 2 -> listMoviesAndDirectors(movies);
                case 3 -> listAllDirectors(directorMap.values());
                default -> System.exit(0);
            }
        }
    }

    private static void listAllMovies(List<Movie> movies) {
        movies.forEach(movie -> System.out.println(movie.toString()));
    }
    private static void listAllDirectors(Collection<List<Director>> directors) {
        Set<String> directorsNameSet = new HashSet<>();
        directors.forEach(diretorList -> diretorList.forEach(director -> directorsNameSet.add(director.getName())));

        directorsNameSet.stream()
                .sorted()
                .forEach(name -> {
                    System.out.println("Nome: " + name);
                    System.out.println("------");
                });
    }
    private static void listMoviesAndDirectors(List<Movie> movies) {
        movies.stream()
                .filter(movie -> !movie.getDirectors().isEmpty())
                .forEach(movie -> {
                    System.out.println("------");
                    System.out.println("Filme: " + movie.getName());
                    listDirectors(movie);
                    System.out.println("------");
                });
    }
    private static void listDirectors(Movie movie) {
        StringBuilder stringBuilder = new StringBuilder();
        if(movie.getDirectors().size() > 1) {
            movie.getDirectors().forEach(director -> {
                stringBuilder.append(director.getName());
                stringBuilder.append(", ");
            });
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), ".");
            System.out.println("Directors: " + stringBuilder);
        } else {
            System.out.println("Director: " + movie.getDirectors().get(0).getName());
        }
    }

    private static List<Movie> readMoviesFile(Map<Long, List<Director>> directorsByMovie) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(MOVIES_FILE_ADDRESS));
        Map<String, Movie> moviesMap = new HashMap<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");

            final int id = Integer.parseInt(parts[0]);

            String name = null;
            if(parts.length >= 2) name = parts[1];

            Integer year = null;
            if(parts.length >= 3) year = Integer.parseInt(parts[2]);

            List<Director> directors = new ArrayList<>();
            if(directorsByMovie.containsKey((long) id))
                directors.addAll(directorsByMovie.get((long) id));

            Movie movie = new Movie(id, name, year, directors);

            if(!moviesMap.containsKey(name)) moviesMap.put(name, movie);
        }
        scanner.close();
        return new ArrayList<>(moviesMap.values());
    }

    private static Map<Long, List<Director>> readDirectors() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(DIRECTORS_FILE_ADDRESS));
        Map<Long, List<Director>> directorsByMovies = new HashMap<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            final long movieId = Long.parseLong(parts[0]);
            final String name = parts[1];

            Director director = new Director(name);

            if(directorsByMovies.containsKey(movieId))
                directorsByMovies.get(movieId).add(director);
            else {
                List<Director> list = new ArrayList<>();
                list.add(director);
                directorsByMovies.put(movieId, list);
            }


        }
        scanner.close();
        return directorsByMovies;
    }
}
