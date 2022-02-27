import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class log3 {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String site = line.split(" ")[6];
            word.set(site);
            context.write(word, one);
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text,IntWritable> {
        private IntWritable result = new IntWritable();
        private static int max=0;
        private static Text maxPath = new Text();
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            if(max<sum){
                max=sum;
                maxPath.set(key);
            }
//            result.set(max);
//            context.write(maxPath, result);
            result.set(sum);
            context.write(key, result);
        }

        @Override
        public void cleanup(Context context) throws IOException, InterruptedException {
            context.write(maxPath, new IntWritable(max));
        }
    }
//    public class IntWritableDecreasingComparator extends IntWritable.Comparator {
//        @SuppressWarnings("rawtypes")
//        public int compare(WritableComparable a, WritableComparable b){
//            return -super.compare(a,b);
//        }
//        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2){
//            return -super.compare(b1, s1, l1, b2, s2, l2);
//        }
//    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "log3");
        job.setJarByClass(log3.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);

        // add sort may here
//        job.setMapOutputKeyClass(IntWritable.class);
//        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // add sort method here
//        job.setSortComparatorClass(IntWritableDecreasingComparator.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}