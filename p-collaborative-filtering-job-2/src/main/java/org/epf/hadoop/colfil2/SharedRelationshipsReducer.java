package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
public class SharedRelationshipsReducer
    extends Reducer<UserPair, IntWritable, Text, IntWritable>  {
    @Override
        protected void reduce(UserPair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int nbSharedRelationships = 0;
            boolean areFriends = false;

            for(IntWritable value: values) {
                if (value.get() == -1) {
                    areFriends = true;//if they are friends, they are outta here
                    break;
                }
                nbSharedRelationships += value.get();//else +1 shared friend
            }
        if (!areFriends && nbSharedRelationships > 0) {
            context.write(new Text(key.toString()), new IntWritable(nbSharedRelationships));}
        }   
}