package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class RelationshipMapper extends Mapper<LongWritable, Relationship, Text, Text> {
    @Override
    public void map(LongWritable key, Relationship value, Mapper<LongWritable, Relationship, Text, Text>.Context context) throws IOException, InterruptedException {
        context.write(new Text(value.getId1()), new Text(value.getId2()));
        context.write(new Text(value.getId2()), new Text(value.getId1()));
    }
}
