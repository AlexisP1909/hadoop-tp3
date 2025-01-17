package org.epf.hadoop.colfil2;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configuration;
   
public class ColFilJob2 {
 
    public static void main(String[] args) throws Exception {
        String inputPath="";
        String outputPath="";

        if (args.length == 3 && args[0].contains("ColFilJob2")) {
            inputPath = args[1];
            outputPath = args[2];
        } else if (args.length == 2) {
            inputPath = args[0];
            outputPath = args[1];
        } else {
            System.err.println("Usage: ColFilJob2 <input path> <output path>");
            System.err.println("Arguments received : " + args.length);
            for (int i = 0; i < args.length; i++) {
                System.err.println("Arg[" + i + "] : " + args[i]);
            }
            System.exit(0);
        }

        System.out.println("Using input path: " + inputPath);
        System.out.println("Using output path: " + outputPath);

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Job2: Shared Friends Counter");
        job.setJarByClass(ColFilJob2.class);

        job.setInputFormatClass(TextInputFormat.class);

        job.setMapperClass(SharedRelationshipsMapper.class);
        job.setReducerClass(SharedRelationshipsReducer.class);

        job.setMapOutputKeyClass(UserPair.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class); 

        job.setNumReduceTasks(2);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}