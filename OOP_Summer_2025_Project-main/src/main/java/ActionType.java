public enum ActionType {
    BORROW,
    RETURN;

    public boolean isBorrowing() {
        return this == BORROW;
    }
}