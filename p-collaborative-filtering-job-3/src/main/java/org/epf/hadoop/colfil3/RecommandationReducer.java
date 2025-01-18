package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.*;
public class RecommandationReducer
extends Reducer<Text, RelationRecommandation, Text, Text> {
    private static final int N_Best = 5; // Define the N_Best variable

    @Override
    protected void reduce(Text key, Iterable<RelationRecommandation> values, Context context)
    throws IOException,InterruptedException{
        PriorityQueue<RelationRecommandation> topRecommendations = new PriorityQueue<>(
            Comparator.comparingInt(RelationRecommandation::getNbSharedFriends)
                    .thenComparing(RelationRecommandation::getFriendRecommended)
        );

        // Collect all recommendations
        for (RelationRecommandation val : values) {
            // Add the current recommendation to the heap
            topRecommendations.add(val);

            // If the heap size exceeds N_Best, remove the recommendation with the least common friends
            if (topRecommendations.size() > N_Best) {
                topRecommendations.poll();
            }
        }

        // Build the list of recommendations
        List<String> recommendations = new ArrayList<>(N_Best);
        while (!topRecommendations.isEmpty()) {
            RelationRecommandation rec = topRecommendations.poll();
            recommendations.add(String.format("%s(%d)",
                    rec.getFriendRecommended(), rec.getNbSharedFriends()));
        }
        // Sort the recommendations in descending order of shared friends
        recommendations.sort((a, b) -> {
            int sharedFriendsA = Integer.parseInt(a.split("\\(")[1].replace(")", ""));
            int sharedFriendsB = Integer.parseInt(b.split("\\(")[1].replace(")", ""));
            return -Integer.compare(sharedFriendsA, sharedFriendsB);
        });
        context.write(key, new Text(String.join(",", recommendations)));
    }
}
