package Assignment4;/* Java imports */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* Spark imports */

public class SparkWordCount {

    
    /**
     * args[0]: Input file path on distributed file system
     * args[1]: Output file path on distributed file system
     */
    public static void main(String[] args){

	System.out.println("Hello World from Java");

	String input = args[0];
	String output = args[1];
	
	/* essential to run any spark code */
	SparkConf conf = new SparkConf().setAppName("WordCount").setMaster("local");
	JavaSparkContext sc = new JavaSparkContext(conf);

	/* load input data to RDD */
	JavaRDD<String> dataRDD = sc.textFile("hdfs://localhost:9000"+input);

	JavaPairRDD<String, Integer> counts =
	    dataRDD.flatMapToPair(new PairFlatMapFunction<String, String, Integer>(){
		    public Iterator<Tuple2<String, Integer>> call(String value){
			String[] words = value.split(" ");
			
			List<Tuple2<String, Integer>> retWords =
		     	new ArrayList<Tuple2<String, Integer>>();
			
			for (String word:words){
			    retWords.add(new Tuple2<String, Integer>(word, 1));
			}
			
			return retWords.iterator();
		    }
		}).reduceByKey(new Function2<Integer, Integer, Integer>(){
			public Integer call(Integer x, Integer y){
			    return x+y;
			}
		    });
	
	counts.saveAsTextFile("hdfs://localhost:9000"+output);
	
    }
}
