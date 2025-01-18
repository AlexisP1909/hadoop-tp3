package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
public class RelationRecommandation implements WritableComparable<RelationRecommandation> {
    private String user;
    private String friendRecommended;
    private int nbSharedFriends;
    public RelationRecommandation() {
        this.user = "";
        this.friendRecommended = "";
        this.nbSharedFriends = 0;
    }
    public RelationRecommandation(String user, String friendRecommended, int nbSharedFriends) {
        this.user = user;
        this.friendRecommended = friendRecommended;
        this.nbSharedFriends = nbSharedFriends;
    }
    public String getUser() {
        return user;
    }
    public String getFriendRecommended() {
        return friendRecommended;
    }
    public int getNbSharedFriends() {
        return nbSharedFriends;
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(user);
        out.writeUTF(friendRecommended);
        out.writeInt(nbSharedFriends);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        user = in.readUTF();
        friendRecommended = in.readUTF();
        nbSharedFriends = in.readInt();
    }
    @Override
    public String toString() {
        return friendRecommended + "(" + nbSharedFriends+")";
    }
    @Override
    public int hashCode() {
        return user.hashCode() + friendRecommended.hashCode() + nbSharedFriends;
    }
    @Override
    public int compareTo(RelationRecommandation o) {
        int compare = -Integer.compare(this.nbSharedFriends, o.nbSharedFriends);
        if (compare != 0) return compare;

        return this.friendRecommended.compareTo(o.friendRecommended);
    }
}
