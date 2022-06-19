package thesis.preservation;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.jenetics.Genotype;
import org.jenetics.Phenotype;
import thesis.configuration.genetic.Configuration;
import thesis.configuration.preservation.PreservationConfiguration;
import thesis.game.model.Game;
import thesis.genetic.models.NeuralGene;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.TreeSet;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Created by Alex on 06/08/2016.
 */
public class PreservationService {

    private final Game game;
    private final PreservationConfiguration preservationConfiguration;

    public PreservationService(Game game, PreservationConfiguration preservationConfiguration) {
        this.game = game;
        this.preservationConfiguration = preservationConfiguration;
    }

    public Genotype<NeuralGene> retrieveLatestGenotype(Game game) {
        String individualsDirectory = preservationConfiguration.getIndividualsDirectory();
        String gameIncubatorDirectory = game.getTitle();
        try {

            TreeSet<File> files = new TreeSet<>(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            listFiles(Paths.get(createDirectoryTree(individualsDirectory, gameIncubatorDirectory)), files);

            return loadLatestGenotype(files);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void preserve(Phenotype<NeuralGene, Double> bestPhenotype) {
        if (preservationConfiguration.preserveBestPhenotype()) {
            prsrv(bestPhenotype);
        }
    }

    public void preserve(Configuration configuration) {
        if (preservationConfiguration.preserveConfiguration()) {
            prsrv(configuration);
        }
    }

    private void prsrv(Phenotype<NeuralGene, Double> bestPhenotype) {
        try {
            String individualsDirectory = preservationConfiguration.getIndividualsDirectory();
            String gameIncubatorDirectory = game.getTitle();
            String today = now().format(ofPattern(preservationConfiguration.getPreservationGameDirectoryDailyFormat()));

            String directory = createDirectoryTree(individualsDirectory, gameIncubatorDirectory, today);

            File[] files = new File(directory).listFiles();
            int numberOfPreviousIndividuals = 0;
            if (files != null) {
                numberOfPreviousIndividuals = files.length;
            }

            String individualCardinality = directory + numberOfPreviousIndividuals;

            saveIndividual(bestPhenotype, individualCardinality);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prsrv(Configuration configuration) {
        try {
            String configurationsDirectory = preservationConfiguration.getConfigurationsDirectory();
            String gameIncubatorDirectory = game.getTitle();
            String today = now().format(ofPattern(preservationConfiguration.getPreservationGameDirectoryDailyFormat()));

            String directory = createDirectoryTree(configurationsDirectory, gameIncubatorDirectory, today);

            File[] files = new File(directory).listFiles();
            int numberOfPreviousIndividuals = 0;
            if (files != null) {
                numberOfPreviousIndividuals = files.length;
            }

            String individualCardinality = directory + numberOfPreviousIndividuals;

            saveConfiguration(configuration, individualCardinality);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveIndividual(Phenotype<NeuralGene, Double> bestPhenotype, String individualCardinality) throws IOException {
        String filename = formatIndividualFilename(bestPhenotype, individualCardinality);
        saveAsObject(bestPhenotype.getGenotype(), filename);
    }

    private void saveConfiguration(Configuration configuration, String configurationCardinality) throws IOException {
        String filename = formatConfigurationFilename(configuration, configurationCardinality);
        saveAsString(configuration, filename);
    }

    private void saveAsObject(Object obj, String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(obj);
    }

    private void saveAsString(Object c, String filename) throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream(filename));
        out.print(c.toString());
        out.close();
    }

    private String createDirectoryTree(String... paths) throws IOException {
        String tree = "";
        for (String path : paths) {
            if (!Files.exists(Paths.get(tree + path))) {
                Files.createDirectory(Paths.get(tree + path));
            }
            tree += path;
            tree += "/";
        }
        return tree;
    }


    private String formatIndividualFilename(Phenotype<NeuralGene, Double> bestPhenotype, String individualCardinality) {
        return String.format("%s - genes#%d - gen#%d - (%.5f).ind", individualCardinality, bestPhenotype.getGenotype().getChromosome().length(), bestPhenotype.getGeneration(), bestPhenotype.getFitness());
    }

    private String formatConfigurationFilename(Configuration configuration, String configurationCardinality) {
        return String.format("%s - pop#%d.prop", configurationCardinality, configuration.getPopulationSize());
    }

    private void listFiles(Path path, Collection<File> files) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    listFiles(entry, files);
                }
                files.add(entry.toFile());
            }
        }
    }

    private Genotype<NeuralGene> loadLatestGenotype(TreeSet<File> files) throws IOException, ClassNotFoundException {
        return loadGenotype(files.first().getPath());
    }

    @SuppressWarnings("unchecked")
    private Genotype<NeuralGene> loadGenotype(String genotypePath) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(genotypePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Genotype<NeuralGene> genotype = (Genotype<NeuralGene>) objectInputStream.readObject();
        objectInputStream.close();
        return genotype;
    }
}
