package type;

public enum ActionType {
    BORROW,
    RETURN;

    public boolean isBorrowing() {
        return this == BORROW;
    }

    public boolean isReturning() {
        return this == RETURN;
    }
}
