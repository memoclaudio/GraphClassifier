import java.util.Random;

import Classifier.WekaClassification;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class MainClass {

	public static void main(String[] args) {
		try {
			String instancesPath = args[0];
			String graphDir = args[1];
			double C=1.0;
			double epsilon = 1.0E-12;
			
			DataSource source;
			source = new DataSource(instancesPath);

			Instances data = source.getDataSet();
			NumericToNominal filter = new NumericToNominal();
			filter.setInputFormat(data);
			data = Filter.useFilter(data, filter);
			data.setClassIndex(data.numAttributes()-1);
			
			SMO smo = WekaClassification.generateClassifier(data, graphDir, C, epsilon);
			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(smo, data, data.numInstances(), new Random(0));
			System.out.println(eval.toSummaryString("\nResults\n======\n", false));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in DataSource Instantiation");
		}
	}

}
