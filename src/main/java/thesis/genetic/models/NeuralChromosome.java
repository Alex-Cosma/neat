package thesis.genetic.models;

import org.jenetics.Chromosome;
import org.jenetics.util.ISeq;
import thesis.genetic.processing.ChromosomeCreators;
import thesis.genetic.processing.ChromosomeExtractors;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.models.Neuron;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static thesis.neural.models.NeuronType.INPUT;
import static thesis.neural.models.NeuronType.OUTPUT;

/**
 * Created by Alex on 12/07/2016.
 */
public class NeuralChromosome implements Chromosome<NeuralGene>, Serializable {

    private final ISeq<NeuralGene> genes;
    private final int nrInputNeurons;
    private final int nrOutputNeurons;
    private final NetworkIdMap networkIdMap;

    private NeuralChromosome(ISeq<NeuralGene> genes, NetworkIdMap networkIdMap) {
        this.genes = ISeq.of(new HashSet<>(genes.asList().stream().map(NeuralGene::newInstance).collect(Collectors.toList())));
        this.networkIdMap = networkIdMap;
        ISeq<Neuron> neurons = ISeq.of(ChromosomeExtractors.extractNeuronsFromGenes(this.genes));
        this.nrInputNeurons = (int) neurons.stream().filter(it -> it.getNeuronType().equals(INPUT)).count();
        this.nrOutputNeurons = (int) neurons.stream().filter(it -> it.getNeuronType().equals(OUTPUT)).count();
    }

    /**
     * Base constructor for initially creating only an input/output chromosome
     */
    private NeuralChromosome(int nrInputNeurons, int nrOutputNeurons, NetworkIdMap networkIdMap) {
        this.nrInputNeurons = nrInputNeurons;
        this.nrOutputNeurons = nrOutputNeurons;
        this.networkIdMap = networkIdMap;

        List<Neuron> inputNeurons = ChromosomeCreators.createNeurons(nrInputNeurons, INPUT, 1);
        List<Neuron> outputNeurons = ChromosomeCreators.createNeurons(nrOutputNeurons, OUTPUT, nrInputNeurons + 1);
        List<NeuralGene> geneList = ChromosomeCreators.createGeneList(inputNeurons, outputNeurons, networkIdMap);

        this.genes = ISeq.of(geneList);
    }

    public static NeuralChromosome of(int nrInputNeurons, int nrOutputNeurons, NetworkIdMap networkIdMap) {
        return new NeuralChromosome(nrInputNeurons, nrOutputNeurons, networkIdMap);
    }

    public static NeuralChromosome of(ISeq<NeuralGene> genes, NetworkIdMap networkIdMap) {
        return new NeuralChromosome(genes, networkIdMap);
    }

    @Override
    public Chromosome<NeuralGene> newInstance(ISeq<NeuralGene> genes) {
        return new NeuralChromosome(genes, networkIdMap);
    }

    @Override
    public Chromosome<NeuralGene> newInstance() {
        return new NeuralChromosome(nrInputNeurons, nrOutputNeurons, networkIdMap);
    }

    @Override
    public NeuralGene getGene(int index) {
        return genes.get(index);
    }

    @Override
    public int length() {
        return genes.length();
    }

    @Override
    public ISeq<NeuralGene> toSeq() {
        return genes;
    }

    @Override
    public Iterator<NeuralGene> iterator() {
        return genes.iterator();
    }

    @Override
    public boolean isValid() {
        ISeq<Neuron> neurons = ISeq.of(ChromosomeExtractors.extractNeuronsFromGenes(genes));
        List<Integer> neuronIds = neurons.stream().mapToInt(Neuron::getId).boxed().collect(Collectors.toList());
        return neuronIds.size() == new HashSet<>(neuronIds).size(); // basically checking if neurons are unique
    }

}
