package Domain.Expressions;

public enum LogicalOperation {
    AND{
        @Override
        public String toString() {
            return "&&";
        }
    },
    OR {
        @Override
        public String toString() {
            return "||";
        }
    }
}
