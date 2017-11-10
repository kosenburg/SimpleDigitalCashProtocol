package PlaceHolderForBetterName;
// TODO Will have to be placed in both customer projects so Bob can verify commitment
public class Committer {
    public static long commit(String commitString, String randomString) {
        //TODO pick hashing function to perform commit
        return hash(commitString, randomString);
    }

    private static long hash(String commitString, String randomString) {
        //TODO need has that uses key were the key is the commit string?
        return 0;
    }

    public static long deCommit(long hash, String randomString) {
        //TODO
        return 0;
    }
}
