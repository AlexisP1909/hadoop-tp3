package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
public class RecommandationMapper 
extends Mapper<LongWritable, Text, Text, RelationRecommandation>{
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //usr1.usr2 nbFriends
        String[] line = value.toString().split("\t");
        if (line.length < 2) {
            return;
        }
        String[] users = line[0].split("\\.");
        if (users.length < 2) {
            return;
        }
        int nbSharedFriends = Integer.parseInt(line[1]);
        //b->a && a->b
        context.write(new Text(users[0]), new RelationRecommandation(users[0],users[1], nbSharedFriends));
        context.write(new Text(users[1]), new RelationRecommandation(users[1],users[0], nbSharedFriends));
    }

}
