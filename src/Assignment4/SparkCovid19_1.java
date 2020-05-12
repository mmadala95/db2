package Assignment4;/* Java imports */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import scala.Tuple2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Spark imports */

public class SparkCovid19_1 {


    /**
     * args[0]: Input file path on distributed file system
     * args[1]: Output file path on distributed file system
     */
    public static void main(String[] args){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = simpleDateFormat.parse(args[1]);
            Date endDate = simpleDateFormat.parse(args[2]);
            String input = args[0];
            String output = args[3];

            /* essential to run any spark code */
            SparkConf conf = new SparkConf().setAppName("Covid19_2").setMaster("local");
            JavaSparkContext sc = new JavaSparkContext(conf);

            /* load input data to RDD */
            JavaRDD<String> dataRDD = sc.textFile("hdfs://localhost:9000"+input);
            String header = dataRDD.first();

            dataRDD = dataRDD.filter(line -> !line.equals(header) );
            JavaPairRDD<String, Long> counts =dataRDD.filter(new Function<String, Boolean>() {
                @Override
                public Boolean call(String s) throws Exception {
                    String[] entries=s.split(",");
                    Date currentDate =simpleDateFormat.parse(entries[0]);
                    if(!currentDate.before(startDate) && !currentDate.after(endDate)){
                        return true;
                    }
                    return false;
                }
            }).mapToPair(new PairFunction<String, String, Long>(){
                    public Tuple2<String, Long> call(String value){
                        String[] entries = value.split(",");


                        return new Tuple2<String, Long>(entries[1], Long.parseLong(entries[3]));
                    }

                    }).reduceByKey(new Function2<Long, Long, Long>(){
                        public Long call(Long x, Long y){
                            return x+y;
                        }
                    }).sortByKey();


            counts.saveAsTextFile("hdfs://localhost:9000"+output);

        }
        catch (ParseException e)
        {
            System.out.println("invalid date entered ");
            System.exit(0);

        }



    }
}
