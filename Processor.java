import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Processor {
	
	SurveyResult[] surveyResults;
	NumeratorCalculate nc;
	public Processor(SurveyResult[] surveyResults){
		this.surveyResults = surveyResults;	
		this.nc = new NumeratorCalculate();
	}
	
	// Get the indices of size most similar patients.
	public int[] getSeveralSimilarPatientsIndicesByPatients(SurveyResult[] targetPatients, SurveyResult chosenPatient, int size){
		List<SurveyResult> similarPatients = new ArrayList<SurveyResult>(); 
		int sizeOfSimilarPatient = 0;
		for(int i = 0; i < targetPatients.length; i++){
			double cocc = nc.getCorrelationCoefficient(chosenPatient.getAnswers(), targetPatients[i].getAnswers());
			if(similarPatients.isEmpty()){
				targetPatients[i].setSimilarity(cocc);
				similarPatients.add(targetPatients[i]);
			}
			else{
				sizeOfSimilarPatient = similarPatients.size();
				while(sizeOfSimilarPatient !=0 && cocc <= similarPatients.get(sizeOfSimilarPatient -1).getSimilarity()){
					sizeOfSimilarPatient--;
				}
				targetPatients[i].setSimilarity(cocc);
				similarPatients.add(sizeOfSimilarPatient, targetPatients[i]);
				
			}
		}
		int[] returnIndex = new int[size];
		if(similarPatients.size()>size){
			for(int i = 0; i < size ; i++){
			
				returnIndex[i] = similarPatients.get(i).getIndex();		
			}			
		}
		else{
			for(int i = 0; i < similarPatients.size() ; i++){
				
				returnIndex[i] = similarPatients.get(i).getIndex();		
			}
		}
		return returnIndex;
	}
	
	// Calculate the numbers of times of each index in the array, and return the most frequent one
	public int getTheBestPatientIndex(List<Integer> ar) {   
		  
	    List<Integer> outValue = new ArrayList<Integer>();   
	    int count = 0;   	   
	    int has = ar.size();   
	    int element;   
	    int tempCount;  
	    while (has > 0) {
	    	element = ar.get(has - 1);
	    	tempCount = 0;
	    	for (int j = has - 1; j >= 0; j--) {
	    		if (element == ar.get(j)) {
	    			tempCount++;
	    			ar.set(j, ar.get(has - 1));
	    			has--;
	    		}
	    	}   
	    	if (tempCount > count) {
	    		count = tempCount;
	    		outValue.clear();
	    		outValue.add(element);
	    	}
	    	else if (tempCount == count) {
	    		outValue.add(element);
	    	}   
	    }
	    System.out.println("The best patitents are :" + outValue);   
	    return outValue.get(0);	    
	  } 
	
	public int[] processData(SurveyResult[] targetPatients,SurveyResult chosenPatient ,int size){
		
		if(targetPatients.length >= size){
			return getSeveralSimilarPatientsIndicesByPatients(targetPatients, chosenPatient, size);
		}else{
			return getSeveralSimilarPatientsIndicesByPatients(targetPatients, chosenPatient, targetPatients.length);
		}
	}
	
	//Receives list of survey results, and index of the patient that’s been chosen for a tailored plan and returns indices of 5 most similar patients to the chosen one. 
	public int[] returnPatientsByResulteAndChosenPatient(SurveyResult[] targetPatients, int index){
		SurveyResult chosenPatient = surveyResults[index];
		return processData(targetPatients, chosenPatient, 5);
	}
	
	// Receives only a list of results, chooses a random patient and returns indices of 5 most similar patients to the chosen one.
	public int[] returnPatientsByResulteAndRandomPatient(SurveyResult[] results){
		int size = surveyResults.length;
		int index = (int)(Math.random()*(size)); // A random patient index.
		System.out.println("The random num is: " + index);
		SurveyResult chosenPatient = surveyResults[index];
		return processData(results, chosenPatient, 5);
	}
	
	// Receives the list of survey results returns “best” patient for a tailored plan.
	public int returnBestPatientsByResults(SurveyResult[] results, int size){

		List<Integer> tailoredPatients = new ArrayList<Integer>();
		for(int i = 0; i < surveyResults.length; i++){
			int[] tem = processData(results, surveyResults[1], size); 
			for(int a = 0; a < tem.length; a++){
				tailoredPatients.add(tem[a]);
			}
		}			
		return getTheBestPatientIndex(tailoredPatients);
	}

	
	
	
	public static void main(String[] args) throws IOException{
		SurveyResult[] surveyResult = new SurveyResult[10];
		SurveyResult[] targetPatients = new SurveyResult[5];
		for(int i = 0; i < surveyResult.length; i++){
			surveyResult[i] = new SurveyResult();
			surveyResult[i].setSimilarity(0.0);
			surveyResult[i].setIndex(i);
		}
		surveyResult[0].setAnswers(new String[]{"2","3","4","2","3","4","2","3","4","2","3","4","2","3","4"});
		surveyResult[1].setAnswers(new String[]{"2","3","4","2","3","4","2","3","4","2","3","4","2","3","4"});				
		surveyResult[2].setAnswers(new String[]{"0","3","4","2","3","4","2","3","4","2","3","3","2","3","4"});				
		surveyResult[3].setAnswers(new String[]{"2","3","3","4","3","4","2","3","5","2","3","4","2","3","4"});				
		surveyResult[4].setAnswers(new String[]{"2","3","4","2","3","34","4","3","4","2","3","4","2","3","4"});				
		surveyResult[5].setAnswers(new String[]{"2","3","4","2","3","4","2","5","4","2","3","4","2","3","4"});				
		surveyResult[6].setAnswers(new String[]{"2","3","4","1","3","4","2","3","4","2","3","4","2","3","4"});
		surveyResult[7].setAnswers(new String[]{"2","3","4","2","8","4","6","3","4","2","3","4","2","3","4"});
		surveyResult[8].setAnswers(new String[]{"2","3","4","2","3","2","2","3","4","2","3","4","2","3","4"});
		surveyResult[9].setAnswers(new String[]{"2","3","4","2","3","4","2","9","4","2","3","3","2","3","4"});
		
		for(int i = 0; i < 5; i++){
			targetPatients[i] = surveyResult[i];
		}
		
		Processor processor = new Processor(surveyResult);
		int[] result = processor.returnPatientsByResulteAndChosenPatient(targetPatients, 6);
		for(int i = 0; i < result.length; i++){
			 System.out.println("The result is " + result[i]); 
		}
		
		int[] result_2 = processor.returnPatientsByResulteAndRandomPatient(targetPatients);
		for(int i = 0; i < result_2.length; i++){
			 System.out.println("The result_2 is " + result_2[i]); 
		}
		int n = processor.returnBestPatientsByResults(targetPatients, 2);
		 System.out.println("The n is " + n); 
    }  
}

// Object can be used to calculate the similarity between arrays by Pearson product-moment correlation coefficient.
class NumeratorCalculate {
    
    /** 
     * add operate method 
     */  
    public double calcuteNumerator(String[] xList ,String[] yList){  
        double result =0.0;  
        double xAverage = 0.0;  
        double temp = 0.0;  
          
        int xSize = xList.length;  
        for(int x=0;x<xSize;x++){  
            temp += Double.parseDouble(xList[x]);  
        }  
        xAverage = temp/xSize;  
          
        double yAverage = 0.0;  
        temp = 0.0;
        int ySize = yList.length;  
        for(int x=0;x<ySize;x++){  
            temp += Double.parseDouble(yList[x]);  
        }  
        yAverage = temp/ySize;  
          
        //double sum = 0.0;  
        for(int x=0;x<xSize;x++){  
            result+=(Double.parseDouble(xList[x])-xAverage)*(Double.parseDouble(yList[x])-yAverage);  
        }  
        return result;  
    }
    
    public double calculateDenominator(String[] xList ,String[] yList){  
        double standardDifference = 0.0;  
        int size = xList.length;  
        double xAverage = 0.0;  
        double yAverage = 0.0;  
        double xException = 0.0;  
        double yException = 0.0;  
        double temp = 0.0;  
        for(int i=0;i<size;i++){  
            temp += Double.parseDouble(xList[i]);  
        }  
        xAverage = temp/size;  
          
        for(int i=0;i<size;i++){  
            temp += Double.parseDouble(yList[i]);  
        }  
        yAverage = temp/size;  
          
        for(int i=0;i<size;i++){  
            xException += Math.pow(Double.parseDouble(xList[i])-xAverage,2);  
            yException += Math.pow(Double.parseDouble(yList[i])-yAverage, 2);  
        }  
        //calculate denominator of   
        return standardDifference = Math.sqrt(xException*yException);  
    }
    public double getCorrelationCoefficient(String[] xList ,String[] yList){
    	double CORR = calcuteNumerator(xList, yList)/calculateDenominator(xList,yList);
    	return CORR;   	
    }
}


// Data structures to store survey results: patient name, date of birth and answers to questions.
class SurveyResult {
	private int index;
	private String name;
	private Date brithday;
	private String[] answers;
	private Double similarity ;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBrithdat() {
		return brithday;
	}
	public void setBrithdat(Date brithdat) {
		this.brithday = brithdat;
	}
	public String[] getAnswers() {
		return answers;
	}
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	public Double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}	
}