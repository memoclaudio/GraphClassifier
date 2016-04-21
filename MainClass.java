import java.util.Random;

import Classifier.WekaClassification;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MainClass {

	public static void main(String[] args) {
		try {
			String instancesPath = "";
			String graphDir = "";
			double C=1;
			double epsilon = 0.001;
			
			DataSource source;
			source = new DataSource(instancesPath);

			Instances data = source.getDataSet();
			data.setClassIndex(data.numAttributes()-1);
			
			WekaClassification.buildKernel("/Users/stamile/Dropbox/AMSEP_connect/Matrices/new/");
			SMO smo = WekaClassification.generateClassifier(data, graphDir, C, epsilon);
			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(smo, data, 10, new Random(1));
			System.out.println(eval.toSummaryString("\nResults\n======\n", false));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in DataSource Instantiation");
		}
	}

}
