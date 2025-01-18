package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.*;
public class RecommandationReducer
extends Reducer<Text, RelationRecommandation, Text, Text> {
    private static final int N_Best = 5; // Define the N_Best 

    @Override
    protected void reduce(Text key, Iterable<RelationRecommandation> values, Context context)
    throws IOException,InterruptedException{
        TreeSet<RelationRecommandation> topRecommendations = new TreeSet<>((a, b) -> {
            int compare = -Integer.compare(a.getNbSharedFriends(), b.getNbSharedFriends());
            if (compare != 0) return compare;

            return a.getFriendRecommended().compareTo(b.getFriendRecommended());
        });
        for (RelationRecommandation val : values) {
            RelationRecommandation copy = new RelationRecommandation(
                    val.getUser(),
                    val.getFriendRecommended(),
                    val.getNbSharedFriends()
            );
            topRecommendations.add(copy);

            if (topRecommendations.size() > N_Best) {
                topRecommendations.pollLast();
            }
        }
        if (!topRecommendations.isEmpty()) {
            List<String> recommendations = new ArrayList<>();
            for (RelationRecommandation rec : topRecommendations) {
                recommendations.add(String.format("%s(%d)",
                        rec.getFriendRecommended(), rec.getNbSharedFriends()));
            }
            context.write(key, new Text(String.join(",", recommendations)));
        }
    }
}

