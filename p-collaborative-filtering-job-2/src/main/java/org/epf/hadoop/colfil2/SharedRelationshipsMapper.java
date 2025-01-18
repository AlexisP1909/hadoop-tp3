package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SharedRelationshipsMapper extends Mapper<LongWritable, Text, UserPair, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // bob a,b,c,d...
        String[] line = value.toString().split("\\s+");
        if (line.length < 2) {
            return;
        }
        String user = line[0];
        List<String> friends = Arrays.asList(line[1].split(","));
        for (int i = 0; i < friends.size(); i++) {
            for (int j = i + 1; j < friends.size(); j++) {
                UserPair pair = new UserPair(friends.get(i), friends.get(j));
                context.write(pair, new IntWritable(1)); // they are close to Bob (they have +1 close friend)
            }
        }
        for (String friend : friends) {
            context.write(new UserPair(user, friend), new IntWritable(-1)); // Bob is related to them (they are already friends so we cancel out the +1 they could get elsewhere)
        }
    }
}
