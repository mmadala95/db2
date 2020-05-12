package Assignment4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Covid19_3 {

    // 4 types declared: Type of input key, type of input value, type of output key, type of output value
    public static class MyMapper extends Mapper<Object, Text, Text, DoubleWritable> {
        private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        private final static DoubleWritable count = new DoubleWritable();

        private Text country = new Text();



        // The 4 types declared here should match the types that was declared on the top
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            try{
                String[] entries= value.toString().split(",");
                if(!entries[1].equals("location")){



                        country.set(entries[1]);
                        count.set(Double.parseDouble(entries[2]));
                        context.write(country,count);

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }





        }

    }



    // 4 types declared: Type of input key, type of input value, type of output key, type of output value
    // The input types of reduce should match the output type of map
    public static class MyReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
        private DoubleWritable total = new DoubleWritable();
        private Path path;

        // Notice the that 2nd argument: type of the input value is an Iterable collection of objects
        //  with the same type declared above/as the type of output value from map
        public void setup(Context context) throws IOException {

            URI[] files = context.getCacheFiles();
            if (files != null && files.length > 0)
            {

                    path = new Path(files[0].toString());

            }


        }
        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {

                try {
                    FileSystem fs = FileSystem.get(context.getConfiguration());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(path)));
                    String line = null;
                    double population = 0;
                    Set<String> set=new HashSet<String>(Arrays.asList("countriesAndTerritories","Bonaire"));
                    while ((line = reader.readLine()) != null) {

                        String[] entries = line.split(",");
                        if (!set.contains(entries[0]) && entries[1].equals(key.toString()) && entries.length >4) {
                            population = Double.parseDouble(entries[4]);
                            break;
                        }
                    }
                    reader.close();
                    if (population > 0) {
                        long sum = 0;
                        for (DoubleWritable tmp : values) {
                            sum += tmp.get();
                        }
//                        System.out.println(population + "..." + sum + "..." + key.toString());
                        double casesPerMil = (((double) sum / population) * 1000000);
                        total.set(casesPerMil);

                        context.write(key, total);
                    }
                }
                    catch (Exception e){
                        e.printStackTrace();

            }
        }
    }


    public static void main(String[] args)  throws Exception {
        Configuration conf = new Configuration();
        conf.set("isWorld",args[1]);
        Job myjob = Job.getInstance(conf, "covid_19 count");
        myjob.getConfiguration().set("fs.default.name", "hdfs://localhost:9000");
        myjob.addCacheFile(new Path(args[1]).toUri());
        myjob.setJarByClass(MyWordCount.class);
        myjob.setMapperClass(MyMapper.class);
        myjob.setReducerClass(MyReducer.class);
        myjob.setOutputKeyClass(Text.class);
        myjob.setOutputValueClass(DoubleWritable.class);
        // Uncomment to set the number of reduce tasks
//        myjob.setNumReduceTasks(2);
        FileInputFormat.addInputPath(myjob, new Path(args[0]));
        FileOutputFormat.setOutputPath(myjob,  new Path(args[2]));
        System.exit(myjob.waitForCompletion(true) ? 0 : 1);
    }
}
