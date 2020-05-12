package Assignment4;/* Java imports */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;

/* Spark imports */

public class SparkCovid19_2 {


    /**
     * args[0]: Input file path on distributed file system
     * args[1]: Output file path on distributed file system
     */
    public static void main(String[] args){

        String input = args[0];
        String input_2=args[1];
        String output = args[2];

        /* essential to run any spark code */
        SparkConf conf = new SparkConf().setAppName("Covid19_2").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        /* load input data to RDD */
        JavaRDD<String> populationRDD=sc.textFile("hdfs://localhost:9000"+input_2);
        String headerPopulation = populationRDD.first();
        populationRDD=populationRDD.filter(line->!line.equals(headerPopulation));

        JavaPairRDD<String, Long> populationMapRDD=populationRDD.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                String[] entries=s.split(",");
                if (!entries[0].equals("Bonaire") && entries.length >4) {
                    return true;
                }
                return false;
            }
        }).mapToPair(new PairFunction<String, String, Long>() {
            @Override
            public Tuple2<String, Long> call(String value){
                String[] entries=value.split(",");
                return new Tuple2<String, Long>(entries[1], Long.parseLong(entries[4]));
            }

        });

        Map<String, Long> map=populationMapRDD.collectAsMap();
        Broadcast<Map<String, Long>> broadcastData=sc.broadcast(map);


        JavaRDD<String> dataRDD = sc.textFile("hdfs://localhost:9000"+input);
        String header = dataRDD.first();

        dataRDD = dataRDD.filter(line -> !line.equals(header) );
        JavaPairRDD<String, Double> counts =dataRDD.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                String[] entries=s.split(",");
                if(broadcastData.getValue().containsKey(entries[1])&& broadcastData.getValue().get(entries[1])>0) {
                    return true;
                }
                return false;
            }
        }).mapToPair(new PairFunction<String, String, Long>(){
            public Tuple2<String, Long> call(String value){
                String[] entries = value.split(",");
                return new Tuple2<String, Long>(entries[1], Long.parseLong(entries[2]));
            }

        }).reduceByKey(new Function2<Long, Long, Long>(){
            public Long call(Long x, Long y){
                return x+y;
            }
        }).mapToPair(new PairFunction<Tuple2<String, Long>, String, Double>() {
            @Override
            public Tuple2<String, Double> call(Tuple2<String, Long> stringLongTuple2) throws Exception {
                String key=stringLongTuple2._1.toString();
                Long sum=stringLongTuple2._2;
                Long population=broadcastData.getValue().get(key);

                return new Tuple2<String, Double>(key, (((double) sum / population) * 1000000));
            }
        }).sortByKey();

//        System.out.print(counts.collect());




           counts.saveAsTextFile("hdfs://localhost:9000"+output);






    }
}
