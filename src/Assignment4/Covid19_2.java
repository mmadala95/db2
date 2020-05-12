package Assignment4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Covid19_2 {

    // 4 types declared: Type of input key, type of input value, type of output key, type of output value
    public static class MyMapper extends Mapper<Object, Text, Text, LongWritable> {
        private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        private final static LongWritable count = new LongWritable();

        private Text country = new Text();



        // The 4 types declared here should match the types that was declared on the top
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            try{
                String[] entries= value.toString().split(",");
                Configuration configuration=context.getConfiguration();
                Date startDate=new Date(Long.parseLong(configuration.get("startDate")));
                Date endDate=new Date(Long.parseLong(configuration.get("endDate")));
//                System.out.println("tets");

                if(!entries[1].equals("location")){


                    Date currentDate =simpleDateFormat.parse(entries[0]);
                    if(!currentDate.before(startDate) && !currentDate.after(endDate)){
//                        System.out.println(entries[0]+".."+entries[1]);
                        country.set(entries[1]);
                        count.set(Long.parseLong(entries[3]));
                        context.write(country,count);
                    }
                }
            }
            catch (ParseException e) {

                e.printStackTrace();
            }


        }

    }



    // 4 types declared: Type of input key, type of input value, type of output key, type of output value
    // The input types of reduce should match the output type of map
    public static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        private LongWritable total = new LongWritable();

        // Notice the that 2nd argument: type of the input value is an Iterable collection of objects
        //  with the same type declared above/as the type of output value from map
        public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long sum = 0;
            for (LongWritable tmp: values) {
                sum += tmp.get();
            }

            total.set(sum);
            // This write to the final output
            context.write(key, total);
        }
    }


    public static void main(String[] args)  throws Exception {
        Configuration conf = new Configuration();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = simpleDateFormat.parse(args[1]);
            Date endDate = simpleDateFormat.parse(args[2]);
            conf.set("startDate", String.valueOf(startDate.getTime()));
            conf.set("endDate", String.valueOf(endDate.getTime()));
            Job myjob = Job.getInstance(conf, "covid_19 count");
            myjob.getConfiguration().set("fs.default.name", "hdfs://localhost:9000");
            myjob.setJarByClass(MyWordCount.class);
            myjob.setMapperClass(MyMapper.class);
            myjob.setReducerClass(MyReducer.class);
            myjob.setOutputKeyClass(Text.class);
            myjob.setOutputValueClass(LongWritable.class);
            // Uncomment to set the number of reduce tasks
            //        myjob.setNumReduceTasks(2);
            FileInputFormat.addInputPath(myjob, new Path(args[0]));
            FileOutputFormat.setOutputPath(myjob, new Path(args[3]));
            System.exit(myjob.waitForCompletion(true) ? 0 : 1);
        } catch (ParseException e) {
            System.out.println("invalid date entered ");
            System.exit(0);
        }
    }
}
