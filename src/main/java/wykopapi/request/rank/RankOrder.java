package wykopapi.request.rank;

public enum RankOrder {
    RANK,
    COMMENT_COUNT,
    LINK_COUNT,
    HP_LINK_COUNT,
    FOLLOWERS_COUNT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
